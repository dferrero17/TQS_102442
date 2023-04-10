package pt.ua.deti.tqs.carsservice;

import lombok.Data;

@Data
public class CarDTO {

    private Long carId;

    private String maker;
    private String model;

    public Car toDataEntity() {
        return new Car(maker, model);
    }

}
