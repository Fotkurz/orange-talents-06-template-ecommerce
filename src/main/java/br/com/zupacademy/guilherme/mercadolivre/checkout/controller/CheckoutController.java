package br.com.zupacademy.guilherme.mercadolivre.checkout.controller;

import br.com.zupacademy.guilherme.mercadolivre.adviser.FormErrorDto;
import br.com.zupacademy.guilherme.mercadolivre.checkout.domain.Checkout;
import br.com.zupacademy.guilherme.mercadolivre.checkout.domain.Transaction;
import br.com.zupacademy.guilherme.mercadolivre.checkout.dto.CheckoutRequest;
import br.com.zupacademy.guilherme.mercadolivre.checkout.dto.PaymentReturnDto;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.Product;
import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.user.repository.UserRepository;
import br.com.zupacademy.guilherme.mercadolivre.validation.ExistsId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CheckoutController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Value("${url.client.invoice}")
    private String invoiceEndpoint;

    @Value("${url.client.vendor}")
    private String vendorEndpoint;


    @Transactional
    @PostMapping("/products/{id}/checkout")
    public ResponseEntity<?> compra(@PathVariable @ExistsId(entity = Product.class) Long id,
                                    @AuthenticationPrincipal User login,
                                    @RequestBody @Valid CheckoutRequest checkoutRequest) {

        Optional<Product> possibleProduct = Optional.of(entityManager.find(Product.class, id));

        if (possibleProduct.isEmpty()) return ResponseEntity.notFound().build();

        Optional<User> possibleUser = userRepository.findByLogin(login.getUsername());
        Optional<Checkout> possibleCheckout = Optional.ofNullable(
                checkoutRequest.toModel(
                        possibleProduct.get(),
                        possibleUser.get()));

        if (possibleCheckout.isEmpty()) return ResponseEntity.badRequest()
                .body(new FormErrorDto("quantity", "quantity not available"));

        Checkout checkout = possibleCheckout.get();

        entityManager.persist(checkout);

        PaymentReturnDto paymentReturnDto = PaymentMethod.sendRedirect(checkout.getId(), checkout.getPaymentMethod());

        Optional<Transaction> possibleTransaction = Optional.ofNullable(paymentReturnDto.toTransaction(checkout));
        if (possibleTransaction.isEmpty()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

        entityManager.persist(possibleTransaction.get());

        String invoiceWithVariables = invoiceEndpoint.concat("?idTransaction=" + possibleTransaction.get().getId() +
                "&idClient=" + possibleUser.get().getId());

        String vendorWithVariables = vendorEndpoint.concat("?idTransaction=" + possibleTransaction.get().getId() +
                "&idVendor=" + possibleUser.get().getId());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForEntity(invoiceWithVariables, ResponseEntity.class);
        restTemplate.getForEntity(vendorWithVariables, ResponseEntity.class);

        entityManager.merge(possibleProduct.get());

        return ResponseEntity.created(paymentReturnDto.getUrlLocation()).build();
    }

}
