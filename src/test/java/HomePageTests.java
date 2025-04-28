import config.TestPropertiesConfig;
import constants.Constants;
import org.aeonbits.owner.ConfigFactory;
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

class HomePageTests {
    WebDriver driver;
    TestPropertiesConfig testConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get(testConfig.getBaseUrl());
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(Constants.WEB_FORM_PAGE_PATH, "web-form.html", "Web form"),
                Arguments.of(Constants.NAVIGATION_PAGE_PATH, "navigation1.html", "Navigation example"),
                Arguments.of(Constants.DROPDOWN_MENU_PAGE_PATH, "dropdown-menu.html", "Dropdown menu"),
                Arguments.of(Constants.MOUSE_OVER_PAGE_PATH, "mouse-over.html", "Mouse over"),
                Arguments.of(Constants.DRAG_AND_DROP_PAGE_PATH, "drag-and-drop.html", "Drag and drop"),
                Arguments.of(Constants.DRAW_IN_CANVAS_PAGE_PATH, "draw-in-canvas.html", "Drawing in canvas"),
                Arguments.of(Constants.LOADING_IMAGES_PAGE_PATH, "loading-images.html", "Loading images"),
                Arguments.of(Constants.SLOW_CALCULATOR_PAGE_PATH, "slow-calculator.html", "Slow calculator"),
                Arguments.of(Constants.LONG_PAGE_PAGE_PATH, "long-page.html", "This is a long page"),
                Arguments.of(Constants.INFINITE_SCROLL_PAGE_PATH, "infinite-scroll.html", "Infinite scroll"),
                Arguments.of(Constants.SHADOW_DOM_PAGE_PATH, "shadow-dom.html", "Shadow DOM"),
                Arguments.of(Constants.COOKIES_PAGE_PATH, "cookies.html", "Cookies"),
                Arguments.of(Constants.IFRAMES_PAGE_PATH, "iframes.html", "IFrame"),
                Arguments.of(Constants.DIALOG_BOXES_PAGE_PATH, "dialog-boxes.html", "Dialog boxes"),
                Arguments.of(Constants.WEB_STORAGE_PAGE_PATH, "web-storage.html", "Web storage"),
                Arguments.of(Constants.GEOLOCATION_PAGE_PATH, "geolocation.html", "Geolocation"),
                Arguments.of(Constants.NOTIFICATIONS_PAGE_PATH, "notifications.html", "Notifications"),
                Arguments.of(Constants.GET_USER_MEDIA_PAGE_PATH, "get-user-media.html", "Get user media"),
                Arguments.of(Constants.MULTILANGUAGE_PAGE_PATH, "multilanguage.html", ""),
                Arguments.of(Constants.CONSOLE_LOGS_PAGE_PATH, "console-logs.html", "Console logs"),
                Arguments.of(Constants.LOGIN_FORM_PAGE_PATH, "login-form.html", "Login form"),
                Arguments.of(Constants.LOGIN_SLOW_PAGE_PATH, "login-slow.html", "Slow login form"),
                Arguments.of(Constants.RANDOM_CALCULATOR_PAGE_PATH, "random-calculator.html", "Random calculator"),
                Arguments.of(Constants.DOWNLOAD_PAGE_PATH, "download.html", "Download files"),
                Arguments.of(Constants.AB_TESTING_PAGE_PATH, "ab-testing.html", "A/B Testing"),
                Arguments.of(Constants.DATA_TYPES_PAGE_PATH, "data-types.html", "Data types")
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void linksTest(String link, String webFormUrl, String expectedPageTitle) {
        driver.findElement(By.xpath(link)).click();
        String currentUrl = driver.getCurrentUrl();
        WebElement actualTitle = driver.findElement(By.className("display-6"));

        assertEquals(testConfig.getBaseUrl() + webFormUrl, currentUrl);
        assertEquals(expectedPageTitle, actualTitle.getText());
    }

    @Test
    void frameTest() {
        driver.findElement(By.xpath(Constants.FRAMES_PAGE_PATH)).click();
        String currentUrl = driver.getCurrentUrl();
        WebElement frame = driver.findElement(By.cssSelector("frame[name='frame-header']"));
        driver.switchTo().frame(frame);
        WebElement actualTitle = driver.findElement(By.className("display-6"));

        assertEquals(testConfig.getBaseUrl() + "frames.html", currentUrl);
        assertEquals("Frames", actualTitle.getText());
    }
}
