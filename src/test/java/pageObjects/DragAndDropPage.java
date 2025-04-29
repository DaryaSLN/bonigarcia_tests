package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DragAndDropPage extends BasePage {
    WebElement draggable = getDriver().findElement(By.id("draggable"));
    WebElement target = getDriver().findElement(By.id("target"));

    public DragAndDropPage(WebDriver driver) {
        super(driver);
    }

    public Point getDraggableLocation() {
        return draggable.getLocation();
    }

    public Point getTargetLocation() {
        return target.getLocation();
    }

    public DragAndDropPage dragAndDropElement() {
        new Actions(getDriver())
                .dragAndDrop(draggable, target)
                .perform();
        return this;
    }
}
