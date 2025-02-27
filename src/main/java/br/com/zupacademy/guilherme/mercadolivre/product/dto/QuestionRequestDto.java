package br.com.zupacademy.guilherme.mercadolivre.product.dto;

import br.com.zupacademy.guilherme.mercadolivre.product.domain.Product;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.ProductQuestion;
import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;

public class QuestionRequestDto {

    @NotBlank
    @JsonSerialize
    private String title;

    @JsonCreator
    public QuestionRequestDto(@JsonProperty("title") String title) {
        this.title = title;
    }

    public ProductQuestion toModel(User user, Product product) {
        return new ProductQuestion(title, user, product);
    }
}
