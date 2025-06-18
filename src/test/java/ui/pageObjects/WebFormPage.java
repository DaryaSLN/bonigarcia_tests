package ui.pageObjects;

import ui.components.HeaderComponent;
import org.openqa.selenium.WebDriver;

public class WebFormPage extends BasePage {
    HeaderComponent header;

    public WebFormPage(WebDriver driver) {
        super(driver);
        header = new HeaderComponent(driver);
    }

    public HeaderComponent getHeader() {
        return header;
    }
}
