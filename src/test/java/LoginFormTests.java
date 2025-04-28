import config.TestPropertiesConfig;
import constants.Constants;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginFormTests {
    WebDriver driver;
    TestPropertiesConfig testConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get(testConfig.getBaseUrl());
        driver.manage().window().maximize();
        driver.findElement(By.xpath(Constants.LOGIN_FORM_PAGE_PATH)).click();
    }

    @AfterEach
    void tearDown() {
        driver.getPageSource();
        driver.quit();
    }

    @Test
    void validCredentialsTest() {
        driver.findElement(By.id("username")).sendKeys(testConfig.getUsername());
        driver.findElement(By.id("password")).sendKeys(testConfig.getPassword());
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement message = driver.findElement(By.className("alert"));

        assertEquals("Login successful", message.getText());
    }

    static Stream<Arguments> invalidCredentials() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("        ", "        "),
                Arguments.of("username", "password")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCredentials")
    void invalidCredentialsTest(String username, String password) {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement message = driver.findElement(By.className("alert"));

        assertEquals("Invalid credentials", message.getText());
    }
}
