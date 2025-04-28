import config.TestPropertiesConfig;
import constants.Constants;
import org.aeonbits.owner.ConfigFactory;
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

class LoadingImagesTests {
    WebDriver driver;
    TestPropertiesConfig testConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());
    WebDriverWait wait10;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get(testConfig.getBaseUrl());
        driver.manage().window().maximize();
        wait10 = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        driver.getPageSource();
        driver.quit();
    }

    static Stream<String> testData() {
        return Stream.of("compass", "calendar", "award", "landscape");
    }

    @ParameterizedTest
    @MethodSource("testData")
    void loadingImagesTest(String imageName) {
        driver.findElement(By.xpath(Constants.LOADING_IMAGES_PAGE_PATH)).click();

        WebElement pageImage = wait10.until(ExpectedConditions.presenceOfElementLocated(By.id(imageName)));

        assertThat(pageImage.getDomAttribute("src")).containsIgnoringCase(imageName);
    }
}
