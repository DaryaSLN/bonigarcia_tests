package ui.tests;

import ui.constants.Constants;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NavigationTests extends BaseTest {
    @Test
    void previousButtonTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.NAVIGATION_PAGE_LINKTEXT)));
        WebElement previousButton = getDriver().findElement(By.xpath("//a[text()='Previous']/.."));
        WebElement firstPageButton = getDriver().findElement(By.xpath("//a[text()='Previous']/../following-sibling::li/a[text()='1']/.."));

        boolean isPreviousButtonDisabled = previousButton.getDomAttribute("class").contains("disabled");
        boolean isFirstPageButtonActive = firstPageButton.getDomAttribute("class").contains("active");
        boolean isPreviousButtonDisabledOnFirstPage = isFirstPageButtonActive && isPreviousButtonDisabled;

        getDriver().findElement(By.xpath("//a[text()='Next']")).click();

        previousButton = getDriver().findElement(By.xpath("//a[text()='Previous']/.."));
        WebElement secondPageButton = getDriver().findElement(By.xpath("//a[text()='Previous']/../following-sibling::li/a[text()='2']/.."));

        isPreviousButtonDisabled = previousButton.getDomAttribute("class").contains("disabled");
        boolean isSecondPageButtonActive = secondPageButton.getDomAttribute("class").contains("active");
        boolean isPreviousButtonDisabledOnSecondPage = isSecondPageButtonActive && isPreviousButtonDisabled;

        assertAll(
                () -> assertTrue(isPreviousButtonDisabledOnFirstPage),
                () -> assertFalse(isPreviousButtonDisabledOnSecondPage)
        );
    }

    @Test
    void nextButtonTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.NAVIGATION_PAGE_LINKTEXT)));
        WebElement lastPageButton = getDriver().findElement(By.xpath("//a[text()='Previous']/../following-sibling::li/a[text()='3']/.."));
        lastPageButton.click();

        WebElement nextButtonOnLastPage = getDriver().findElement(By.xpath("//a[text()='Next']/.."));
        WebElement activePageButton = getDriver().findElement(By.xpath("//a[text()='Previous']/../following-sibling::li/a[text()='3']/.."));

        boolean nextDisabledOnLastPage = nextButtonOnLastPage.getDomAttribute("class").contains("disabled");
        boolean isLastPageActive = activePageButton.getDomAttribute("class").contains("active");

        boolean isNextButtonDisabledOnLastPage = isLastPageActive && nextDisabledOnLastPage;

        WebElement previousButton = getDriver().findElement(By.xpath("//a[text()='Previous']"));
        previousButton.click();

        WebElement nextButtonOnSecondPage = getDriver().findElement(By.xpath("//a[text()='Next']/.."));
        WebElement secondPageButton = getDriver().findElement(By.xpath("//a[text()='Previous']/../following-sibling::li/a[text()='2']/.."));

        boolean nextDisabledOnSecondPage = nextButtonOnSecondPage.getDomAttribute("class").contains("disabled");
        boolean isSecondPageActive = secondPageButton.getDomAttribute("class").contains("active");

        boolean isNextButtonDisabledOnSecondPage = isSecondPageActive && nextDisabledOnSecondPage;

        assertAll(
                () -> assertTrue(isNextButtonDisabledOnLastPage),
                () -> assertFalse(isNextButtonDisabledOnSecondPage)
        );
    }
}
