package pt.ua.deti.tqs.carsservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        testEntityManager.clear();
    }

    @Test
    @DisplayName("findCarById() with existent id")
    void findByCarIdTest_success() {

        Car car = new Car("Opel", "Astra");
        Long carId = (Long) testEntityManager.persistAndGetId(car);

        Car loadedCar = carRepository.findByCarId(carId);

        Assertions.assertThat(loadedCar)
                .isNotNull()
                .isEqualTo(car);

    }

    @Test
    @DisplayName("findCarById() with nonexistent id")
    void findByCarIdTest_error() {

        Car loadedCar = carRepository.findByCarId(-10L);

        Assertions.assertThat(loadedCar).isNull();

    }

    @Test
    void findAllTest() {

        Car car1 = new Car("Opel", "Astra");
        Car car2 = new Car("Renault", "Clio");
        Car car3 = new Car("Citroen", "C3");

        testEntityManager.persist(car1);
        testEntityManager.persist(car2);

        testEntityManager.flush();

        List<Car> allCars = carRepository.findAll();

        Assertions.assertThat(allCars).hasSize(2);
        Assertions.assertThat(allCars.get(0)).isEqualTo(car1);
        Assertions.assertThat(allCars.get(1)).isEqualTo(car2);

        testEntityManager.persistAndFlush(car3);

        allCars = carRepository.findAll();

        Assertions.assertThat(allCars).hasSize(3);
        Assertions.assertThat(allCars.get(0)).isEqualTo(car1);
        Assertions.assertThat(allCars.get(1)).isEqualTo(car2);
        Assertions.assertThat(allCars.get(2)).isEqualTo(car3);

        testEntityManager.clear();

    }

}