package se.ifmo.ru.http;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpResponse {
    private final HashMap<String, String> headers;
    private HttpStatus status = HttpStatus.OK;
    private String version = "1.1";
    private String body = "";
    public HttpResponse() {
        this.headers = new HashMap<>();
    }
    public void setHeader(String key, String value) {
        headers.put(key, value);
    }
    public void setBody(String body) {
        if (body == null) {
            this.body = "";
            return;
        }
        this.body = body;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    public String getRaw() {
        StringBuilder response = new StringBuilder("HTTP/%s %s\r\n".formatted(version, status));
        setHeader("Content-Length", "" + body.getBytes(StandardCharsets.UTF_8).length);
        for (String key : headers.keySet()) {
            response.append("%s: %s\r\n".formatted(key, headers.get(key)));
        }
        response.append("\r\n" + body + "\r\n");
        return response.toString();
    }
}
