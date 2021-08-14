package br.com.zupacademy.guilherme.mercadolivre.controller;

import br.com.zupacademy.guilherme.mercadolivre.controller.dto.request.CheckoutRequest;
import br.com.zupacademy.guilherme.mercadolivre.controller.dto.response.FormErrorDto;
import br.com.zupacademy.guilherme.mercadolivre.domain.Checkout;
import br.com.zupacademy.guilherme.mercadolivre.domain.Product;
import br.com.zupacademy.guilherme.mercadolivre.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
public class CheckoutController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @PostMapping("/products/{id}/checkout")
    public ResponseEntity<?> compra(@PathVariable Long id,
                                    @AuthenticationPrincipal User login,
                                    UriComponentsBuilder uriComponentsBuilder,
                                    @RequestBody @Valid CheckoutRequest checkoutRequest) {

        Optional<Product> possibleProduct = Optional.of(entityManager.find(Product.class, id));
        Optional<User> possibleUser = userRepository.findByLogin(login.getUsername());
        if (possibleProduct.isEmpty()) return ResponseEntity.notFound().build();

        Optional<Checkout> possibleCheckout = Optional.ofNullable(checkoutRequest.toModel(possibleProduct.get(),
                possibleUser.get()));
        if (possibleCheckout.isEmpty()) return ResponseEntity
                .badRequest()
                .body(new FormErrorDto("quantity", "quantity not available"));

        Checkout checkout = possibleCheckout.get();

        entityManager.persist(checkout);
        entityManager.merge(possibleProduct.get());

        URI uriCheckout = uriComponentsBuilder.path("products/{id}/checkout/")
                .uriVariables(Map.of("id", possibleProduct.get().getId()))
                .build().toUri();

        URI withVariables;
        if (checkout.getPaymentMethod().toString().equals("PAYPAL")) {
            withVariables = URI.create("paypal.com?buyerId=" + checkout.getId() + "&redirectUrl=" + uriCheckout);
        } else {
            withVariables = URI.create("pagseguro.com?returnId=" + checkout.getId() + "&redirectUrl=" + uriCheckout);
        }
        return ResponseEntity.created(withVariables).build();
    }

}
