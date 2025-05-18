package patterns;

import config.TestPropertiesConfig;
import constants.Browsers;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class WebDriverFactory {
    public static WebDriver createWebDriver(String browser) {
        WebDriver driver = switch (Browsers.fromString(browser)) {
            case CHROME -> getChromeDriver();
            case FIREFOX -> new FirefoxDriver();
            case EDGE -> new EdgeDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver getChromeDriver() {
        WebDriver driver;
        TestPropertiesConfig testConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());
        String remoteUrl = testConfig.getRemoteUrl();

        if (remoteUrl != null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed URL for Selenium Remote WebDriver", e);
            }
        } else {
            driver = new ChromeDriver();
        }
        return driver;
    }
}
