package com.francefootball.niceequipefootballapi.config.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class SortedByFieldValidator implements ConstraintValidator<ValidateSortedByField, String> {
    private static final List<String> VALID_FIELDS = List.of("nomEquipe", "acronym", "budget");

    @Override
    public boolean isValid(String sortedFieldType, ConstraintValidatorContext constraintValidatorContext) {
        return VALID_FIELDS.contains(sortedFieldType);
    }

}
