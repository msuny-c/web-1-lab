package se.ifmo.ru;
import se.ifmo.ru.handlers.AreaHandler;
import se.ifmo.ru.handlers.FCGIRouter;
import se.ifmo.ru.http.HttpMethod;
import se.ifmo.ru.server.FCGIServer;
import se.ifmo.ru.validators.AreaValidator;


public class Main {
    public static void main(String[] args) {
        FCGIServer server = new FCGIServer();
        FCGIRouter router = new FCGIRouter();
        var validator = new AreaValidator();
        router.setHandler(HttpMethod.GET, new AreaHandler(validator));
        server.run(router);
    }
}