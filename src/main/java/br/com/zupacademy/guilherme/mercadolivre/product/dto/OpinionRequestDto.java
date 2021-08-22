package br.com.zupacademy.guilherme.mercadolivre.product.dto;

import br.com.zupacademy.guilherme.mercadolivre.product.domain.Opinion;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.Product;
import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OpinionRequestDto {

    @NotNull
    @Range(min = 1, max = 5)
    private Integer rating;
    @NotBlank
    private String title;
    @NotBlank
    @Size(max = 500)
    private String description;

    public OpinionRequestDto(Integer rating, String title, String description) {
        this.rating = rating;
        this.title = title;
        this.description = description;
    }

    public Opinion toModel(Product product, User user) {
        return new Opinion(rating, title, description, product, user);
    }
}
