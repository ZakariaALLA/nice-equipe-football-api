package com.francefootball.niceequipefootballapi.config.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SortedByFieldValidator.class)
public @interface ValidateSortedByField {

    String message() default "Invalide valeur de tri : il faut utiliser 'nomEquipe', 'acronym' ou 'budget'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
