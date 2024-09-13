package se.ifmo.ru.handlers;

import se.ifmo.ru.http.HttpRequest;
import se.ifmo.ru.http.HttpResponse;
import se.ifmo.ru.http.HttpStatus;

public class AreaHandler implements FCGIHandler {
    @Override
    public void serve(HttpResponse response, HttpRequest request) {
        request.parseParams();
        double x, y, r;
        try {
            x = Double.parseDouble(request.getParam("x"));
            y = Double.parseDouble(request.getParam("y"));
            r = Double.parseDouble(request.getParam("r"));
            if (r < 0) throw new NumberFormatException();
        } catch (NumberFormatException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            return;
        }
        var json = "{\"x\": \"%s\", \"y\": \"%s\", \"r\": \"%s\", \"hit\": \"%s\"}";
        response.setHeader("Content-Type", "application/json");
        response.setBody(json.formatted(x, y, r, calculate(x, y, r)));
    }
    private boolean calculate(double x, double y, double r) {
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
}
