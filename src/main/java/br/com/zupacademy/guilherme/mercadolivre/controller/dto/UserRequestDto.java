package br.com.zupacademy.guilherme.mercadolivre.controller.dto;

import br.com.zupacademy.guilherme.mercadolivre.domain.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRequestDto {

    @NotBlank @Email
    private String login;
    @NotBlank @Length(min = 6)
    private String password;

    public UserRequestDto(@NotBlank @Email String login, @NotBlank @Length(min = 6) String password) {
        this.login = login;
        this.password = password;
    }

    public User toModel() {
        return new User(login, password);
    }
}
