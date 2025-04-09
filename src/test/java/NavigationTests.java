import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    static String navigationLink = "//h5[text()='Chapter 3. WebDriver Fundamentals']/../a[contains(@href,'navigation1')]";


    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();
        driver.findElement(By.xpath(navigationLink)).click();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void previousButtonTest() {
        WebElement previousButton = driver.findElement(By.xpath("//a[text()='Previous']/.."));
        WebElement firstPageButton = driver.findElement(By.xpath("//a[text()='Previous']/../following-sibling::li/a[text()='1']/.."));

        boolean isPreviousButtonDisabled = previousButton.getDomAttribute("class").contains("disabled");
        boolean isFirstPageButtonActive = firstPageButton.getDomAttribute("class").contains("active");
        boolean isPreviousButtonDisabledOnFirstPage = isFirstPageButtonActive && isPreviousButtonDisabled;

        driver.findElement(By.xpath("//a[text()='Next']")).click();

        previousButton = driver.findElement(By.xpath("//a[text()='Previous']/.."));
        WebElement secondPageButton = driver.findElement(By.xpath("//a[text()='Previous']/../following-sibling::li/a[text()='2']/.."));

        isPreviousButtonDisabled = previousButton.getDomAttribute("class").contains("disabled");
        boolean isSecondPageButtonActive = secondPageButton.getDomAttribute("class").contains("active");
        boolean isPreviousButtonDisabledOnSecondPage = isSecondPageButtonActive && isPreviousButtonDisabled;

        assertAll(
                () -> assertTrue(isPreviousButtonDisabledOnFirstPage),
                () -> assertFalse(isPreviousButtonDisabledOnSecondPage)
        );
    }

    @Test
    public void nextButtonTest() {
        WebElement lastPageButton = driver.findElement(By.xpath("//a[text()='Previous']/../following-sibling::li/a[text()='3']/.."));
        lastPageButton.click();

        WebElement nextButtonOnLastPage = driver.findElement(By.xpath("//a[text()='Next']/.."));
        WebElement activePageButton = driver.findElement(By.xpath("//a[text()='Previous']/../following-sibling::li/a[text()='3']/.."));

        boolean nextDisabledOnLastPage = nextButtonOnLastPage.getDomAttribute("class").contains("disabled");
        boolean isLastPageActive = activePageButton.getDomAttribute("class").contains("active");

        boolean isNextButtonDisabledOnLastPage = isLastPageActive && nextDisabledOnLastPage;

        WebElement previousButton = driver.findElement(By.xpath("//a[text()='Previous']"));
        previousButton.click();

        WebElement nextButtonOnSecondPage = driver.findElement(By.xpath("//a[text()='Next']/.."));
        WebElement secondPageButton = driver.findElement(By.xpath("//a[text()='Previous']/../following-sibling::li/a[text()='2']/.."));

        boolean nextDisabledOnSecondPage = nextButtonOnSecondPage.getDomAttribute("class").contains("disabled");
        boolean isSecondPageActive = secondPageButton.getDomAttribute("class").contains("active");

        boolean isNextButtonDisabledOnSecondPage = isSecondPageActive && nextDisabledOnSecondPage;

        assertAll(
                () -> assertTrue(isNextButtonDisabledOnLastPage),
                () -> assertFalse(isNextButtonDisabledOnSecondPage)
        );
    }
}
