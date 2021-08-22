package br.com.zupacademy.guilherme.mercadolivre.product.dto;

import br.com.zupacademy.guilherme.mercadolivre.product.domain.Category;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.Product;
import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.validation.ExistsId;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductRequestDto {

    @NotBlank
    private String name;
    @NotNull
    @Positive
    private BigDecimal value;
    @NotNull
    @Min(0)
    @Positive
    private Integer quantity;
    @NotBlank
    @Length(max = 1000)
    private String description;
    @NotNull
    @ExistsId(entity = Category.class)
    private Long idCategory;
    @Size(min = 3)
    @Valid
    private List<CharacteristicRequestDto> characteristics = new ArrayList<>();

    public ProductRequestDto(String name, BigDecimal value, Integer quantity,
                             List<CharacteristicRequestDto> characteristics, String description, Long idCategory) {
        this.name = name;
        this.value = value;
        this.quantity = quantity;
        this.characteristics.addAll(characteristics);
        this.description = description;
        this.idCategory = idCategory;

    }

    public List<CharacteristicRequestDto> getCharacteristics() {
        return characteristics;
    }

    public Product toModel(EntityManager em, User user) {
        Category category = em.find(Category.class, this.idCategory);
        if (!user.haveAProductAlready(em)) {
            Product product = new Product(name, value, quantity, description, category, characteristics, user);
            return product;
        }
        return null;
    }

}
