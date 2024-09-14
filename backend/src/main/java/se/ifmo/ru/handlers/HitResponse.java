package se.ifmo.ru.handlers;
import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({"x", "y", "r", "hit"})
public class HitResponse {
    @JsonProperty("x")
    public double x;

    @JsonProperty("y")
    public double y;

    @JsonProperty("r")
    public double r;

    @JsonProperty("hit")
    public boolean hit;

    public HitResponse(double x, double y, double r, boolean hit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
    }
}
