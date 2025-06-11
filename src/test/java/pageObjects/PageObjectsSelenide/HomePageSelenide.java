package pageObjects.PageObjectsSelenide;

import constants.Constants;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class HomePageSelenide {
    @Step("Navigate to LoadingImagesPage")
    public LoadingImagesPageSelenide navigateToLoadingImagesPage() {
        $(By.linkText(Constants.LOADING_IMAGES_PAGE_LINKTEXT)).click();
        return new LoadingImagesPageSelenide();
    }
}
