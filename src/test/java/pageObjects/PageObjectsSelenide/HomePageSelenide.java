package pageObjects.PageObjectsSelenide;

import constants.Constants;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class HomePageSelenide {
    @Step("Navigate to LoadingImagesPage")
    public LoadingImagesPageSelenide navigateToLoadingImagesPage() {
        executeJavaScript("arguments[0].click();", $(By.linkText(Constants.LOADING_IMAGES_PAGE_LINKTEXT)));
        return new LoadingImagesPageSelenide();
    }
}
