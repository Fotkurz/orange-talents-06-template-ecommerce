package br.com.zupacademy.guilherme.mercadolivre.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginRequestDto {

    private String login;
    private String password;

    public LoginRequestDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UsernamePasswordAuthenticationToken toUserToken(){
        return new UsernamePasswordAuthenticationToken(this.login, this.password);
    }

}
