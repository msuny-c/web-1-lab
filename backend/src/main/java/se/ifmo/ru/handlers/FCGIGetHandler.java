package se.ifmo.ru.handlers;

import se.ifmo.ru.http.HttpRequest;
import se.ifmo.ru.http.HttpResponse;
import se.ifmo.ru.http.HttpStatus;

public class FCGIGetHandler implements FCGIHandler {
    @Override
    public void serve(HttpResponse response, HttpRequest request) {
        request.parseParams();
        double x, y, r;
        try {
            x = Double.parseDouble(request.getParam("x"));
            y = Double.parseDouble(request.getParam("y"));
            r = Double.parseDouble(request.getParam("r"));
        } catch (Exception exception) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            return;
        }
        if (r < 0) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            return;
        }
        var json = """
                {
                    "x": "%s",
                    "y": "%s",
                    "r": "%s",
                    "hit": "%s"
                }
                """;
        response.setHeader("Content-Type", "application/json");
        if (x <= 0 && y <= 0 && x <= -r && y <= -r || x >= 0 && x <= r && y <= 0 && y >= -r/2 && y >= 1.0/2*x-r/2 || x <= 0 && y >= 0 && x*x+y*y <= r*r) {
            response.setBody(json.formatted(x, y, r, true));
        } else {
            response.setBody(json.formatted(x, y, r, false));
        }
    }
}
