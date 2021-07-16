package br.com.zupacademy.guilherme.mercadolivre.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_characteristics")
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @ManyToOne
    private Product product;

    public Characteristic() {}

    public Characteristic(String name, String description, Product product) {
        this.name = name;
        this.description = description;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Characteristic toModel(@NotNull @Valid Product product) {
        return new Characteristic(name, description, product);
    }
}
