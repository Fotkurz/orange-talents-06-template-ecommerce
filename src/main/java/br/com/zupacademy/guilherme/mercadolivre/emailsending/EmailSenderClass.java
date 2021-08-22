package br.com.zupacademy.guilherme.mercadolivre.emailsending;

import br.com.zupacademy.guilherme.mercadolivre.product.domain.Product;
import org.springframework.stereotype.Component;

@Component
public interface EmailSenderClass {
    public void emailSender(Product product);
}
