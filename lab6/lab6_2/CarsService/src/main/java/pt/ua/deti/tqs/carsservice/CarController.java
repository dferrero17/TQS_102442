package pt.ua.deti.tqs.carsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarManagerService carManagerService;

    @PostMapping("/create")
    public ResponseEntity<Car> createCar(@RequestBody CarDTO car) {
        return ResponseEntity.ok().body(carManagerService.save(car.toDataEntity()));
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Long carId) {
        return carManagerService.getCarDetails(carId).map(car -> ResponseEntity.ok().body(car)).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
