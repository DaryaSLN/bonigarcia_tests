package tests;

import constants.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pageObjects.ABTestingPage;
import pageObjects.BasePage;
import pageObjects.ConsoleLogsPage;
import pageObjects.CookiesPage;
import pageObjects.DataTypesPage;
import pageObjects.DialogBoxesPage;
import pageObjects.DownloadFilesPage;
import pageObjects.DragAndDropPage;
import pageObjects.DrawingInCanvasPage;
import pageObjects.DropdownMenuPage;
import pageObjects.FramesPage;
import pageObjects.GeolocationPage;
import pageObjects.GetUserMediaPage;
import pageObjects.HomePage;
import pageObjects.IFramePage;
import pageObjects.InfiniteScrollPage;
import pageObjects.LoadingImagesPage;
import pageObjects.LoginFormPage;
import pageObjects.LongPagePage;
import pageObjects.MouseOverPage;
import pageObjects.MultilanguagePage;
import pageObjects.NavigationPage;
import pageObjects.NotificationsPage;
import pageObjects.RandomCalculatorPage;
import pageObjects.ShadowDomPage;
import pageObjects.SlowCalculatorPage;
import pageObjects.SlowLoginFormPage;
import pageObjects.WebFormPage;
import pageObjects.WebStoragePage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HomePageTests extends BaseTest {
    Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(Constants.WEB_FORM_PAGE_PATH, "web-form.html", "Web form", WebFormPage.class),
                Arguments.of(Constants.NAVIGATION_PAGE_PATH, "navigation1.html", "Navigation example", NavigationPage.class),
                Arguments.of(Constants.DROPDOWN_MENU_PAGE_PATH, "dropdown-menu.html", "Dropdown menu", DropdownMenuPage.class),
                Arguments.of(Constants.MOUSE_OVER_PAGE_PATH, "mouse-over.html", "Mouse over", MouseOverPage.class),
                Arguments.of(Constants.DRAG_AND_DROP_PAGE_PATH, "drag-and-drop.html", "Drag and drop", DragAndDropPage.class),
                Arguments.of(Constants.DRAW_IN_CANVAS_PAGE_PATH, "draw-in-canvas.html", "Drawing in canvas", DrawingInCanvasPage.class),
                Arguments.of(Constants.LOADING_IMAGES_PAGE_PATH, "loading-images.html", "Loading images", LoadingImagesPage.class),
                Arguments.of(Constants.SLOW_CALCULATOR_PAGE_PATH, "slow-calculator.html", "Slow calculator", SlowCalculatorPage.class),
                Arguments.of(Constants.LONG_PAGE_PAGE_PATH, "long-page.html", "This is a long page", LongPagePage.class),
                Arguments.of(Constants.INFINITE_SCROLL_PAGE_PATH, "infinite-scroll.html", "Infinite scroll", InfiniteScrollPage.class),
                Arguments.of(Constants.SHADOW_DOM_PAGE_PATH, "shadow-dom.html", "Shadow DOM", ShadowDomPage.class),
                Arguments.of(Constants.COOKIES_PAGE_PATH, "cookies.html", "Cookies", CookiesPage.class),
                Arguments.of(Constants.IFRAMES_PAGE_PATH, "iframes.html", "IFrame", IFramePage.class),
                Arguments.of(Constants.DIALOG_BOXES_PAGE_PATH, "dialog-boxes.html", "Dialog boxes", DialogBoxesPage.class),
                Arguments.of(Constants.WEB_STORAGE_PAGE_PATH, "web-storage.html", "Web storage", WebStoragePage.class),
                Arguments.of(Constants.GEOLOCATION_PAGE_PATH, "geolocation.html", "Geolocation", GeolocationPage.class),
                Arguments.of(Constants.NOTIFICATIONS_PAGE_PATH, "notifications.html", "Notifications", NotificationsPage.class),
                Arguments.of(Constants.GET_USER_MEDIA_PAGE_PATH, "get-user-media.html", "Get user media", GetUserMediaPage.class),
                Arguments.of(Constants.MULTILANGUAGE_PAGE_PATH, "multilanguage.html", "", MultilanguagePage.class),
                Arguments.of(Constants.CONSOLE_LOGS_PAGE_PATH, "console-logs.html", "Console logs", ConsoleLogsPage.class),
                Arguments.of(Constants.LOGIN_FORM_PAGE_PATH, "login-form.html", "Login form", LoginFormPage.class),
                Arguments.of(Constants.LOGIN_SLOW_PAGE_PATH, "login-slow.html", "Slow login form", SlowLoginFormPage.class),
                Arguments.of(Constants.RANDOM_CALCULATOR_PAGE_PATH, "random-calculator.html", "Random calculator", RandomCalculatorPage.class),
                Arguments.of(Constants.DOWNLOAD_PAGE_PATH, "download.html", "Download files", DownloadFilesPage.class),
                Arguments.of(Constants.AB_TESTING_PAGE_PATH, "ab-testing.html", "A/B Testing", ABTestingPage.class),
                Arguments.of(Constants.DATA_TYPES_PAGE_PATH, "data-types.html", "Data types", DataTypesPage.class)
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void linksTest(String link, String webFormUrl, String expectedPageTitle, Class<? extends BasePage> pageClass) {
        BasePage openedPage = new HomePage(getDriver())
                .navigateToPage(link, pageClass);

        String currentUrl = openedPage.getCurrentUrl();
        String actualPageTitle = openedPage.getPageTitle();

        assertEquals(testConfig.getBaseUrl() + webFormUrl, currentUrl);
        assertEquals(expectedPageTitle, actualPageTitle);
    }

    @Test
    void frameTest() {
        FramesPage framesPage = new HomePage(getDriver())
                .navigateToPage(Constants.FRAMES_PAGE_PATH, FramesPage.class)
                .switchToFrame();

        String currentUrl = framesPage.getCurrentUrl();
        String actualPageTitle = framesPage.getPageTitle();

        assertEquals(testConfig.getBaseUrl() + "frames.html", currentUrl);
        assertEquals("Frames", actualPageTitle);
    }
}
