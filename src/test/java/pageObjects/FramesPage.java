package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FramesPage extends BasePage {
    WebElement frame = getDriver().findElement(By.cssSelector("frame[name='frame-header']"));

    public FramesPage(WebDriver driver) {
        super(driver);
    }

    @Step("Switch to frame")
    public FramesPage switchToFrame() {
        getDriver().switchTo().frame(frame);
        return this;
    }
}
