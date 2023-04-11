package tqs.homework.frontend.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.DriverManager;
import java.time.Duration;
import java.util.List;

public class AirQualityStatsSteps {

    private WebDriver driver;

    @Given("the user navigates to the air quality stats page")
    public void the_user_navigates_to_the_air_quality_stats_page() {
        System.setProperty("webdriver.firefox.driver", "driver/geckodriver");
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("http://localhost:5173/");
    }

    @When("the user selects a location")
    public void the_user_selects_a_location() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Find the input field, type "Aveiro" and submit the form
        WebElement cityInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("cityInput")));
        cityInput.sendKeys("Aveiro");
        cityInput.sendKeys(Keys.ENTER);
    }

    @Then("the air quality stats for that location should be displayed")
    public void the_air_quality_stats_for_that_location_should_be_displayed() {

        // Wait for the stats container to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement stats = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("airq")));

        // Check if the container is displayed
        Assert.assertTrue("Stats container is not displayed", stats.isDisplayed());

        // Verify the presence of individual stats elements
        String[] airQualityKeys = {"co", "no", "no2", "o3", "so2", "pm2_5", "pm10", "nh3"};

        for (String key : airQualityKeys) {
            List<WebElement> statTitles = stats.findElements(By.xpath(".//div[contains(@class, 'stat-title') and contains(text(), '" + key.toUpperCase() + "')]"));
            List<WebElement> statValues = stats.findElements(By.xpath(".//div[contains(@class, 'stat-value') and preceding-sibling::div[contains(text(), '" + key.toUpperCase() + "')]]"));

            Assert.assertFalse("Stat title for " + key + " is not displayed", statTitles.isEmpty());
            Assert.assertFalse("Stat value for " + key + " is not displayed", statValues.isEmpty());
        }

        // Close the browser after the test is finished
        driver.quit();
    }






}
