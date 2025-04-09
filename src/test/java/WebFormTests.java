import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebFormTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    static String webFormLink = "//h5[text()='Chapter 3. WebDriver Fundamentals']/../a[contains(@href,'web-form')]";
    String text = "Nostrud enim officia veniam aute. Occaecat consectetur commodo deserunt consectetur anim excepteur " +
            "ad qui mollit Lorem mollit ipsum. Et excepteur voluptate pariatur minim dolore sunt adipisicing magna et " +
            "ea reprehenderit sint. Nulla qui sint occaecat deserunt minim nulla enim consectetur voluptate ad fugiat " +
            "laboris nisi. Ullamco est et ex cillum sit.\n" + "Esse quis magna pariatur reprehenderit pariatur laborum " +
            "dolor. Nostrud magna voluptate elit dolor nostrud ullamco elit anim culpa aliquip. Mollit do non sunt " +
            "pariatur dolore incididunt veniam aliquip. Consectetur exercitation non tempor eiusmod eiusmod. Et in " +
            "occaecat eu commodo ut et reprehenderit do.";

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();
        driver.findElement(By.xpath(webFormLink)).click();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void textInputTest() {
        String inputText = "Lorem ipsum 123#$().;";

        WebElement textInputField = driver.findElement(By.id("my-text-id"));
        textInputField.sendKeys(inputText);
        String actualText = textInputField.getAttribute("value");

        assertEquals(inputText, actualText);
    }

    @Test
    public void editTextInputTest() {
        String initialText = "Lorem ipsum 123#$().;";
        String editedText = "Edited Text";

        WebElement textInputField = driver.findElement(By.id("my-text-id"));
        textInputField.sendKeys(initialText);
        textInputField.clear();
        textInputField.sendKeys(editedText);
        String actualText = textInputField.getAttribute("value");

        assertEquals(editedText, actualText);
    }

    @Test
    public void passwordInputTest() {
        String inputPassword = "Lorem123#$().;";

        WebElement textInputField = driver.findElement(By.xpath("//input[@type='password']"));
        textInputField.sendKeys(inputPassword);
        String actualPassword = textInputField.getAttribute("value");

        assertEquals(inputPassword, actualPassword);
    }

    @Test
    public void textareaInputTest() {
        WebElement textInputField = driver.findElement(By.name("my-textarea"));
        textInputField.sendKeys(text);

        String initialFieldHeight = textInputField.getCssValue("height");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.height = '300px';", textInputField);

        String actualFieldHeight = textInputField.getCssValue("height");
        String actualText = textInputField.getAttribute("value");

        assertAll(
                () -> assertEquals(text, actualText),
                () -> assertNotEquals(initialFieldHeight, actualFieldHeight),
                () -> assertEquals("300px", actualFieldHeight)
        );
    }

    @Test
    public void disabledInputTest() {
        WebElement disabledInputField = driver.findElement(By.cssSelector("[name='my-disabled']"));

        assertAll(
                () -> assertFalse(disabledInputField.isEnabled()),
                () -> assertEquals("Disabled input", disabledInputField.getDomAttribute("placeholder")),
                () -> assertThrows(ElementNotInteractableException.class, () -> disabledInputField.sendKeys("text"))
        );
    }

    @Test
    public void readOnlyTest() {
        WebElement readonlyInputField = driver.findElement(By.name("my-readonly"));

        assertAll(
                () -> assertTrue(readonlyInputField.isEnabled()),
                () -> assertNotNull(readonlyInputField.getDomAttribute("readonly"))
        );
    }

    @Test
    public void optionsListTest() {
        List<String> expectedSelectOptions = new ArrayList<>(Arrays.asList("One", "Two", "Three"));

        List<String> actualSelectOptions = driver.findElements(By.xpath("//select[@name='my-select']/option/following-sibling::option"))
                .stream().map(WebElement::getText).toList();

        assertEquals(expectedSelectOptions, actualSelectOptions);
    }

    @Test
    public void dropdownDefaultOptionTest() {
        String expectedSelectedOption = "Open this select menu";

        WebElement formSelect = driver.findElement(By.cssSelector(".form-select"));
        Select select = new Select(formSelect);
        String actualSelectedOption = select.getFirstSelectedOption().getText();

        assertEquals(expectedSelectedOption, actualSelectedOption);
    }

    @Test
    public void dropdownSelectTest() {
        String expectedSelectedOption = "Three";

        WebElement formSelect = driver.findElement(By.cssSelector(".form-select"));
        Select select = new Select(formSelect);
        select.selectByValue("3");
        String actualSelectedOption = select.getFirstSelectedOption().getText();

        assertEquals(expectedSelectedOption, actualSelectedOption);
    }

    @Test
    public void dropdownDatalistTest() {
        List<String> expectedDatalistOptions = new ArrayList<>(Arrays.asList("San Francisco", "New York", "Seattle", "Los Angeles", "Chicago"));

        List<String> actualDatalistOptions = driver.findElements(By.xpath("//datalist/option"))
                .stream().map(option -> option.getDomAttribute("value")).toList();

        assertEquals(expectedDatalistOptions, actualDatalistOptions);
    }

    @Test
    public void fileInputTest() throws URISyntaxException, InterruptedException {
        String fileName = "2016516223709_2.jpg";

        URL resource = HomePageTests.class.getClassLoader().getResource("images/" + fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        }
        File file = new File(resource.toURI());

        WebElement fileInput = driver.findElement(By.name("my-file"));
        fileInput.sendKeys(file.getAbsolutePath());
        boolean isFileDownloaded = fileInput.getAttribute("value").contains(fileName);

        driver.findElement(By.xpath("//button[text()='Submit']")).click();
        Thread.sleep(1000);
        String formInfo = driver.findElement(By.xpath("//div[@class='row']/div/h1")).getText();
        String formResult = driver.findElement(By.xpath("//div/p")).getText();

        assertAll(
                () -> assertTrue(isFileDownloaded),
                () -> assertThat(driver.getCurrentUrl()).contains(fileName),
                () -> assertEquals("Form submitted", formInfo),
                () -> assertEquals("Received!", formResult)
        );
    }

    @Test
    public void checkboxTest() {
        WebElement checkedCheckbox = driver.findElement(By.cssSelector("#my-check-1"));
        checkedCheckbox.click();
        WebElement defaultCheckbox = driver.findElement(By.cssSelector("#my-check-2"));
        defaultCheckbox.click();

        assertAll(
                () -> assertFalse(checkedCheckbox.isSelected()),
                () -> assertTrue(defaultCheckbox.isSelected())
        );
    }

    @Test
    public void selectorsTest() {
        WebElement checkedRadio = driver.findElement(By.cssSelector("#my-radio-1"));
        WebElement defaultRadio = driver.findElement(By.cssSelector("#my-radio-2"));

        boolean isCheckedByDefault = checkedRadio.isSelected();

        defaultRadio.click();
        boolean isDefaultRadioSelected = defaultRadio.isSelected();

        assertAll(
                () -> assertTrue(isCheckedByDefault),
                () -> assertTrue(isDefaultRadioSelected)
        );
    }

    @Test
    public void ColorPickerTest() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement colorPicker = driver.findElement(By.name("my-colors"));
        String initColor = colorPicker.getDomAttribute("value");

        Color yellow = new Color(241, 245, 15, 1);
        String script = String.format("arguments[0].setAttribute('value', '%s');", yellow.asHex());
        js.executeScript(script, colorPicker);

        String finalColor = colorPicker.getDomAttribute("value");

        assertAll(
                () -> assertThat(finalColor).isNotEqualTo(initColor),
                () -> assertThat(Color.fromString(finalColor)).isEqualTo(yellow)
        );
    }

    @Test
    public void datePickerTest() {
        String expectedDate = "04/01/2025";

        WebElement datePicker = driver.findElement(By.name("my-date"));
        datePicker.sendKeys(expectedDate);

        assertEquals(expectedDate, datePicker.getDomProperty("value"));
    }

    @Test
    public void rangePickerTest() {
        WebElement exampleRange = driver.findElement(By.xpath("//input[@type='range']"));
        String expectedRangePickerValue = String.valueOf(Integer.parseInt(exampleRange.getDomAttribute("value")) + 2);

        for (int i = 0; i < 2; i++) {
            exampleRange.sendKeys(Keys.ARROW_RIGHT);
        }
        String actualRangePickerValue = exampleRange.getDomProperty("value");

        assertEquals(expectedRangePickerValue, actualRangePickerValue);
    }

    @Test
    public void movingRangePickerTest() {
        int incrementValue = 3;
        WebElement exampleRange = driver.findElement(By.xpath("//input[@type='range']"));
        String expectedRangePickerValue = String.valueOf(Integer.parseInt(exampleRange.getDomAttribute("value")) + incrementValue);

        int elementWidth = exampleRange.getSize().getWidth();
        int resizeOffset = elementWidth / 10 * incrementValue;

        new Actions(driver)
                .clickAndHold(exampleRange)
                .moveByOffset(resizeOffset, 0)
                .release()
                .perform();
        String actualRangePickerValue = exampleRange.getDomProperty("value");

        assertEquals(expectedRangePickerValue, actualRangePickerValue);
    }

    @Test
    public void returnToIndexLinkTest() {
        String expectedHeader = "Hands-On Selenium WebDriver with Java";

        driver.findElement(By.partialLinkText("Return")).click();
        String actualPageHeader = driver.findElement(By.cssSelector("h1")).getText();

        assertEquals(expectedHeader, actualPageHeader);
    }

    @Test
    public void BoniGarciaLinkTest() throws InterruptedException {
        String expectedHeader = "About Me";

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[contains(text(),'Boni')]")).click();

        String actualPageHeader = driver.findElement(By.cssSelector("h1")).getText();

        assertEquals(expectedHeader, actualPageHeader);
    }
}
