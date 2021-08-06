package br.com.zupacademy.guilherme.mercadolivre.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = ExistsIdValidator.class)
public @interface ExistsId {
    Class<?> entity();

    String message() default "Entity not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
