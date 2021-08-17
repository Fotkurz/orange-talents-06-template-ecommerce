package br.com.zupacademy.guilherme.mercadolivre.controller;


import br.com.zupacademy.guilherme.mercadolivre.controller.dto.response.FormErrorDto;
import br.com.zupacademy.guilherme.mercadolivre.domain.Checkout;
import br.com.zupacademy.guilherme.mercadolivre.domain.PaymentStatus;
import br.com.zupacademy.guilherme.mercadolivre.domain.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
public class PaymentProcessor {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @GetMapping("/payment-processor")
    public ResponseEntity<?> process(@RequestParam String idGateway,
                                     @RequestParam Long idCheckout,
                                     @RequestParam String status) {
        Optional<Checkout> checkoutOptional = Optional.ofNullable(
                entityManager.find(Checkout.class, idCheckout));

        if(checkoutOptional.isEmpty()) return ResponseEntity.notFound().build();

        Checkout checkout = checkoutOptional.get();
        if(PaymentStatus.valueOf(status) == PaymentStatus.SUCCESS) checkout.finishCheckout();
        Transaction transaction = new Transaction(PaymentStatus.valueOf(status), checkoutOptional.get(), idGateway);
        entityManager.merge(checkout);

        RestTemplate restTemplate = new RestTemplate();


        return ResponseEntity.ok().body(new FormErrorDto("Teste", "Está voltando"));
    }
}
