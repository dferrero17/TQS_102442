package pt.ua.deti.tqs.lab7;


import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestAPITest {

    @Test
    void isTodoListAvailable() {
        given().when().get("https://jsonplaceholder.typicode.com/todos").then().statusCode(200);
    }

    @Test
    void isElementFourExpected() {
        given().pathParams("x", 4).when().get("https://jsonplaceholder.typicode.com/todos/{x}").then().body("title", equalTo("et porro tempora"));
    }

    @Test
    void isTodo198and199Available() {
        given().when().get("https://jsonplaceholder.typicode.com/todos").then().body("id", hasItems(198, 199));
    }

}
