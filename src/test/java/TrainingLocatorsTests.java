import config.TestPropertiesConfig;
import constants.Constants;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static patterns.WebDriverFactory.createWebDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TrainingLocatorsTests {
    WebDriver driver;
    TestPropertiesConfig testConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());
    JavascriptExecutor js;
    WebDriverWait wait1;

    @BeforeAll
    void setup() {
        driver = createWebDriver(testConfig.getBrowser());
        driver.get(testConfig.getBaseUrl());
        driver.manage().window().maximize();
        driver.findElement(By.linkText(Constants.WEB_FORM_PAGE_LINKTEXT)).click();

        js = (JavascriptExecutor) driver;

        wait1 = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    @AfterAll
    void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    void headerTest() {
        String pageHeader = driver.findElement(By.className("display-4")).getText();
        String description = driver.findElement(By.tagName("h5")).getText();

        assertAll(
                () -> assertEquals("Hands-On Selenium WebDriver with Java", pageHeader),
                () -> assertEquals("Practice site", description)
        );
    }

    @Test
    @Order(2)
    void logoTest() {
        WebElement image = driver.findElement(By.xpath("//img"));
        int imageWidth = image.getSize().getWidth();
        int imageHeight = image.getSize().getHeight();
        driver.findElement(By.xpath("//a[@href='https://github.com/bonigarcia/selenium-webdriver-java']")).click();
        String gitHubName = driver.findElement(By.linkText("bonigarcia")).getText();
        driver.navigate().back();
        String pageTitle = driver.findElement(By.className("display-6")).getText();

        assertAll(
                () -> assertEquals(80, imageWidth),
                () -> assertEquals(80, imageHeight),
                () -> assertEquals("bonigarcia", gitHubName),
                () -> assertEquals("Web form", pageTitle)
        );
    }

    @Test
    @Order(3)
    void inputTest() {
        driver.findElement(By.id("my-text-id")).sendKeys("123");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123");
        driver.findElement(By.name("my-textarea")).sendKeys("Lorem ipsum.");
        String disabledInput = driver.findElement(By.cssSelector("[name='my-disabled']")).getDomAttribute("placeholder");
        String readonlyInput = driver.findElement(By.name("my-readonly")).getDomAttribute("value");

        assertAll(
                () -> assertEquals("Disabled input", disabledInput),
                () -> assertEquals("Readonly input", readonlyInput)
        );
    }

    @Test
    @Order(4)
    void selectorsTest() throws URISyntaxException {
        List<String> expectedSelectOptions = new ArrayList<>(Arrays.asList("One", "Two", "Three"));
        List<String> expectedDatalistOptions = new ArrayList<>(Arrays.asList("San Francisco", "New York", "Seattle", "Los Angeles", "Chicago"));

        WebElement formSelect = driver.findElement(By.cssSelector(".form-select"));
        formSelect.click();
        List<String> actualSelectOptions = driver.findElements(By.xpath("//select[@name='my-select']/option/following-sibling::option"))
                .stream().map(WebElement::getText).toList();
        Select select = new Select(formSelect);
        select.selectByValue("2");

        driver.findElement(By.cssSelector("input[name='my-datalist']")).sendKeys("Seattle");
        List<String> actualDatalistOptions = driver.findElements(By.xpath("//datalist/option"))
                .stream().map(option -> option.getDomAttribute("value")).toList();

        URL resource = TrainingLocatorsTests.class.getClassLoader().getResource("images/2016516223709_2.jpg");
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        }
        File file = new File(resource.toURI());

        WebElement fileInput = driver.findElement(By.name("my-file"));
        fileInput.sendKeys(file.getAbsolutePath());

        driver.findElement(By.cssSelector("#my-check-1")).click();
        driver.findElement(By.cssSelector("#my-check-2")).click();
        driver.findElement(By.cssSelector("#my-radio-1")).click();
        driver.findElement(By.cssSelector("#my-radio-2")).click();

        assertAll(
                () -> assertEquals(expectedSelectOptions, actualSelectOptions),
                () -> assertEquals(expectedDatalistOptions, actualDatalistOptions)
        );
    }

    @Test
    @Order(5)
    void pickersTest() {
        driver.findElement(By.xpath("//label[contains(text(),'Color picker')]/input")).click();

        WebElement datePicker = driver.findElement(By.xpath("//input [@name='my-date']/ancestor::label"));
        datePicker.click();
        datePicker.sendKeys("04/01/2025");

        WebElement exampleRange = driver.findElement(By.xpath("//input[@type='range']"));
        for (int i = Integer.parseInt(exampleRange.getDomAttribute("value")); i < 10; i++) {
            exampleRange.sendKeys(Keys.ARROW_RIGHT);
        }
        driver.findElement(By.tagName("button")).click();
        String formInfo = driver.findElement(By.xpath("//div[@class='row']/div/h1")).getText();
        String formResult = driver.findElement(By.xpath("//div/p")).getText();
        driver.navigate().back();

        assertAll(
                () -> assertEquals("Form submitted", formInfo),
                () -> assertEquals("Received!", formResult)
        );
    }

    @Test
    @Order(6)
    void linksTest() {
        driver.findElement(By.partialLinkText("Return")).click();
        String pageHeader = driver.findElement(By.cssSelector("h1")).getText();
        driver.navigate().back();

        WebElement boniLink = driver.findElement(By.xpath("//a[contains(text(),'Boni')]"));
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", boniLink);
        wait1.until(ExpectedConditions.elementToBeClickable(boniLink));
        js.executeScript("arguments[0].click();", boniLink);

        String bonigarciaPageHeader = driver.findElement(By.cssSelector("h1")).getText();

        assertAll(
                () -> assertEquals("Hands-On Selenium WebDriver with Java", pageHeader),
                () -> assertEquals("About Me", bonigarciaPageHeader)
        );
    }
}
