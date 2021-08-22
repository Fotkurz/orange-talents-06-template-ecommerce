package br.com.zupacademy.guilherme.mercadolivre.checkout.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoicegenerator")
public class InvoiceController {

    @GetMapping
    protected ResponseEntity<?> geraNota(@RequestParam String idTransaction, @RequestParam String idClient) {
        System.out.println("Gerando nota fiscal...");
        return ResponseEntity.ok().build();
    }
}
