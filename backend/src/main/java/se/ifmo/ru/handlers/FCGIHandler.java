package se.ifmo.ru.handlers;

import se.ifmo.ru.http.HttpRequest;
import se.ifmo.ru.http.HttpResponse;

public interface FCGIHandler {
    void serve(HttpResponse response, HttpRequest request);
}
