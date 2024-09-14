package se.ifmo.ru.validators;

public class AreaValidator implements Validator {
    public boolean validate(double x, double y, double r) {
        return validateX(x) && validateY(y) && validateR(r);
    }
    public boolean inArea(double x, double y, double r) {
        return inRectangle(x, y, r) || inCircle(x, y, r) || inTriangle(x, y, r);
    }
    private boolean inRectangle(double x, double y, double r) {
        return x <= 0 && y <= 0 && x <= -r && y <= -r;
    }
    private boolean inCircle(double x, double y, double r) {
        return x <= 0 && y >= 0 && x * x + y * y <= r * r;
    }
    private boolean inTriangle(double x, double y, double r) {
        return x >= 0 && x <= r && y <= 0 && y >= -r / 2 && y >= 1.0 / 2 * x - r / 2;
    }
    private boolean validateX(double x) {
        return x == Math.ceil(x) && -5 <= x && x <= 3;
    }
    private boolean validateY(double y) {
        return -3 < y && y < 3;
    }
    private boolean validateR(double r) {
        return 2 < r && r < 5;
    }
}
