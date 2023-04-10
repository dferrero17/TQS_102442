package pt.ua.deti.tqs.carsservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService carManagerService;

    @Test
    void createCar() throws Exception {

        Car car = new Car("Opel", "Astra");

        Mockito.when(carManagerService.save(Mockito.any())).thenReturn(car);

        mvc.perform(post("/api/create").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(car)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is("Opel")))
                .andExpect(jsonPath("$.model", is("Astra")));

        Mockito.verify(carManagerService, Mockito.times(1)).save(Mockito.any(Car.class));

    }

    @Test
    void getAllCars() throws Exception {

        Car car1 = new Car("Opel", "Astra");
        Car car2 = new Car("Renault", "Clio");

        Mockito.when(carManagerService.getAllCars()).thenReturn(Arrays.asList(car1, car2));

        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].maker", is("Opel")))
                .andExpect(jsonPath("$.[0].model", is("Astra")))
                .andExpect(jsonPath("$.[1].maker", is("Renault")))
                .andExpect(jsonPath("$.[1].model", is("Clio")));

        Mockito.verify(carManagerService, Mockito.times(1)).getAllCars();

    }

    @Test
    void getCarById() throws Exception {

        Car car = new Car("Opel", "Astra");

        Mockito.when(carManagerService.getCarDetails(1L)).thenReturn(Optional.of(car));

        mvc.perform(get("/api/car/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is("Opel")))
                .andExpect(jsonPath("$.model", is("Astra")));

        Mockito.verify(carManagerService, Mockito.times(1)).getCarDetails(Mockito.anyLong());

    }

}