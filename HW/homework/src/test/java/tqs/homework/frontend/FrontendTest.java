package tqs.homework.frontend;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:tqs/homework/frontend/features",
        glue = "tqs.homework.frontend.steps"
)
public class FrontendTest {
}

// VAI AO DISCORD JÁ DESCOBRI O PROBLEMA