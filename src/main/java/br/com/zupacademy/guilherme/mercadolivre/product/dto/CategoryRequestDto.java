package br.com.zupacademy.guilherme.mercadolivre.product.dto;

import br.com.zupacademy.guilherme.mercadolivre.product.domain.Category;
import br.com.zupacademy.guilherme.mercadolivre.validation.ExistsId;
import br.com.zupacademy.guilherme.mercadolivre.validation.Unique;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class CategoryRequestDto {

    @NotBlank
    @Unique(fieldName = "name", clazz = Category.class)
    private String name;
    @ExistsId(entity = Category.class)
    private Long parentId;

    public CategoryRequestDto(String name) {
        this.name = name;
    }

    public CategoryRequestDto(String name, @ExistsId(entity = Category.class) Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public Category toModel(EntityManager em) {
        if (parentId == null) return new Category(name);
        Category category = em.find(Category.class, this.parentId);
        return new Category(name, category);
    }
}
