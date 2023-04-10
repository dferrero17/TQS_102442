package pt.ua.deti.tqs.carsservice;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue
    private Long carId;

    private String maker;
    private String model;

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

}
