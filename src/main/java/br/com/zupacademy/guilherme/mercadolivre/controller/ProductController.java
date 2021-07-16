package br.com.zupacademy.guilherme.mercadolivre.controller;

import br.com.zupacademy.guilherme.mercadolivre.controller.dto.request.ProductRequestDto;
import br.com.zupacademy.guilherme.mercadolivre.controller.dto.response.FormErrorDto;
import br.com.zupacademy.guilherme.mercadolivre.domain.Product;
import br.com.zupacademy.guilherme.mercadolivre.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.repository.UserRepository;
import br.com.zupacademy.guilherme.mercadolivre.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid ProductRequestDto productRequestDto, HttpServletRequest request) {
        String token = tokenManager.tokenRecovery(request);
        String login = tokenManager.getTokenAudience(token);

        Optional<User> user = userRepository.findByLogin(login);

        Product product = productRequestDto.toModel(entityManager, user.get());

        if(product != null) {
            entityManager.persist(product);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(new FormErrorDto("User", "Already have a product registred"));
    }
}
