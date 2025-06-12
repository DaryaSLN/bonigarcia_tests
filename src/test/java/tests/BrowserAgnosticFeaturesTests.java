package tests;

import constants.Constants;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BrowserAgnosticFeaturesTests extends BaseTest {
    @Test
    void infiniteScrollTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.INFINITE_SCROLL_PAGE_LINKTEXT)));

        By pLocator;
        int initParagraphsNumber = 0;
        int targetParagraphsNumber = 120;
        WebElement lastParagraph;

        while (initParagraphsNumber < targetParagraphsNumber) {
            pLocator = By.tagName("p");
            List<WebElement> paragraphs = wait20.until(ExpectedConditions.numberOfElementsToBeMoreThan(pLocator, 0));
            initParagraphsNumber = paragraphs.size();

            lastParagraph = getDriver().findElement(By.xpath(String.format("//p[%d]", initParagraphsNumber)));
            String script = "arguments[0].scrollIntoView();";
            js.executeScript(script, lastParagraph);

            wait20.until(ExpectedConditions.numberOfElementsToBeMoreThan(pLocator, initParagraphsNumber));
        }

        String targetText = getDriver().findElement(By.xpath(String.format("//p[%d]", targetParagraphsNumber))).getText();

        assertThat(targetText).contains("Magnis feugiat natoque");
    }

    @Test
    void shadowDomTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.SHADOW_DOM_PAGE_LINKTEXT)));

        WebElement content = getDriver().findElement(By.id("content"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement textElement = shadowRoot.findElement(By.cssSelector("p"));

        assertThat(textElement.getText()).contains("Hello Shadow DOM");
    }

    @Test
    void cookieTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.COOKIES_PAGE_LINKTEXT)));

        WebDriver.Options options = getDriver().manage();
        Set<Cookie> cookies = options.getCookies();
        int initialCookiesSize = cookies.size();

        Cookie username = options.getCookieNamed("username");
        String cookieUsername = username.getValue();
        String cookieUsernamePath = username.getPath();

        getDriver().findElement(By.id("refresh-cookies")).click();

        Cookie newCookie = new Cookie("new-cookie-key", "new-cookie-value");
        options.addCookie(newCookie);
        String newCookieValue = options.getCookieNamed(newCookie.getName()).getValue();

        cookies = options.getCookies();
        int newCookiesSize = cookies.size();

        getDriver().findElement(By.id("refresh-cookies")).click();

        options.deleteCookie(username);
        int cookiesSizeAfterDelete = options.getCookies().size();

        getDriver().findElement(By.id("refresh-cookies")).click();

        assertAll(
                () -> assertEquals(2, initialCookiesSize),
                () -> assertEquals("John Doe", cookieUsername),
                () -> assertEquals("/", cookieUsernamePath),
                () -> assertEquals("new-cookie-value", newCookieValue),
                () -> assertEquals(3, newCookiesSize),
                () -> assertEquals(2, cookiesSizeAfterDelete)
        );
    }

    @Test
    void iFrameTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.IFRAMES_PAGE_LINKTEXT)));

        WebElement iFrameElement = wait5.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.id("my-iframe"))));
        wait1.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrameElement));
        String actualText = getDriver().findElement(By.xpath("//p[@class='lead']")).getText();

        assertThat(actualText).contains("Lorem ipsum dolor sit amet");
    }

    @Test
    void launchAlertDialogBoxTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.DIALOG_BOXES_PAGE_LINKTEXT)));

        WebElement launchAlert = getDriver().findElement(By.id("my-alert"));
        launchAlert.click();
        wait5.until(ExpectedConditions.alertIsPresent());
        Alert alert = getDriver().switchTo().alert();
        String alertText = alert.getText();
        alert.accept();

        assertAll(
                () -> assertEquals("Hello world!", alertText),
                () -> assertThat(launchAlert.getText()).isEqualTo("Launch alert")
        );
    }

    @Test
    void acceptLaunchConfirmDialogBoxTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.DIALOG_BOXES_PAGE_LINKTEXT)));

        getDriver().findElement(By.id("my-confirm")).click();
        getDriver().switchTo().alert().accept();
        String acceptanceMessage = getDriver().findElement(By.id("confirm-text")).getText();

        assertEquals("You chose: true", acceptanceMessage);
    }

    @Test
    void cancelLaunchConfirmDialogBoxTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.DIALOG_BOXES_PAGE_LINKTEXT)));

        getDriver().findElement(By.id("my-confirm")).click();
        getDriver().switchTo().alert().dismiss();
        String cancelMessage = getDriver().findElement(By.id("confirm-text")).getText();

        assertEquals("You chose: false", cancelMessage);
    }

    @Test
    void acceptLaunchPromptDialogBoxTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.DIALOG_BOXES_PAGE_LINKTEXT)));

        getDriver().findElement(By.id("my-prompt")).click();
        getDriver().switchTo().alert().sendKeys("Test");
        getDriver().switchTo().alert().accept();

        assertThat(getDriver().findElement(By.id("prompt-text")).getText()).isEqualTo("You typed: Test");
    }

    @Test
    void cancelLaunchPromptDialogBoxTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.DIALOG_BOXES_PAGE_LINKTEXT)));

        getDriver().findElement(By.id("my-prompt")).click();
        getDriver().switchTo().alert().sendKeys("Test");
        getDriver().switchTo().alert().dismiss();

        assertThat(getDriver().findElement(By.id("prompt-text")).getText()).isEqualTo("You typed: null");
    }

    @Test
    void saveLaunchModalDialogBoxTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.DIALOG_BOXES_PAGE_LINKTEXT)));

        getDriver().findElement(By.id("my-modal")).click();
        WebElement saveButton = getDriver().findElement(By.xpath("//button[normalize-space()='Save changes']"));
        wait5.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();

        assertThat(getDriver().findElement(By.id("modal-text")).getText()).isEqualTo("You chose: Save changes");
    }

    @Test
    void closeLaunchModalDialogBoxTest() {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.DIALOG_BOXES_PAGE_LINKTEXT)));

        getDriver().findElement(By.id("my-modal")).click();
        WebElement closeButton = getDriver().findElement(By.xpath("//button[text()='Close']"));
        wait5.until(ExpectedConditions.elementToBeClickable(closeButton));
        closeButton.click();

        assertThat(getDriver().findElement(By.id("modal-text")).getText()).isEqualTo("You chose: Close");
    }

    @Disabled
    @Test
    void localStorageTest() {
        WebStorage webStorage = (WebStorage) getDriver();

        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.WEB_STORAGE_PAGE_LINKTEXT)));

        LocalStorage localStorage = webStorage.getLocalStorage();
        getDriver().findElement(By.id("display-session")).click();
        String localStorageText = getDriver().findElement(By.id("local-storage")).getText();

        assertAll(
                () -> assertThat(localStorage.size()).isEqualTo(0),
                () -> assertEquals("", localStorageText)
        );
    }

    @Disabled
    @Test
    void storageTest() {
        WebStorage webStorage = (WebStorage) getDriver();

        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(Constants.WEB_STORAGE_PAGE_LINKTEXT)));

        SessionStorage sessionStorage = webStorage.getSessionStorage();
        sessionStorage.setItem("new element", "new value");
        getDriver().findElement(By.id("display-session")).click();
        String sessionStorageText = getDriver().findElement(By.id("session-storage")).getText();

        assertThat(sessionStorage.size()).isEqualTo(3);
        assertAll(
                () -> assertThat(sessionStorage.size()).isEqualTo(3),
                () -> assertEquals("{\"lastname\":\"Doe\",\"name\":\"John\",\"new element\":\"new value\"}", sessionStorageText)
        );
    }
}
