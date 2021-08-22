package br.com.zupacademy.guilherme.mercadolivre.product.controller;

import br.com.zupacademy.guilherme.mercadolivre.adviser.FormErrorDto;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.DevImageUpload;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.Opinion;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.Product;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.ProductQuestion;
import br.com.zupacademy.guilherme.mercadolivre.product.dto.*;
import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid ProductRequestDto productRequestDto,
                                    @AuthenticationPrincipal User user) {
        Optional<User> owner = userRepository.findByLogin(user.getUsername());
        if(owner.isEmpty()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Product product = productRequestDto.toModel(entityManager, owner.get());
        if (product != null) {
            entityManager.persist(product);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(new FormErrorDto("User", "Already have a product registred"));


    }

    @PostMapping(value = "/{id}/images")
    @Transactional
    @Profile("dev")
    public ResponseEntity<?> addImagesToProduct(@PathVariable("id") Long id, @Valid ImageRequestDto imageRequestDto,
                                                @AuthenticationPrincipal User principal) {
        String login = principal.getUsername();
        DevImageUpload upload = new DevImageUpload(imageRequestDto.getImages());
        Optional<Product> optionalProduct = Optional.ofNullable(entityManager.find(Product.class, id));
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.isOwner(login)) {
                product.setImagesLinks(upload.getUrls());
                entityManager.merge(product);
                return ResponseEntity.ok().build();
            }
        } else {
            return ResponseEntity.badRequest().body(new FormErrorDto("Product", "No product with this id"));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new FormErrorDto("User", "Product not owned by " + login));
    }

    @PostMapping("/{id}/opinion")
    @Transactional
    public ResponseEntity<?> opinion(@PathVariable("id") Long id, @Valid @RequestBody OpinionRequestDto opinionRequestDto,
                                     @AuthenticationPrincipal User user) {
        Optional<Product> product = Optional.ofNullable(entityManager.find(Product.class, id));
        if (product.isPresent()) {
            Optional<User> owner = userRepository.findByLogin(user.getUsername());
            if (owner.isPresent()) {
                Opinion opinion = opinionRequestDto.toModel(product.get(), owner.get());
                entityManager.persist(opinion);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body(new FormErrorDto("User", "Not a valid user"));
        }

        return ResponseEntity.badRequest().body(new FormErrorDto("Product", "No product with this id"));
    }

    @PostMapping("/{id}/questions")
    @Transactional
    public ResponseEntity<?> questions(@PathVariable("id") Long id, @Valid @RequestBody QuestionRequestDto questionRequestDto,
                                       @AuthenticationPrincipal User user) {

        Optional<Product> product = Optional.ofNullable(entityManager.find(Product.class, id));
        if (product.isPresent()) {
            Optional<User> owner = userRepository.findByLogin(user.getUsername());
            if (owner.isPresent()) {
                ProductQuestion question = questionRequestDto.toModel(owner.get(), product.get());
                entityManager.persist(question);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body(new FormErrorDto("User", "Not a valid user"));
        }

        return ResponseEntity.badRequest().body(new FormErrorDto("Product", "No product with this id"));
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<?> details(@PathVariable("id") Long id) {

        Optional<Product> product = Optional.ofNullable(entityManager.find(Product.class, id));

        if (product.isEmpty()) return ResponseEntity.notFound().build();

        ProductResponseDto productResponseDto = new ProductResponseDto(product.get());
        return ResponseEntity.ok().body(productResponseDto);
    }
}
