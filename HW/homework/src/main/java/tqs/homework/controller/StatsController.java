package tqs.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.homework.statistics.Stats;
import java.util.Map;


@RestController
@RequestMapping("/stats")
@CrossOrigin
public class StatsController {
    private final Logger logger = LoggerFactory.getLogger(StatsController.class);

    @GetMapping()
    public Map<String, Integer> getStats() {
        logger.info("Stats get");
        return Stats.getInstance().toHashMap();
    }
}
