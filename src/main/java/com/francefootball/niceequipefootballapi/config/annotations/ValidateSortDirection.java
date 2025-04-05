package com.francefootball.niceequipefootballapi.config.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SortedDirectionValidator.class)
public @interface ValidateSortDirection {

    String message() default "Invalide valeur de direction de tri : il faut utiliser 'ASC' ou 'DESC'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
