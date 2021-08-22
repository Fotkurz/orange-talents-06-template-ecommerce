package br.com.zupacademy.guilherme.mercadolivre.checkout.domain;

import br.com.zupacademy.guilherme.mercadolivre.checkout.controller.PaymentStatus;
import br.com.zupacademy.guilherme.mercadolivre.emailsending.EmailSenderClass;
import br.com.zupacademy.guilherme.mercadolivre.emailsending.EmailSendingFake;
import br.com.zupacademy.guilherme.mercadolivre.emailsending.EmailSubject;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.Product;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_transactions")
public class Transaction implements EmailSenderClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Checkout checkout;

    @Column(unique = true)
    private String paymentServiceId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String productUrl;

    private LocalDateTime transactionInstant = LocalDateTime.now();

    @Deprecated
    public Transaction() {
    }

    public Transaction(PaymentStatus status,
                       Checkout checkout,
                       String paymentServiceId,
                       String productUrl) {
        this.status = status;
        this.checkout = checkout;
        this.paymentServiceId = paymentServiceId;
        this.productUrl = productUrl;
        emailSender(checkout.getProduct());
    }

    public Long getId() {
        return id;
    }

    @Override
    public void emailSender(Product product) {
        String emailForBuyier = "Your transaction status is " + this.status;
        String emailForOwner = "The transaction made by " + checkout.getBuiyerLogin() + " was " + this.status;
        if (this.status.equals(PaymentStatus.SUCCESS)) {
            EmailSendingFake notifyProductOwner = new EmailSendingFake.EmailBuilder(product.getOwner().getUsername())
                    .setEmailSubjectLabel(EmailSubject.SUCCESSFUL_TRANSACTION.getLabel())
                    .setBody(emailForOwner)
                    .build();
            notifyProductOwner.sendEmail();

            EmailSendingFake notifyBuiyer = new EmailSendingFake.EmailBuilder(checkout.getBuiyerLogin())
                    .setEmailSubjectLabel(EmailSubject.SUCCESSFUL_TRANSACTION.getLabel())
                    .setBody(emailForBuyier)
                    .build();
            notifyBuiyer.sendEmail();

        } else {
            EmailSendingFake notifyProductOwner = new EmailSendingFake.EmailBuilder(product.getOwner().getUsername())
                    .setEmailSubjectLabel(EmailSubject.FAILED_TRANSACTION.getLabel())
                    .setBody(emailForOwner)
                    .build();
            notifyProductOwner.sendEmail();

            EmailSendingFake notifyBuiyer = new EmailSendingFake.EmailBuilder(checkout.getBuiyerLogin())
                    .setEmailSubjectLabel(EmailSubject.FAILED_TRANSACTION.getLabel())
                    .setBody(emailForBuyier + "\nClick this link if you wish to try again: " + this.productUrl)
                    .build();
            notifyBuiyer.sendEmail();
        }
    }




}
