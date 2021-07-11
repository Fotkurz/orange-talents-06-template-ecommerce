package br.com.zupacademy.guilherme.mercadolivre.controller;

import br.com.zupacademy.guilherme.mercadolivre.controller.dto.request.UserRequestDto;
import br.com.zupacademy.guilherme.mercadolivre.domain.User;
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
    public void create (@RequestBody @Valid UserRequestDto userRequestDto) {
        User user = userRequestDto.toModel();
        em.persist(user);
    }
}
