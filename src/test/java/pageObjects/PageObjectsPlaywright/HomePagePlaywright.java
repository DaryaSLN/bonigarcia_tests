package pageObjects.PageObjectsPlaywright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import constants.Constants;

public class HomePagePlaywright {
    private final Page page;
    private final Locator loginFormLink;

    public HomePagePlaywright(Page page) {
        this.page = page;
        this.loginFormLink = page.locator("a:has-text('" + Constants.LOGIN_FORM_PAGE_LINKTEXT + "')");
    }

    public LoginFormPagePlaywright navigateToLoginFormPage() {
        loginFormLink.click();
        return new LoginFormPagePlaywright(page);
    }
}
