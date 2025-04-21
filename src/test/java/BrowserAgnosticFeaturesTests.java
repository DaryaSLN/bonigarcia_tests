import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrowserAgnosticFeaturesTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        driver.getPageSource();
        driver.quit();
    }

    @Test
    public void infiniteScrollTest() {
        driver.findElement(By.xpath("//a[@href='infinite-scroll.html']")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By pLocator;
        int initParagraphsNumber = 0;
        int targetParagraphsNumber = 120;
        WebElement lastParagraph;

        while (initParagraphsNumber < targetParagraphsNumber) {
            pLocator = By.tagName("p");
            List<WebElement> paragraphs = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pLocator, 0));
            initParagraphsNumber = paragraphs.size();

            lastParagraph = driver.findElement(By.xpath(String.format("//p[%d]", initParagraphsNumber)));
            String script = "arguments[0].scrollIntoView();";
            js.executeScript(script, lastParagraph);

            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pLocator, initParagraphsNumber));
        }

        String targetText = driver.findElement(By.xpath(String.format("//p[%d]", targetParagraphsNumber))).getText();

        assertThat(targetText).contains("Magnis feugiat natoque");
    }

    @Test
    public void shadowDomTest() {
        driver.findElement(By.xpath("//a[@href='shadow-dom.html']")).click();

        WebElement content = driver.findElement(By.id("content"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement textElement = shadowRoot.findElement(By.cssSelector("p"));

        assertThat(textElement.getText()).contains("Hello Shadow DOM");
    }

    @Test
    public void cookieTest() {
        driver.findElement(By.xpath("//a[@href='cookies.html']")).click();

        WebDriver.Options options = driver.manage();
        Set<Cookie> cookies = options.getCookies();
        int initialCookiesSize = cookies.size();

        Cookie username = options.getCookieNamed("username");
        String cookieUsername = username.getValue();
        String cookieUsernamePath = username.getPath();

        driver.findElement(By.id("refresh-cookies")).click();

        Cookie newCookie = new Cookie("new-cookie-key", "new-cookie-value");
        options.addCookie(newCookie);
        String newCookieValue = options.getCookieNamed(newCookie.getName()).getValue();

        cookies = options.getCookies();
        int newCookiesSize = cookies.size();

        driver.findElement(By.id("refresh-cookies")).click();

        options.deleteCookie(username);
        int cookiesSizeAfterDelete = options.getCookies().size();

        driver.findElement(By.id("refresh-cookies")).click();

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
    public void iFrameTest() {
        driver.findElement(By.xpath("//a[@href='iframes.html']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        WebElement iFrameElement = driver.findElement(By.id("my-iframe"));
        driver.switchTo().frame(iFrameElement);
        String actualText = driver.findElement(By.xpath("//p[@class='lead']")).getText();

        assertThat(actualText).contains("Lorem ipsum dolor sit amet");
    }

    @Test
    public void launchAlertDialogBoxTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.xpath("//a[@href='dialog-boxes.html']")).click();

        WebElement launchAlert = driver.findElement(By.id("my-alert"));
        launchAlert.click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();

        assertAll(
                () -> assertEquals("Hello world!", alertText),
                () -> assertThat(launchAlert.getText()).isEqualTo("Launch alert")
        );
    }

    @Test
    public void acceptLaunchConfirmDialogBoxTest() {
        driver.findElement(By.xpath("//a[@href='dialog-boxes.html']")).click();

        driver.findElement(By.id("my-confirm")).click();
        driver.switchTo().alert().accept();
        String acceptanceMessage = driver.findElement(By.id("confirm-text")).getText();

        assertEquals("You chose: true", acceptanceMessage);
    }

    @Test
    public void cancelLaunchConfirmDialogBoxTest() {
        driver.findElement(By.xpath("//a[@href='dialog-boxes.html']")).click();

        driver.findElement(By.id("my-confirm")).click();
        driver.switchTo().alert().dismiss();
        String cancelMessage = driver.findElement(By.id("confirm-text")).getText();

        assertEquals("You chose: false", cancelMessage);
    }

    @Test
    public void acceptLaunchPromptDialogBoxTest() {
        driver.findElement(By.xpath("//a[@href='dialog-boxes.html']")).click();

        driver.findElement(By.id("my-prompt")).click();
        driver.switchTo().alert().sendKeys("Test");
        driver.switchTo().alert().accept();

        assertThat(driver.findElement(By.id("prompt-text")).getText()).isEqualTo("You typed: Test");
    }

    @Test
    public void cancelLaunchPromptDialogBoxTest() {
        driver.findElement(By.xpath("//a[@href='dialog-boxes.html']")).click();

        driver.findElement(By.id("my-prompt")).click();
        driver.switchTo().alert().sendKeys("Test");
        driver.switchTo().alert().dismiss();

        assertThat(driver.findElement(By.id("prompt-text")).getText()).isEqualTo("You typed: null");
    }

    @Test
    public void saveLaunchModalDialogBoxTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.xpath("//a[@href='dialog-boxes.html']")).click();

        driver.findElement(By.id("my-modal")).click();
        WebElement saveButton = driver.findElement(By.xpath("//button[normalize-space()='Save changes']"));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();

        assertThat(driver.findElement(By.id("modal-text")).getText()).isEqualTo("You chose: Save changes");
    }

    @Test
    public void closeLaunchModalDialogBoxTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.xpath("//a[@href='dialog-boxes.html']")).click();

        driver.findElement(By.id("my-modal")).click();
        WebElement closeButton = driver.findElement(By.xpath("//button[text()='Close']"));
        wait.until(ExpectedConditions.elementToBeClickable(closeButton));
        closeButton.click();

        assertThat(driver.findElement(By.id("modal-text")).getText()).isEqualTo("You chose: Close");
    }

    @Test
    public void localStorageTest() {
        driver.findElement(By.xpath("//a[@href='web-storage.html']")).click();
        WebStorage webStorage = (WebStorage) driver;

        LocalStorage localStorage = webStorage.getLocalStorage();
        driver.findElement(By.id("display-session")).click();
        String localStorageText = driver.findElement(By.id("local-storage")).getText();

        assertAll(
                () -> assertThat(localStorage.size()).isEqualTo(0),
                () -> assertEquals("", localStorageText)
        );
    }

    @Test
    public void storageTest() {
        driver.findElement(By.xpath("//a[@href='web-storage.html']")).click();
        WebStorage webStorage = (WebStorage) driver;

        SessionStorage sessionStorage = webStorage.getSessionStorage();
        sessionStorage.setItem("new element", "new value");
        driver.findElement(By.id("display-session")).click();
        String sessionStorageText = driver.findElement(By.id("session-storage")).getText();

        assertThat(sessionStorage.size()).isEqualTo(3);
        assertAll(
                () -> assertThat(sessionStorage.size()).isEqualTo(3),
                () -> assertEquals("{\"lastname\":\"Doe\",\"name\":\"John\",\"new element\":\"new value\"}", sessionStorageText)
        );
    }
}
