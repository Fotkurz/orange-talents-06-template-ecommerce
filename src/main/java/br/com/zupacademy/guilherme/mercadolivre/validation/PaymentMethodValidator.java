package br.com.zupacademy.guilherme.mercadolivre.validation;

import br.com.zupacademy.guilherme.mercadolivre.checkout.controller.PaymentMethod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PaymentMethodValidator implements ConstraintValidator<PaymentChecker, String> {

    @Override
    public void initialize(PaymentChecker constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(PaymentMethod.isField(value)) return true;
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate( value + " is not a valid payment method")
                .addConstraintViolation();
        return false;
    }
}
