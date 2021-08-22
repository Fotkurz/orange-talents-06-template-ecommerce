package br.com.zupacademy.guilherme.mercadolivre.checkout.dto;

import br.com.zupacademy.guilherme.mercadolivre.checkout.domain.Checkout;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.Product;
import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.validation.PaymentChecker;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class CheckoutRequest {

    @NotNull
    @Positive
    private Integer quantity;
    @NotBlank
    @PaymentChecker
    private String paymentMethod;

    @JsonCreator
    public CheckoutRequest(@JsonProperty("quantity") Integer quantity,
                           @JsonProperty("paymentMethod") String paymentMethod) {
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
    }

    public Checkout toModel(Product product, User buiyer) {
        if (product.stockCheck(this.quantity)) {
            BigDecimal value = product.getValue();
            product.decreaseQuantity(this.quantity);
            return new Checkout(product, buiyer, value, paymentMethod, quantity);
        }
        return null;
    }

}
