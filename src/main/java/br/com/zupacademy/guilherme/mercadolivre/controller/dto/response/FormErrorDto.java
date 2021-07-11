package br.com.zupacademy.guilherme.mercadolivre.controller.dto.response;

public class FormErrorDto {

    private String field;
    private String message;

    public FormErrorDto(String campo, String message) {
        this.field = campo;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
