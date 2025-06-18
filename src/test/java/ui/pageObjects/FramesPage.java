package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FramesPage extends BasePage {
    @FindBy(css = "frame[name='frame-header']")
    private WebElement frame;

    public FramesPage(WebDriver driver) {
        super(driver);
    }

    @Step("Switch to frame")
    public FramesPage switchToFrame() {
        getDriver().switchTo().frame(frame);
        return this;
    }
}
