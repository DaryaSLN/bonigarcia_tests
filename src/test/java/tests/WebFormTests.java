package tests;

import constants.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

class WebFormTests extends BaseTest {
    String text = "Nostrud enim officia veniam aute. Occaecat consectetur commodo deserunt consectetur anim excepteur " +
            "ad qui mollit Lorem mollit ipsum. Et excepteur voluptate pariatur minim dolore sunt adipisicing magna et " +
            "ea reprehenderit sint. Nulla qui sint occaecat deserunt minim nulla enim consectetur voluptate ad fugiat " +
            "laboris nisi. Ullamco est et ex cillum sit.\n" + "Esse quis magna pariatur reprehenderit pariatur laborum " +
            "dolor. Nostrud magna voluptate elit dolor nostrud ullamco elit anim culpa aliquip. Mollit do non sunt " +
            "pariatur dolore incididunt veniam aliquip. Consectetur exercitation non tempor eiusmod eiusmod. Et in " +
            "occaecat eu commodo ut et reprehenderit do.";

    @BeforeEach
    void setup() {
        super.setup();
        getDriver().findElement(By.linkText(Constants.WEB_FORM_PAGE_LINKTEXT)).click();
    }

    @Test
    void textInputTest() {
        String inputText = "Lorem ipsum 123#$().;";

        WebElement textInputField = getDriver().findElement(By.id("my-text-id"));
        textInputField.sendKeys(inputText);
        String actualText = textInputField.getAttribute("value");

        assertEquals(inputText, actualText);
    }

    @Test
    void editTextInputTest() {
        String initialText = "Lorem ipsum 123#$().;";
        String editedText = "Edited Text";

        WebElement textInputField = getDriver().findElement(By.id("my-text-id"));
        textInputField.sendKeys(initialText);
        textInputField.clear();
        textInputField.sendKeys(editedText);
        String actualText = textInputField.getAttribute("value");

        assertEquals(editedText, actualText);
    }

    @Test
    void passwordInputTest() {
        String inputPassword = "Lorem123#$().;";

        WebElement textInputField = getDriver().findElement(By.xpath("//input[@type='password']"));
        textInputField.sendKeys(inputPassword);
        String actualPassword = textInputField.getAttribute("value");

        assertEquals(inputPassword, actualPassword);
    }

