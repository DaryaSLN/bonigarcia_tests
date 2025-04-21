import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LoadingImagesTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        driver.getPageSource();
        driver.quit();
    }

    static Stream<String> testData() {
        return Stream.of("compass", "calendar", "award", "landscape");
    }

    @ParameterizedTest
    @MethodSource("testData")
    public void loadingImagesTest(String imageName) {
        driver.findElement(By.xpath("//a[@href='loading-images.html']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement pageImage = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(imageName)));

        assertThat(pageImage.getDomAttribute("src")).containsIgnoringCase(imageName);
    }
}
