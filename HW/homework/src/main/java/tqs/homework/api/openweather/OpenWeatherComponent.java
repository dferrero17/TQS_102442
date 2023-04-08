package tqs.homework.api.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenWeatherComponent {
    private int aqi;
    private double co;
    private double no;
    private double no2;
    private double o3;
    private double so2;
    private double pm2_5;
    private double pm10;
    private double nh3;
    private long dt;
    private final Logger logger = LoggerFactory.getLogger(OpenWeatherComponent.class);

    @JsonProperty("dt")
    private void unpackDt(long dt) {
        this.dt = dt * 1000;
    }

    @JsonProperty("main")
    private void unpackMain(HashMap<String, Integer> main) {
        this.aqi = main.get("aqi");
    }

    @JsonProperty("components")
    private void unpackComponents(HashMap<String, Double> components) {
        this.co = components.get("co");
        this.no = components.get("no");
        this.no2 = components.get("no2");
        this.o3 = components.get("o3");
        this.so2 = components.get("so2");
        this.pm2_5 = components.get("pm2_5");
        this.pm10 = components.get("pm10");
        this.nh3 = components.get("nh3");
        logger.info("OpenWeather Unpacked");
    }

    @Override
    public String toString() {
        return "OpenWeatherComponent{" +
                "aqi=" + aqi +
                ", co=" + co +
                ", no=" + no +
                ", no2=" + no2 +
                ", o3=" + o3 +
                ", so2=" + so2 +
                ", pm2_5=" + pm2_5 +
                ", pm10=" + pm10 +
                ", nh3=" + nh3 +
                ", dt=" + dt +
                '}';
    }
}
