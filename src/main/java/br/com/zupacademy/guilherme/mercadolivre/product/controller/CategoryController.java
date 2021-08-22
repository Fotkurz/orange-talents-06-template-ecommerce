package br.com.zupacademy.guilherme.mercadolivre.product.controller;

import br.com.zupacademy.guilherme.mercadolivre.product.domain.Category;
import br.com.zupacademy.guilherme.mercadolivre.product.dto.CategoryRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @PersistenceContext
    private EntityManager em;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        Category category = categoryRequestDto.toModel(em);
        em.persist(category);
    }

}
