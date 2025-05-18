package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import steps.AllureSteps;

import java.io.File;

public class DownloadFilesPage extends BasePage {
    AllureSteps allureSteps = new AllureSteps();

    @FindBy(xpath = "//a[@download='webdrivermanager.png']")
    private WebElement pngLink;

    @FindBy(xpath = "//a[@download='webdrivermanager.pdf']")
    private WebElement pdfLink;

    protected DownloadFilesPage(WebDriver driver) {
        super(driver);
    }

    @Step("Download .png file")
    public File downloadPng() {
        return downloadFile("webdrivermanager.png", pngLink);
    }

    private File downloadFile(String childPathname, WebElement link) {
        File file = new File(".", childPathname);
        allureSteps.download(link.getDomProperty("href"), file);
        return file;
    }

    @Step("Download .pdf file")
    public File downloadPdf() {
        return downloadFile("webdrivermanager.pdf", pdfLink);
    }
}
