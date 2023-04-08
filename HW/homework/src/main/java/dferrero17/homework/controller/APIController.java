package dferrero17.homework.controller;


import dferrero17.homework.api.mapquest.MapQuestData;
import dferrero17.homework.api.openweather.OpenWeatherComponent;
import dferrero17.homework.api.openweather.OpenWeatherData;
import dferrero17.homework.services.APIService;
import dferrero17.homework.services.GeoCodingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class APIController {
    private final APIService aqService;
    private final GeoCodingService geoService;

    public APIController(APIService aqService, GeoCodingService geoService) {
        this.aqService = aqService;
        this.geoService = geoService;
    }

    @GetMapping("/airquality/now")
    public ResponseEntity<OpenWeatherComponent> getAirQualityNow(@RequestParam Double lat, @RequestParam Double lon) {
        OpenWeatherComponent cmp = aqService.getMostRecentAirQuality(lat, lon);
        if (cmp == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(cmp);
    }

    @GetMapping("/airquality/history")
    public ResponseEntity<OpenWeatherData> getAirQualityForecast(@RequestParam Double lat, @RequestParam Double lon) {
        OpenWeatherData data = aqService.getAirQuality(lat, lon);
        if (data == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/city")
    public ResponseEntity<MapQuestData> getCity(@RequestParam Double lat, @RequestParam Double lon) {
        MapQuestData data = geoService.getGeoCoding(lat, lon);
        System.out.println(data);
        if (data == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.ok(data);
    }
}
