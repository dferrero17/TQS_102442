package pt.ua.deti.tqs.carsservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarManagerServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @Test
    void save() {

        Car car = new Car("Renault", "Clio");

        Mockito.when(carRepository.save(car)).thenReturn(car);
        assertEquals(car, carManagerService.save(car));
        Mockito.verify(carRepository, Mockito.times(1)).save(Mockito.any());

    }

    @Test
    void getAllCars() {

        Car car1 = new Car("Opel", "Astra");
        Car car2 = new Car("Renault", "Clio");

        Mockito.when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        assertEquals(Arrays.asList(car1, car2), carManagerService.getAllCars());

        Mockito.verify(carRepository, Mockito.times(1)).findAll();

    }

    @Test
    void getCarDetails() {

        Car car = new Car("Renault", "Clio");

        Mockito.when(carRepository.findByCarId(1L)).thenReturn(car);

        Optional<Car> carOptional = carManagerService.getCarDetails(1L);

        assertTrue(carOptional.isPresent());
        assertEquals(car, carOptional.get());

        Mockito.verify(carRepository, Mockito.times(1)).findByCarId(Mockito.anyLong());

    }

}