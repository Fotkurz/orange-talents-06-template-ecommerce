package br.com.zupacademy.guilherme.mercadolivre.domain;

import br.com.zupacademy.guilherme.mercadolivre.observer.EmailSenderObserver;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.net.URI;

@Entity
@Table(name = "tbl_checkouts")
public class Checkout implements EmailSenderObserver {

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

    @Override
    public void emailSender(Product product) {
        User owner = product.getOwner();
        String emailSender = "question-messenger@mercadolivre.com.br";
        System.out.println("[Sender: " + emailSender + "]");
        System.out.println("[Receiver: " + owner.getUsername() + "]");
        System.out.println("[Subject: Sell notification for product: " + product.getName() + "]");
        System.out.println("[Body: " + buiyer.getUsername() +
                " : bought " + quantity + " of your product: " +
                product.getName() + "\n Total: R$ " + totalValue + "]");
    }


}
