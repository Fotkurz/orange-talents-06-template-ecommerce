package br.com.zupacademy.guilherme.mercadolivre.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = PaymentMethodValidator.class)
public @interface PaymentChecker {

    String message() default "Unavailable payment method";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
