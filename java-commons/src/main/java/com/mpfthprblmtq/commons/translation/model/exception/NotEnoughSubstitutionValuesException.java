package com.mpfthprblmtq.commons.translation.model.exception;

public class NotEnoughSubstitutionValuesException extends RuntimeException {

    public NotEnoughSubstitutionValuesException(String translated, int expected, int actual) {
        super(String.format("Not enough substitution values given for given translation (Expected %s, but given %s): %s",
                expected, actual, translated));
    }
}
