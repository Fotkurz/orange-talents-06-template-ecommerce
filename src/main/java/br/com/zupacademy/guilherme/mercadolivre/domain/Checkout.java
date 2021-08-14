package br.com.zupacademy.guilherme.mercadolivre.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_checkouts")
public class Checkout {

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
    }

    public Long getId() {
        return this.id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
