package br.com.zupacademy.guilherme.mercadolivre.controller.dto.response;

public class LoginResponseDto {

    private String type;
    private String token;

    public LoginResponseDto(String type, String token) {
        this.type = type;
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }
}
