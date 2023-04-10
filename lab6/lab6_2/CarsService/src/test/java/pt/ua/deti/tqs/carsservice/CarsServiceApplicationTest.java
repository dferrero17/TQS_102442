package pt.ua.deti.tqs.carsservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class CarsServiceApplicationTest {

    @LocalServerPort
    int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    @DisplayName("Create car successfully")
    void createCar_success() {

        Car car = new Car("Opel", "Astra");
        restTemplate.postForEntity("/api/create", car, Car.class);

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).doesNotContainNull().hasSize(1);
        assertThat(allCars).extracting(Car::getMaker).containsOnly("Opel");
        assertThat(allCars).extracting(Car::getModel).containsOnly("Astra");

    }

    @Test
    @DisplayName("List all cars successfully")
    void listAllCars_success() {

        Car car1 = new Car("Opel", "Astra");
        Car car2 = new Car("Renault", "Clio");
        Car car3 = new Car("Citroen", "C3");

        restTemplate.postForEntity("/api/create", car1, Car.class);
        restTemplate.postForEntity("/api/create", car2, Car.class);
        restTemplate.postForEntity("/api/create", car3, Car.class);

        ResponseEntity<Car[]> allCarsResponse = restTemplate.getForEntity("/api/cars", Car[].class);
        Car[] allCars = allCarsResponse.getBody();


        assertThat(allCars).doesNotContainNull().hasSize(3);
        assertThat(allCars).extracting(Car::getMaker).containsOnly("Opel", "Renault", "Citroen");
        assertThat(allCars).extracting(Car::getModel).containsOnly("Astra", "Clio", "C3");
        assertThat(allCars).extracting(Car::getCarId).doesNotContainNull();

    }

    @Test
    @DisplayName("Get Car by id successfully")
    void getCarById_success() {

        Car car1 = new Car("Opel", "Astra");
        ResponseEntity<Car> carResponseEntity = restTemplate.postForEntity("/api/create", car1, Car.class);

        assertThat(carResponseEntity).isNotNull();
        assertThat(carResponseEntity.getBody()).isNotNull();

        Long createdCarId = carResponseEntity.getBody().getCarId();

        ResponseEntity<Car> carByIdResponseEntity = restTemplate.getForEntity(String.format("/api/car/%d", createdCarId), Car.class);

        assertThat(carByIdResponseEntity).isNotNull();
        assertThat(carByIdResponseEntity.getBody()).isNotNull();
        assertThat(carByIdResponseEntity.getBody()).hasFieldOrPropertyWithValue("maker", "Opel");
        assertThat(carByIdResponseEntity.getBody()).hasFieldOrPropertyWithValue("model", "Astra");

    }

    @Test
    @DisplayName("Get Car with nonexistent id")
    void getCarById_error_nonexistentId() {

        ResponseEntity<Car> carByIdResponseEntity = restTemplate.getForEntity(String.format("/api/car/%d", -100), Car.class);

        assertThat(carByIdResponseEntity).isNotNull();
        assertThat(carByIdResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

}
