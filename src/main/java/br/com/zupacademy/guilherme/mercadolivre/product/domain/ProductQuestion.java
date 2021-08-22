package br.com.zupacademy.guilherme.mercadolivre.product.domain;

import br.com.zupacademy.guilherme.mercadolivre.emailsending.EmailSenderClass;
import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_questions")
public class ProductQuestion implements EmailSenderClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private final LocalDateTime registerDateTime = LocalDateTime.now();

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Product product;

    public ProductQuestion() {
    }

    public ProductQuestion(String title, User user, Product product) {
        this.title = title;
        this.user = user;
        this.product = product;
        emailSender(this.product);
    }

    @Override
    public void emailSender(Product product) {
        User owner = product.getOwner();
        String emailSender = "noreply@mercadolivre.com.br";
        System.out.println("[Sender: " + emailSender + "]");
        System.out.println("[Receiver: " + owner.getUsername() + "]");
        System.out.println("[Subject: Someone posted a question about your product: " + product.getName() + "]");
        System.out.println("[Body: " + user.getUsername() + " : " + title + "]");
    }

    public String getTitle() {
        return this.title;
    }
}
