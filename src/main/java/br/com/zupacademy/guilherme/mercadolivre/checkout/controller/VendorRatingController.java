package br.com.zupacademy.guilherme.mercadolivre.checkout.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendorrating")
public class VendorRatingController {

    @GetMapping
    protected ResponseEntity<?> rating(@RequestParam String idTransaction, @RequestParam String idVendor) {
        System.out.println("Ajustando o rating do vendedor ");
        return ResponseEntity.ok().build();
    }
}
