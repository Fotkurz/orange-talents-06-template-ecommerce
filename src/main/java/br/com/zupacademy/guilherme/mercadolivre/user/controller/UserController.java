package br.com.zupacademy.guilherme.mercadolivre.user.controller;

import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.user.dto.UserRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @PersistenceContext
    private EntityManager em;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid UserRequestDto userRequestDto) {
        User user = userRequestDto.toModel();
        em.persist(user);
    }
}
