package br.com.zupacademy.guilherme.mercadolivre.validation;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Number> {

    private final EntityManager em;
    private Class<?> entity;

    public ExistsIdValidator(EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(ExistsId constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value != null) {
            String jpql = "SELECT x FROM " + entity.getSimpleName() + " x WHERE x.id = :pValue";
            Query query = em.createQuery(jpql)
                    .setParameter("pValue", value);
            List<Object> list = query.getResultList();
            if (list.isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(entity.getSimpleName() + " not found").addConstraintViolation();
                return false;
            }
        }
        return true;
    }

}
