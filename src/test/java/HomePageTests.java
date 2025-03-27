import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePageTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    static String webFormLink = "//h5[text()='Chapter 3. WebDriver Fundamentals']/../a[contains(@href,'web-form')]";
    static String navigationLink = "//h5[text()='Chapter 3. WebDriver Fundamentals']/../a[contains(@href,'navigation1')]";
    static String dropdownMenuLink = "//h5[text()='Chapter 3. WebDriver Fundamentals']/../a[contains(@href,'dropdown-menu')]";
    static String mouseOverLink = "//h5[text()='Chapter 3. WebDriver Fundamentals']/../a[contains(@href,'mouse-over')]";
    static String dragAndDropLink = "//h5[text()='Chapter 3. WebDriver Fundamentals']/../a[contains(@href,'drag-and-drop')]";
    static String drawInCanvasLink = "//h5[text()='Chapter 3. WebDriver Fundamentals']/../a[contains(@href,'draw-in-canvas')]";
    static String loadingImagesLink = "//h5[text()='Chapter 3. WebDriver Fundamentals']/../a[contains(@href,'loading-images')]";
    static String slowCalculatorLink = "//h5[text()='Chapter 3. WebDriver Fundamentals']/../a[contains(@href,'slow-calculator')]";
    static String longPageLink = "//h5[text()='Chapter 4. Browser-Agnostic Features']/../a[contains(@href,'long-page')]";
    static String infiniteScrollLink = "//h5[text()='Chapter 4. Browser-Agnostic Features']/../a[contains(@href,'infinite-scroll')]";
    static String shadowDomLink = "//h5[text()='Chapter 4. Browser-Agnostic Features']/../a[contains(@href,'shadow-dom')]";
    static String cookiesLink = "//h5[text()='Chapter 4. Browser-Agnostic Features']/../a[contains(@href,'cookies')]";
    static String framesLink = "//h5[text()='Chapter 4. Browser-Agnostic Features']/../a[contains(@href,'frames')]";
    static String iframesLink = "//h5[text()='Chapter 4. Browser-Agnostic Features']/../a[contains(@href,'iframes')]";
    static String dialogBoxesLink = "//h5[text()='Chapter 4. Browser-Agnostic Features']/../a[contains(@href,'dialog-boxes')]";
    static String webStorageLink = "//h5[text()='Chapter 4. Browser-Agnostic Features']/../a[contains(@href,'web-storage')]";
    static String geolocationLink = "//h5[text()='Chapter 5. Browser-Specific Manipulation']/../a[contains(@href,'geolocation')]";
    static String notificationsLink = "//h5[text()='Chapter 5. Browser-Specific Manipulation']/../a[contains(@href,'notifications')]";
    static String getUserMediaLink = "//h5[text()='Chapter 5. Browser-Specific Manipulation']/../a[contains(@href,'get-user-media')]";
    static String multilanguageLink = "//h5[text()='Chapter 5. Browser-Specific Manipulation']/../a[contains(@href,'multilanguage')]";
    static String consoleLogsLink = "//h5[text()='Chapter 5. Browser-Specific Manipulation']/../a[contains(@href,'console-logs')]";
    static String loginFormLink = "//h5[text()='Chapter 7. The Page Object Model (POM)']/../a[contains(@href,'login-form')]";
    static String loginSlowLink = "//h5[text()='Chapter 7. The Page Object Model (POM)']/../a[contains(@href,'login-slow')]";
    static String randomCalculatorLink = "//h5[text()='Chapter 8. Testing Framework Specifics']/../a[contains(@href,'random-calculator')]";
    static String downloadLink = "//h5[text()='Chapter 9. Third-Party Integrations']/../a[contains(@href,'download')]";
    static String abTestingLink = "//h5[text()='Chapter 9. Third-Party Integrations']/../a[contains(@href,'ab-testing')]";
    static String dataTypesLink = "//h5[text()='Chapter 9. Third-Party Integrations']/../a[contains(@href,'data-types')]";

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(webFormLink, "web-form.html", "Web form"),
                Arguments.of(navigationLink, "navigation1.html", "Navigation example"),
                Arguments.of(dropdownMenuLink, "dropdown-menu.html", "Dropdown menu"),
                Arguments.of(mouseOverLink, "mouse-over.html", "Mouse over"),
                Arguments.of(dragAndDropLink, "drag-and-drop.html", "Drag and drop"),
                Arguments.of(drawInCanvasLink, "draw-in-canvas.html", "Drawing in canvas"),
                Arguments.of(loadingImagesLink, "loading-images.html", "Loading images"),
                Arguments.of(slowCalculatorLink, "slow-calculator.html", "Slow calculator"),
                Arguments.of(longPageLink, "long-page.html", "This is a long page"),
                Arguments.of(infiniteScrollLink, "infinite-scroll.html", "Infinite scroll"),
                Arguments.of(shadowDomLink, "shadow-dom.html", "Shadow DOM"),
                Arguments.of(cookiesLink, "cookies.html", "Cookies"),
                Arguments.of(iframesLink, "iframes.html", "IFrame"),
                Arguments.of(dialogBoxesLink, "dialog-boxes.html", "Dialog boxes"),
                Arguments.of(webStorageLink, "web-storage.html", "Web storage"),
                Arguments.of(geolocationLink, "geolocation.html", "Geolocation"),
                Arguments.of(notificationsLink, "notifications.html", "Notifications"),
                Arguments.of(getUserMediaLink, "get-user-media.html", "Get user media"),
                Arguments.of(multilanguageLink, "multilanguage.html", ""),
                Arguments.of(consoleLogsLink, "console-logs.html", "Console logs"),
                Arguments.of(loginFormLink, "login-form.html", "Login form"),
                Arguments.of(loginSlowLink, "login-slow.html", "Slow login form"),
                Arguments.of(randomCalculatorLink, "random-calculator.html", "Random calculator"),
                Arguments.of(downloadLink, "download.html", "Download files"),
                Arguments.of(abTestingLink, "ab-testing.html", "A/B Testing"),
                Arguments.of(dataTypesLink, "data-types.html", "Data types")
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    public void linksTest(String link, String webFormUrl, String expectedPageTitle) {
        driver.findElement(By.xpath(link)).click();
        String currentUrl = driver.getCurrentUrl();
        WebElement actualTitle = driver.findElement(By.className("display-6"));

        assertEquals(BASE_URL + webFormUrl, currentUrl);
        assertEquals(expectedPageTitle, actualTitle.getText());
    }

    @Test
    public void frameTest() {
        driver.findElement(By.xpath(framesLink)).click();
        String currentUrl = driver.getCurrentUrl();
        WebElement frame = driver.findElement(By.cssSelector("frame[name='frame-header']"));
        driver.switchTo().frame(frame);
        WebElement actualTitle = driver.findElement(By.className("display-6"));

        assertEquals(BASE_URL + "frames.html", currentUrl);
        assertEquals("Frames", actualTitle.getText());
    }
}
