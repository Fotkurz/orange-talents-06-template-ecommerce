package br.com.zupacademy.guilherme.mercadolivre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("prod")
public class MercadolivreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MercadolivreApplication.class, args);
    }

}
