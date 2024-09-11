package se.ifmo.ru.server;

import com.fastcgi.FCGIInterface;
import se.ifmo.ru.http.HttpMethod;
import se.ifmo.ru.http.HttpRequest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class FCGIHelper {
    protected static HttpRequest parse() {
        var uri = (String) getParam("REQUEST_URI");
        var method = HttpMethod.valueOf((String) getParam("REQUEST_METHOD"));
        var body = readBody();
        var query = (String) getParam("QUERY_STRING");
        return new HttpRequest(uri, query, method, body);
    }
    private static Object getParam(String param) {
        return FCGIInterface.request.params.get(param);
    }
    private static String readBody() {
        try {
            FCGIInterface.request.inStream.fill();
            var length = Integer.valueOf((String) getParam("CONTENT_LENGTH"));
            var buffer = ByteBuffer.allocate(length);
            var read = FCGIInterface.request.inStream.read(buffer.array(), 0, length);
            var bodyRaw = new byte[read];
            buffer.get(bodyRaw);
            buffer.clear();
            return new String(bodyRaw, StandardCharsets.UTF_8);
        } catch (Exception exception) {
            return "";
        }
    }
    protected static void resetStreams() {
        System.setIn(new FileInputStream(FileDescriptor.in));
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
    }
    protected static void setStreams() {
        System.setIn(new BufferedInputStream(FCGIInterface.request.inStream, 8192));
        System.setOut(new PrintStream(new BufferedOutputStream(FCGIInterface.request.outStream, 8192)));
        System.setErr(new PrintStream(new BufferedOutputStream(FCGIInterface.request.errStream, 512)));
    }
}
