package pt.ua.deti.tqs.carsservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByCarId(Long carId);
    List<Car> findAll();

}
