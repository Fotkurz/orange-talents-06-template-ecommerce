package br.com.zupacademy.guilherme.mercadolivre.product.dto;

import br.com.zupacademy.guilherme.mercadolivre.product.domain.Characteristic;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CharacteristicRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public CharacteristicRequestDto(@NotNull String name, @NotNull String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public Characteristic toModel(Product product) {
        return new Characteristic(name, description, product);
    }
}
