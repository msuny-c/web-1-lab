package se.ifmo.ru.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import se.ifmo.ru.http.HttpRequest;
import se.ifmo.ru.http.HttpResponse;
import se.ifmo.ru.http.HttpStatus;
import se.ifmo.ru.validators.Validator;

public class AreaHandler implements FCGIHandler {
    private final Validator validator;
    public AreaHandler(Validator validator) {
        this.validator = validator;
    }
    @Override
    public void serve(HttpResponse response, HttpRequest request) {
        request.parseParams();
        double x, y, r;
        try {
            x = Double.parseDouble(request.getParam("x"));
            y = Double.parseDouble(request.getParam("y"));
            r = Double.parseDouble(request.getParam("r"));
            if (!validator.validate(x, y, r)) throw new NumberFormatException();
        } catch (NumberFormatException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            return;
        }
        var hit = new HitResponse(x, y, r, validator.inArea(x, y, r));
        var mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        response.setHeader("Content-Type", "application/json");
        try {
            response.setBody(mapper.writeValueAsString(hit));
        } catch (JsonProcessingException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
