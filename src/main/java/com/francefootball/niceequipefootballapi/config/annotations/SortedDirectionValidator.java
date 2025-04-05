package com.francefootball.niceequipefootballapi.config.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class SortedDirectionValidator implements ConstraintValidator<ValidateSortDirection, String> {
    private static final List<String> SORT_DIRECTION = List.of("ASC", "DESC");

    @Override
    public boolean isValid(String sortedDirection, ConstraintValidatorContext constraintValidatorContext) {
        return SORT_DIRECTION.contains(sortedDirection);
    }

}
