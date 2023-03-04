package com.mpfthprblmtq.commons.translation.model.exception;

public class NotEnoughSubstitutionValuesException extends RuntimeException {

    public NotEnoughSubstitutionValuesException(String message) {
        super(message);
    }

    public NotEnoughSubstitutionValuesException(int expected, int actual) {
        super(String.format("Not enough substitution values given! Expected %s but given %s!", expected, actual));
    }
}
