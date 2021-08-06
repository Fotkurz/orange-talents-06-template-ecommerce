package br.com.zupacademy.guilherme.mercadolivre.controller.dto.request;

import br.com.zupacademy.guilherme.mercadolivre.domain.User;
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
