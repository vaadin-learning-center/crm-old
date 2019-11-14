package com.vaadin.tutorial.crm.it.util;

import com.vaadin.testbench.IPAddress;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.annotations.RunLocally;
import com.vaadin.testbench.parallel.Browser;
import com.vaadin.testbench.parallel.ParallelTest;

import org.apache.commons.exec.OS;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Base class for TestBench IntegrationTests on Chrome.
 * <p>
 * The tests use Chrome driver (see pom.xml for integration-tests profile) to
 * run integration tests on a headless Chrome. If a property {@code test.use
 * .hub} is set to true, {@code AbstractViewTest} will assume that the TestBench
 * test is running in a CI environment. In order to keep the this class light,
 * it makes certain assumptions about the CI environment (such as available
 * environment variables). It is not advisable to use this class as a base class
 * for you own TestBench tests.
 * <p>
 * To learn more about TestBench, visit
 * <a href= "https://vaadin.com/docs/testbench/testbench-overview.html">Vaadin
 * TestBench</a>.
 */
public abstract class AbstractTest extends ParallelTest {
    private static final String SERVER_HOST = IPAddress.findSiteLocalAddress();
    private static final int SERVER_PORT = 8080;

    private final String route;
    @Rule
    public ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this, true);

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    protected AbstractTest(String route) {
        this.route = route;
    }

    @Before
    public void setup() throws Exception {
        super.setup();
        getDriver().get(getURL(route));
    }

    private static String getURL(String route) {
        return String.format("http://%s:%d/%s", SERVER_HOST, SERVER_PORT, route);
    }

}
