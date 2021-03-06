package org.fundacionjala.salesforce.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.fundacionjala.core.config.TestExecutionProperties;
import org.fundacionjala.core.config.TestPropertiesSetter;
import org.fundacionjala.core.selenium.interaction.WebDriverManager;
import org.fundacionjala.core.throwables.PropertiesReadingException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

/**
 * Cucumber TestNG runner class.
 */
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"org.fundacionjala.salesforce.cucumber"},
        plugin = {"pretty", "json:build/cucumber-reports/Cucumber.json"}
)

public final class Runner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    /**
     * Executes code before all scenarios.
     */
    @BeforeTest
    public void beforeAllScenarios() throws PropertiesReadingException {
        TestExecutionProperties.setRootPath("../salesforce-core/");
        TestPropertiesSetter.setDataProviderThreadCountProp();
        TestPropertiesSetter.setTestBrowser();
    }

    /**
     * Executes code after all scenarios.
     */
    @AfterTest
    public void afterAllScenarios() {
        WebDriverManager.getInstance().quit();
    }
}
