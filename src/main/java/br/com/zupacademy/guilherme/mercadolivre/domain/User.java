package br.com.zupacademy.guilherme.mercadolivre.domain;

import org.hibernate.validator.constraints.Length;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Email
    private String login;
    @NotBlank
    @Length(min = 6)
    private String password;
    @NotNull
    private LocalDateTime registerTime = LocalDateTime.now();

    public User() {}

    public User(String login, String password) {
        this.login = login;

        String encoded = BCrypt.hashpw(password, BCrypt.gensalt());
        this.password = encoded;
    }
}
