package tests;

import components.HeaderComponent;
import constants.Constants;
import org.junit.jupiter.api.Test;
import pageObjects.HomePage;
import pageObjects.WebFormPage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HeaderTests extends BaseTest {
    @Test
    void HeaderTitleTest() {
        final String expectedHeaderTitleText = "Hands-On Selenium WebDriver with Java";

        String actualHeaderTitleText = new HomePage(getDriver())
                .navigateToPage(Constants.WEB_FORM_PAGE_PATH, WebFormPage.class)
                .getHeader()
                .getHeaderTitleText();

        assertEquals(expectedHeaderTitleText, actualHeaderTitleText);
    }

    @Test
    void HeaderDescriptionTest() {
        final String expectedHeaderDescriptionText = "Practice site";

        String actualHeaderDescriptionText = new HomePage(getDriver())
                .navigateToPage(Constants.WEB_FORM_PAGE_PATH, WebFormPage.class)
                .getHeader()
                .getHeaderDescriptionText();

        assertEquals(expectedHeaderDescriptionText, actualHeaderDescriptionText);
    }

    @Test
    void logoTest() {
        final int expectedLogoWidth = 80;
        final int expectedLogoHeight = 80;
        final String expectedGitHubName = "bonigarcia";

        HeaderComponent headerComponent = new HomePage(getDriver())
                .navigateToPage(Constants.WEB_FORM_PAGE_PATH, WebFormPage.class)
                .getHeader();

        int actualImageWidth = headerComponent.getLogoWidth();
        int actualImageHeight = headerComponent.getLogoHeight();
        String actualGitHubName = headerComponent
                .clickLogoLink()
                .getGitHubName();

        assertAll(
                () -> assertEquals(expectedLogoWidth, actualImageWidth),
                () -> assertEquals(expectedLogoHeight, actualImageHeight),
                () -> assertEquals(expectedGitHubName, actualGitHubName)
        );
    }
}
