package br.com.zupacademy.guilherme.mercadolivre.domain;

import javax.persistence.*;

@Entity
@Table(name = "tbl_transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Checkout checkout;

    @Column(unique = true)
    private String paymentServiceId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Deprecated
    public Transaction(){}

    public Transaction(PaymentStatus status,
                       Checkout checkout,
                       String paymentServiceId) {
        this.status = status;
        this.checkout = checkout;
        this.paymentServiceId = paymentServiceId;

    }


}