    @Test
    void textareaInputTest() {
        WebElement textInputField = getDriver().findElement(By.name("my-textarea"));
        textInputField.sendKeys(text);

        String initialFieldHeight = textInputField.getCssValue("height");
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
    void disabledInputTest() {
        WebElement disabledInputField = getDriver().findElement(By.cssSelector("[name='my-disabled']"));

        assertAll(
                () -> assertFalse(disabledInputField.isEnabled()),
                () -> assertEquals("Disabled input", disabledInputField.getDomAttribute("placeholder")),
                () -> assertThrows(ElementNotInteractableException.class, () -> disabledInputField.sendKeys("text"))
        );
    }

    @Test
    void readOnlyTest() {
        WebElement readonlyInputField = getDriver().findElement(By.name("my-readonly"));

        assertAll(
                () -> assertTrue(readonlyInputField.isEnabled()),
                () -> assertNotNull(readonlyInputField.getDomAttribute("readonly"))
        );
    }

    @Test
    void optionsListTest() {
        List<String> expectedSelectOptions = new ArrayList<>(Arrays.asList("One", "Two", "Three"));

        List<String> actualSelectOptions = getDriver().findElements(By.xpath("//select[@name='my-select']/option/following-sibling::option"))
                .stream().map(WebElement::getText).toList();

        assertEquals(expectedSelectOptions, actualSelectOptions);
    }

    @Test
    void dropdownDefaultOptionTest() {
        String expectedSelectedOption = "Open this select menu";

        WebElement formSelect = getDriver().findElement(By.cssSelector(".form-select"));
        Select select = new Select(formSelect);
        String actualSelectedOption = select.getFirstSelectedOption().getText();

        assertEquals(expectedSelectedOption, actualSelectedOption);
    }

    @Test
    void dropdownSelectTest() {
        String expectedSelectedOption = "Three";

        WebElement formSelect = getDriver().findElement(By.cssSelector(".form-select"));
        Select select = new Select(formSelect);
        select.selectByValue("3");
        String actualSelectedOption = select.getFirstSelectedOption().getText();

        assertEquals(expectedSelectedOption, actualSelectedOption);
    }

    @Test
    void dropdownDatalistTest() {
        List<String> expectedDatalistOptions = new ArrayList<>(Arrays.asList("San Francisco", "New York", "Seattle", "Los Angeles", "Chicago"));

        List<String> actualDatalistOptions = getDriver().findElements(By.xpath("//datalist/option"))
                .stream().map(option -> option.getDomAttribute("value")).toList();

        assertEquals(expectedDatalistOptions, actualDatalistOptions);
    }

    @Test
    void fileInputTest() throws URISyntaxException {
        String fileName = "2016516223709_2.jpg";

        URL resource = WebFormTests.class.getClassLoader().getResource("images/" + fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        }
        File file = new File(resource.toURI());

        WebElement fileInput = getDriver().findElement(By.name("my-file"));
        fileInput.sendKeys(file.getAbsolutePath());
        boolean isFileDownloaded = fileInput.getAttribute("value").contains(fileName);

        getDriver().findElement(By.xpath("//button[text()='Submit']")).click();

        String formResult = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/p")))
                .getText();
        String formInfo = getDriver().findElement(By.xpath("//div[@class='row']/div/h1")).getText();

        assertAll(
                () -> assertTrue(isFileDownloaded),
                () -> assertThat(getDriver().getCurrentUrl()).contains(fileName),
                () -> assertEquals("Received!", formResult),
                () -> assertEquals("Form submitted", formInfo)
        );
    }

    @Test
    void checkboxTest() {
        WebElement checkedCheckbox = getDriver().findElement(By.cssSelector("#my-check-1"));
        checkedCheckbox.click();
        WebElement defaultCheckbox = getDriver().findElement(By.cssSelector("#my-check-2"));
        defaultCheckbox.click();

        assertAll(
                () -> assertFalse(checkedCheckbox.isSelected()),
                () -> assertTrue(defaultCheckbox.isSelected())
        );
    }

    @Test
    void selectorsTest() {
        WebElement checkedRadio = getDriver().findElement(By.cssSelector("#my-radio-1"));
        WebElement defaultRadio = getDriver().findElement(By.cssSelector("#my-radio-2"));

        boolean isCheckedByDefault = checkedRadio.isSelected();

        defaultRadio.click();
        boolean isDefaultRadioSelected = defaultRadio.isSelected();

        assertAll(
                () -> assertTrue(isCheckedByDefault),
                () -> assertTrue(isDefaultRadioSelected)
        );
    }

    @Test
    void ColorPickerTest() {
        WebElement colorPicker = getDriver().findElement(By.name("my-colors"));
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
    void datePickerTest() {
        String expectedDate = "04/01/2025";

        WebElement datePicker = getDriver().findElement(By.name("my-date"));
        datePicker.sendKeys(expectedDate);

        assertEquals(expectedDate, datePicker.getDomProperty("value"));
    }

    @Test
    void rangePickerTest() {
        WebElement exampleRange = getDriver().findElement(By.xpath("//input[@type='range']"));
        String expectedRangePickerValue = String.valueOf(Integer.parseInt(exampleRange.getDomAttribute("value")) + 2);

        for (int i = 0; i < 2; i++) {
            exampleRange.sendKeys(Keys.ARROW_RIGHT);
        }
        String actualRangePickerValue = exampleRange.getDomProperty("value");

        assertEquals(expectedRangePickerValue, actualRangePickerValue);
    }

    @Test
    void movingRangePickerTest() {
        int incrementValue = 3;
        WebElement exampleRange = getDriver().findElement(By.xpath("//input[@type='range']"));
        String expectedRangePickerValue = String.valueOf(Integer.parseInt(exampleRange.getDomAttribute("value")) + incrementValue);

        int elementWidth = exampleRange.getSize().getWidth();
        int resizeOffset = elementWidth / 10 * incrementValue;

        new Actions(getDriver())
                .clickAndHold(exampleRange)
                .moveByOffset(resizeOffset, 0)
                .release()
                .perform();
        String actualRangePickerValue = exampleRange.getDomProperty("value");

        assertEquals(expectedRangePickerValue, actualRangePickerValue);
    }

    @Test
    void returnToIndexLinkTest() {
        String expectedHeader = "Hands-On Selenium WebDriver with Java";

        getDriver().findElement(By.partialLinkText("Return")).click();
        String actualPageHeader = getDriver().findElement(By.cssSelector("h1")).getText();

        assertEquals(expectedHeader, actualPageHeader);
    }

    @Test
    void BoniGarciaLinkTest() {
        String expectedHeader = "About Me";

        WebElement boniLink = getDriver().findElement(By.xpath("//a[contains(text(),'Boni')]"));
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", boniLink);
        wait1.until(ExpectedConditions.elementToBeClickable(boniLink));
        js.executeScript("arguments[0].click();", boniLink);

        String actualPageHeader = getDriver().findElement(By.cssSelector("h1")).getText();

        assertEquals(expectedHeader, actualPageHeader);
    }
}
