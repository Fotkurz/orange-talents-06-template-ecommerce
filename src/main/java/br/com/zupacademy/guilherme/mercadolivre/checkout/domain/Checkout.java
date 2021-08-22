package br.com.zupacademy.guilherme.mercadolivre.checkout.domain;

import br.com.zupacademy.guilherme.mercadolivre.checkout.controller.CheckoutStatus;
import br.com.zupacademy.guilherme.mercadolivre.checkout.controller.PaymentMethod;
import br.com.zupacademy.guilherme.mercadolivre.emailsending.EmailSenderClass;
import br.com.zupacademy.guilherme.mercadolivre.emailsending.EmailSendingFake;
import br.com.zupacademy.guilherme.mercadolivre.emailsending.EmailSubject;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.Product;
import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_checkouts")
public class Checkout implements EmailSenderClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Positive
    private Integer quantity;
    @NotNull
    @PositiveOrZero
    private BigDecimal unitaryValue;
    @NotNull
    @PositiveOrZero
    private BigDecimal totalValue;
    @NotNull
    @ManyToOne
    private Product product;
    @NotNull
    @ManyToOne
    private User buiyer;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private CheckoutStatus checkoutStatus = CheckoutStatus.INITIALIZED;

    @Deprecated
    public Checkout() {}

    public Checkout(Product product,
                    User buiyer,
                    BigDecimal value,
                    @NotBlank String paymentMethod,
                    @NotNull @Positive Integer quantity) {
        this.product = product;
        this.buiyer = buiyer;
        this.unitaryValue = value;
        this.paymentMethod = PaymentMethod.valueOf(paymentMethod);
        this.quantity = quantity;
        this.totalValue = value.multiply(BigDecimal.valueOf(quantity));
        emailSender(this.product);
    }

    public Long getId() {
        return this.id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void finishCheckout() {
        this.checkoutStatus = CheckoutStatus.FINISHED;
    }


    public String getBuiyerLogin() {
        return buiyer.getUsername();
    }

    public String getProductOwner() {
        return product.getOwner().getUsername();
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public void emailSender(Product product) {
        User owner = product.getOwner();

        EmailSendingFake emailSendingFake = new EmailSendingFake.EmailBuilder(owner.getUsername())
                .setEmailSubjectLabel(EmailSubject.CHECKOUT.getLabel())
                .setBody("The user " + getBuiyerLogin() + " bought " + this.getQuantity() + " of your " + product.getName())
                .build();

        emailSendingFake.sendEmail();
    }
}
