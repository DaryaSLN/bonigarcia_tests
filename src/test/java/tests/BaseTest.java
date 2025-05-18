package tests;

import config.TestPropertiesConfig;
import extensions.AllureExtension;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static patterns.WebDriverFactory.createWebDriver;

@ExtendWith(AllureExtension.class)
public abstract class BaseTest {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    TestPropertiesConfig testConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());
    WebDriverWait wait1;
    WebDriverWait wait5;
    WebDriverWait wait10;
    WebDriverWait wait20;
    JavascriptExecutor js;

    @BeforeEach
    void setup() {
        WebDriver driver = createWebDriver(testConfig.getBrowser());
        driverThreadLocal.set(driver);

        driver.get(testConfig.getBaseUrl());
        driver.manage().window().maximize();

        wait1 = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait5 = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait10 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait20 = new WebDriverWait(driver, Duration.ofSeconds(20));

        js = (JavascriptExecutor) driver;
    }

    @AfterEach
    void tearDown() {
        WebDriver driver = getDriver();
        if (driver != null) {
            try {
                driver.getPageSource();
            } catch (Exception ignored) {
            }
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
}
