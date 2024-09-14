package se.ifmo.ru.validators;

public interface Validator {
    boolean validate(double x, double y, double r);
    boolean inArea(double x, double y, double r);
}