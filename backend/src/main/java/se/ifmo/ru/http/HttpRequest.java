package se.ifmo.ru.http;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpRequest {
    private final String uri;
    private final HashMap<String, String> params;
    private final String body;
    private final HttpMethod method;
    private final String query;
    public HttpRequest(String uri, String query, HttpMethod method, String body) {
        this.uri = uri;
        this.query = query;
        this.method = method;
        this.body = body;
        this.params = new HashMap<>();
    }
    public void parseParams() {
        String[] pairs = query.split("&");
        try {
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                params.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8), URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
            }
        } catch (Exception ignore) {}

    }
    public String getUri() {
        return uri;
    }
    public String getBody() {
        return body;
    }
    public HttpMethod getMethod() {
        return method;
    }
    public String getParam(String param) {
        return params.get(param);
    }
}
