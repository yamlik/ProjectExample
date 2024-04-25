package com..cucumber.apisteps;

import com..test.context.ScenarioContext;
import com..test.proxy.ZedAttackProxyHandler;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.SSLConfig;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class BaseAPI {

    protected ScenarioContext scenarioContext = ScenarioContext.getTSNTance();
    private EnvironmentVariables environmentVariables = null;

    /**
     * Method to handle basic RequestSpecification by relaxing HTTPS/SSL validation to allow uncertified connections
     * If Security testing is enabled, then requests will be proxied through OWASP via port allocation
     *
     * @return RequestSpecification for RESTAssured with the desired configurations
     */
    public RequestSpecification request() {
        if (environmentVariables == null) {
            environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        }
        RequestSpecification httpRequest =  RestAssured.given()
                .config(RestAssured.config().sslConfig( new SSLConfig().relaxedHTTPSValidation()))
                .config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .relaxedHTTPSValidation();
        if (environmentVariables.getProperty("local.browser.security") != null &&
                environmentVariables.getProperty("local.browser.security").equalsIgnoreCase("true")) {
            httpRequest.proxy(new ZedAttackProxyHandler().getZedHostPort());
        }
        return httpRequest;
    }
}
