package br.com.zupacademy.guilherme.mercadolivre.observer;

import br.com.zupacademy.guilherme.mercadolivre.domain.Product;
import org.springframework.stereotype.Component;

@Component
public interface EmailSenderObserver {
    public void emailSender(Product product);
}
