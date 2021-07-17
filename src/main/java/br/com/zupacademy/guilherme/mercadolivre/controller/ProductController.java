package br.com.zupacademy.guilherme.mercadolivre.controller;

import br.com.zupacademy.guilherme.mercadolivre.controller.dto.request.ImageRequestDto;
import br.com.zupacademy.guilherme.mercadolivre.controller.dto.request.ProductRequestDto;
import br.com.zupacademy.guilherme.mercadolivre.controller.dto.response.FormErrorDto;
import br.com.zupacademy.guilherme.mercadolivre.domain.Product;
import br.com.zupacademy.guilherme.mercadolivre.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.repository.UserRepository;
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
}
