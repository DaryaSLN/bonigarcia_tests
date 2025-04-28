import config.TestPropertiesConfig;
import constants.Constants;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class DragAndDropTests {
    WebDriver driver;
    TestPropertiesConfig testConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get(testConfig.getBaseUrl());
        driver.manage().window().maximize();
        driver.findElement(By.xpath(Constants.DRAG_AND_DROP_PAGE_PATH)).click();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void dragAndDropTest() {
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("target"));

        Point initialElementLocation = draggable.getLocation();

        new Actions(driver)
                .dragAndDrop(draggable, target)
                .perform();

        Point finalElementLocation = draggable.getLocation();
        Point targetElementLocation = target.getLocation();

        assertAll(
                () -> assertEquals(finalElementLocation, targetElementLocation),
                () -> assertNotEquals(initialElementLocation, finalElementLocation)
        );
    }
}
