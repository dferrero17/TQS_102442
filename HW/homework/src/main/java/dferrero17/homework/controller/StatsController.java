package dferrero17.homework.controller;

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
    @GetMapping()
    public HashMap<String, Integer> getStats() {
        return Stats.getInstance().toHashMap();
    }
}
