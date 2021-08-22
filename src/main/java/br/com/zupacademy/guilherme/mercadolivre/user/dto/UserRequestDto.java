package br.com.zupacademy.guilherme.mercadolivre.user.dto;

import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.validation.Unique;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRequestDto {

    @NotBlank
    @Email
    @Unique(fieldName = "login", clazz = User.class)
    private String login;
    @NotBlank
    @Length(min = 6)
    private String password;

    public UserRequestDto(@NotBlank String login, @NotBlank @Length(min = 6) String password) {
        this.login = login;
        this.password = password;
    }

    public User toModel() {
        return new User(login, password);
    }
}
