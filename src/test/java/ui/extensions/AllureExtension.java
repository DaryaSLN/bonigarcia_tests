package ui.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ui.steps.AllureSteps;

public class AllureExtension implements AfterTestExecutionCallback {
    AllureSteps allureSteps = new AllureSteps();

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            allureSteps.captureScreenshotSpoiler();
        }
    }
}
