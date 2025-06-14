package ui.constants;

public enum Browsers {
    CHROME("chrome"), IE("ie"), INTERNET_EXPLORER("internet explorer"), EDGE("edge"), FIREFOX("firefox"), SAFARI("safari");

    private final String browserValue;

    Browsers(String browserValue) {
        this.browserValue = browserValue;
    }

    public static Browsers fromString(String browserName) {
        for (Browsers browser : Browsers.values()) {
            if (browser.browserValue.equalsIgnoreCase(browserName)) {
                return browser;
            }
        }
        throw new IllegalArgumentException("Unsupported browser: " + browserName);
    }
}
