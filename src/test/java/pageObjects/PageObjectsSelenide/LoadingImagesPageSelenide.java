package pageObjects.PageObjectsSelenide;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

public class LoadingImagesPageSelenide {
    @Step("Find image by its name")
    public SelenideElement getImageByImageName(String imageName) {
        return $("#" + imageName).should(exist, Duration.ofSeconds(10));
    }
}
