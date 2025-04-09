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


public class DragAndDropTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    static String dragAndDropLink = "//h5[text()='Chapter 3. WebDriver Fundamentals']/../a[contains(@href,'drag-and-drop')]";


    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();
        driver.findElement(By.xpath(dragAndDropLink)).click();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void dragAndDropTest() {
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
