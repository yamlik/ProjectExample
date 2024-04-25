package com..cucumber.runners;

import com.browserstack.local.Local;
import com..test.proxy.ZedAttackProxyHandler;
import com..test.utilities.Properties;
import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        plugin = {"pretty", "json:target/cucumber.json"},
        glue = {"com..cucumber", "com..test.steps"}
)

public class FullSuite {

    static final Logger log = LoggerFactory.getLogger(FullSuite.class);
    static Local bsLocal;


    public static boolean isTrue(String property) {
        return "true".equalsIgnoreCase(new Properties("serenity.properties").getProperty(property));
    }

    @BeforeClass
    public static void setupZAP() {
        if (isTrue("local.security")) {
            log.info("Attempting ZAP setup");
            new ZedAttackProxyHandler().setAPIForZAP();
            log.info("ZAP setup complete");
        } else {
            log.info("ZAP setup not done");
        }
    }

    @BeforeClass
    public static void setUpBrowserStackLocal() {
        if (isTrue("browserstack.enabled")) {
            bsLocal = new Local();
            Map<String, String> bsLocalArgs = new HashMap<>();
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                bsLocalArgs.put("binarypath", "src/main/resources/BrowserStackLocal.exe");
            } else if (System.getProperty("os.name").toLowerCase().contains("nix") ||
                    System.getProperty("os.name").toLowerCase().contains("nux") ||
                    System.getProperty("os.name").toLowerCase().contains("aix")) {
                bsLocalArgs.put("binarypath", "src/main/resources/BrowserStackLocal");
            } else {
                log.error("No Browserstack executable currently available for \"" + System.getProperty("os.name") + "\" OS. Please try either Browserstack Cloud, Local Webbrowsers or add the relative executable file(s).");
                throw new IllegalArgumentException("No Browserstack executable currently available for \"" + System.getProperty("os.name") + "\" OS. Please try either Browserstack Cloud, Local Webbrowsers or add the relative executable file(s).");
            }
            bsLocalArgs.put("key", new Properties("serenity.properties").getProperty("browserstack.key"));
            bsLocalArgs.put("forcelocal", "true");
            try {
                bsLocal.start(bsLocalArgs);
                log.info("BS local started: " + bsLocal.isRunning());
            } catch (Exception e) {
                log.error(e.getMessage() + "\n" + e.toString());
                Assert.fail("Exception encountered when attempting to startup BrowserStack local: " + e.toString());
            }
        } else {
            log.info("BS local not started");
        }
    }

    @AfterClass
    public static void tearDown() {
        if (bsLocal != null) {
            try {
                bsLocal.stop();
                log.info("BS local stopped");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
