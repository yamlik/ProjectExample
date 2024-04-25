package com..cucumber.runners;

import com..test.proxy.ZedAttackProxyHandler;
import com..test.utilities.Properties;
import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        plugin = {"pretty", "json:target/cucumber.json"},
        glue = {"com..cucumber", "com..test.steps"},
        tags = {"@API"}
)

public class APISuite {

    static final Logger log = LoggerFactory.getLogger(UISuite.class);

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
}
