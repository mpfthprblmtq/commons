package com.mpfthprblmtq.commons.translation.model.exception;

public class TooManySubstitutionValuesException extends RuntimeException {

    public TooManySubstitutionValuesException(String translated, int expected, int actual) {
        super(String.format("Too many substitution values given for given translation (Expected %s, but given %s): %s",
                expected, actual, translated));
    }
}