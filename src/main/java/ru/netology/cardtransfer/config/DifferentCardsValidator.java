package ru.netology.cardtransfer.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.netology.cardtransfer.model.DifferentCards;
import ru.netology.cardtransfer.model.TransferRequest;

public class DifferentCardsValidator implements ConstraintValidator<DifferentCards, TransferRequest> {

    @Override
    public boolean isValid(TransferRequest value, ConstraintValidatorContext context) {
        if (value == null) return true; // Не наша задача, если объект null
        if (value.cardFromNumber == null || value.cardToNumber == null) return true;
        return !value.cardFromNumber.equals(value.cardToNumber);
    }
}
