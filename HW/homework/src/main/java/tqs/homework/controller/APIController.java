package tqs.homework.controller;


import tqs.homework.api.mapquest.MapQuestData;
import tqs.homework.api.openweather.OpenWeatherComponent;
import tqs.homework.api.openweather.OpenWeatherData;
import tqs.homework.services.APIService;
import tqs.homework.services.GeoCodingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.homework.services.ReverseGeoCodingService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class APIController {
    private final APIService aqService;
    private final GeoCodingService geoService;
    private final Logger logger = LoggerFactory.getLogger(APIController.class);
    private final ReverseGeoCodingService reverseGeoCodingService;

    public APIController(APIService aqService, GeoCodingService geoService, ReverseGeoCodingService reverseGeoCodingService) {
        this.aqService = aqService;
        this.geoService = geoService;
        logger.info("APIController Initialized");
        this.reverseGeoCodingService = reverseGeoCodingService;
    }

    @GetMapping("/airquality/now")
    public ResponseEntity<OpenWeatherComponent> getAirQualityNow(@RequestParam Double lat, @RequestParam Double lon) {
        OpenWeatherComponent cmp = aqService.getMostRecentAirQuality(lat, lon);
        if (cmp == null) {
            logger.info("OpenWeather API Now failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("OpenWeather API Now called");
        return ResponseEntity.ok(cmp);
    }

    @GetMapping("/airquality/history")
    public ResponseEntity<OpenWeatherData> getAirQualityForecast(@RequestParam Double lat, @RequestParam Double lon) {
        OpenWeatherData data = aqService.getAirQuality(lat, lon);
        if (data == null) {
            logger.info("OpenWeather API History failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("OpenWeather API History called");
        return ResponseEntity.ok(data);
    }

    @GetMapping("/city")
    public ResponseEntity<MapQuestData> getCity(@RequestParam Double lat, @RequestParam Double lon) {
        MapQuestData data = geoService.getGeoCoding(lat, lon);
        if (data == null) {
            logger.info("MapQuest API failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("MapQuest API called");
        return ResponseEntity.ok(data);
    }

    @GetMapping("/city/{location}")
    public ResponseEntity<MapQuestLocationData> getCity(@RequestParam String location) {
        MapQuestLocationData data = reverseGeoCodingService.getReverseGeoCoding(location);
        if (data == null) {
            logger.info("MapQuest API failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("MapQuest API called");
        return ResponseEntity.ok(data);
    }
}
