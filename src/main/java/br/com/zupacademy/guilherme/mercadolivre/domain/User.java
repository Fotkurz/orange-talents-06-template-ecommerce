package br.com.zupacademy.guilherme.mercadolivre.domain;

import org.hibernate.validator.constraints.Length;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "tbl_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Email
    private String login;
    @NotBlank @Length(min = 6)
    private String password;
    @PastOrPresent
    private LocalDateTime registerDateTime = LocalDateTime.now();

    public User() {}

    public User(String login, String password) {
        this.login = login;
        String encoded = BCrypt.hashpw(password, BCrypt.gensalt());
        this.password = encoded;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean haveAProductAlready(EntityManager em) {
        if(em.createQuery("SELECT x FROM Product x WHERE x.user.id = " + getId()).getResultList().isEmpty()) return false;
        return true;
    }
}
