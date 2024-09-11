package se.ifmo.ru.server;

import com.fastcgi.FCGIInterface;
import se.ifmo.ru.handlers.FCGIHandler;
import se.ifmo.ru.http.HttpResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FCGIServer {
    public void run(FCGIHandler handler) {
        var fcgi = new FCGIInterface();
        while (fcgi.FCGIaccept() >= 0) {
            var startTime = System.nanoTime();
            var response = new HttpResponse();
            var request = FCGIHelper.parse();
            FCGIHelper.resetStreams();
            handler.serve(response, request);
            FCGIHelper.setStreams();
            setDate(response);
            var stopTime = System.nanoTime();
            response.setHeader("FastCGI-Exec-Time", "" + (stopTime - startTime));
            System.out.println(response.getRaw());
        }
    }
    private void setDate(HttpResponse response) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        response.setHeader("Date", dateFormat.format(new Date()));
    }
}
