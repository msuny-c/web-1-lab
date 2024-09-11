package se.ifmo.ru.handlers;

import se.ifmo.ru.http.HttpMethod;
import se.ifmo.ru.http.HttpRequest;
import se.ifmo.ru.http.HttpResponse;
import se.ifmo.ru.http.HttpStatus;

import java.util.HashMap;

public class FCGIRouter implements FCGIHandler {
    private final HashMap<HttpMethod, FCGIHandler> handlers;
    public FCGIRouter() {
        this.handlers = new HashMap<>();
    }
    public void setHandler(HttpMethod method, FCGIHandler handler) {
        this.handlers.put(method, handler);
    }

    @Override
    public void serve(HttpResponse response, HttpRequest request) {
        var handler = handlers.get(request.getMethod());
        if (handler == null) {
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
            return;
        }
        handler.serve(response, request);
    }
}
