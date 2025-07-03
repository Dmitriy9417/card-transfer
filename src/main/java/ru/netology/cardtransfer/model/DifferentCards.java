package ru.netology.cardtransfer.model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.netology.cardtransfer.config.DifferentCardsValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DifferentCardsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DifferentCards {
    String message() default "cardFromNumber and cardToNumber must be different";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
