import config.TestPropertiesConfig;
import constants.Constants;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DropdownMenuTests {
    WebDriver driver;
    TestPropertiesConfig testConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());
    final List<String> expectedDropdownMenuOptions = List.of("Action", "Another action", "Something else here", "Separated link");

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get(testConfig.getBaseUrl());
        driver.manage().window().maximize();
        driver.findElement(By.xpath(Constants.DROPDOWN_MENU_PAGE_PATH)).click();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void leftClickTest() {
        driver.findElement(By.id("my-dropdown-1")).click();

        List<String> actualDropdownMenuOptions = driver.findElements(By.xpath("//ul[@class='dropdown-menu show']/li"))
                .stream().map(WebElement::getText)
                .filter(text -> !text.isBlank()).toList();

        assertEquals(expectedDropdownMenuOptions, actualDropdownMenuOptions);
    }

    @Test
    void rightClickTest() {
        WebElement rightClickDropdown = driver.findElement(By.id("my-dropdown-2"));
        new Actions(driver)
                .contextClick(rightClickDropdown)
                .perform();

        List<String> actualDropdownMenuOptions = driver.findElements(By.xpath("//ul[@id='context-menu-2']/li"))
                .stream().map(WebElement::getText)
                .filter(text -> !text.isBlank()).toList();

        assertEquals(expectedDropdownMenuOptions, actualDropdownMenuOptions);
    }

    @Test
    void doubleClickTest() {
        WebElement doubleClickDropdown = driver.findElement(By.id("my-dropdown-3"));
        new Actions(driver)
                .doubleClick(doubleClickDropdown)
                .perform();

        List<String> actualDropdownMenuOptions = driver.findElements(By.xpath("//ul[@id='context-menu-3']/li"))
                .stream().map(WebElement::getText)
                .filter(text -> !text.isBlank()).toList();

        assertEquals(expectedDropdownMenuOptions, actualDropdownMenuOptions);
    }
}
