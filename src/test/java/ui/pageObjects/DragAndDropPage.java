package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class DragAndDropPage extends BasePage {
    @FindBy(id = "draggable")
    private WebElement draggable;

    @FindBy(id = "target")
    private WebElement target;

    public DragAndDropPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get location of the draggable element")
    public Point getDraggableLocation() {
        return draggable.getLocation();
    }

    @Step("Get location of the target element")
    public Point getTargetLocation() {
        return target.getLocation();
    }

    @Step("Drag and drop a draggable element")
    public DragAndDropPage dragAndDropElement() {
        new Actions(getDriver())
                .dragAndDrop(draggable, target)
                .perform();
        return this;
    }
}
