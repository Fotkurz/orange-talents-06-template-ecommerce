package br.com.zupacademy.guilherme.mercadolivre.domain;

import br.com.zupacademy.guilherme.mercadolivre.observer.EmailSenderObserver;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_checkouts")
public class Checkout implements EmailSenderObserver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @Positive
    private Integer quantity;
    @NotNull @PositiveOrZero
    private BigDecimal unitaryValue;
    @NotNull @PositiveOrZero
    private BigDecimal totalValue;
    @NotNull @ManyToOne
    private Product product;
    @NotNull @ManyToOne
    private User buiyer;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public Checkout(Product product, User buiyer, BigDecimal value, @NotBlank String paymentMethod, @NotNull @Positive Integer quantity) {
        this.unitaryValue = value;
        this.quantity = quantity;
        this.unitaryValue = value;
        this.totalValue = value.multiply(BigDecimal.valueOf(quantity));
        this.product = product;
        this.buiyer = buiyer;
        this.paymentMethod = PaymentMethod.valueOf(paymentMethod);
        emailSender(this.product);
    }

    public Long getId() {
        return this.id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
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
