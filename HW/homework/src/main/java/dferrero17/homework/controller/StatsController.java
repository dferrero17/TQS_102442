package dferrero17.homework.controller;

import dferrero17.homework.cache.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dferrero17.homework.statistics.Stats;

import java.util.HashMap;


@RestController
@RequestMapping("/stats")
@CrossOrigin
public class StatsController {
    private final Logger logger = LoggerFactory.getLogger(Storage.class);

    @GetMapping()
    public HashMap<String, Integer> getStats() {
        logger.info("Stats get");
        return Stats.getInstance().toHashMap();
    }
}
