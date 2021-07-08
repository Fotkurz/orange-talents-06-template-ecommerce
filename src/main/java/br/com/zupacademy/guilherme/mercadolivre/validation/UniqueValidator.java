package br.com.zupacademy.guilherme.mercadolivre.validation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private final EntityManager em;
    private String fieldName;
    private Class<?> clazz;

    public UniqueValidator(final EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.clazz = constraintAnnotation.clazz();

    }

    @Override
    public boolean isValid(Object campo, ConstraintValidatorContext context) {
        String jpql = "SELECT x FROM " + clazz.getSimpleName() + " x WHERE x." + fieldName + " = :pCampo";
        Query query = em.createQuery(jpql).setParameter("pCampo", campo);
        List<Object> list = query.getResultList();
        if (list.isEmpty()) {
            return true;
        } else {
            String message = campo + " already used";
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            context.disableDefaultConstraintViolation();
        }
        return false;
    }


}
