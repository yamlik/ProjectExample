package com..test.clients;
import com..test.context.constants.ApplicationConstants;
import com..test.context.ScenarioContext;
import com..test.models.HttpRequest;
import com..test.proxy.ZedAttackProxyHandler;
import com..test.utilities.JSONHelper;
import com..test.utilities.PropertiesManager;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import java.util.HashMap;
import java.util.Map;

public class TAFSCLIENT{

    protected final PropertiesManager propertiesManager = PropertiesManager.get();
    protected final String productVersion = propertiesManager.getProperty("productUnderTest.version");
    protected final String fcwsBaseURI=propertiesManager.getProperty("productUnderTest.fcws.url");
    private final String getATSNTssTokenUrl = propertiesManager.getProperty("productUnderTest.api.get.aTSNTss.token.url");
    private final String appKeyValue = propertiesManager.getProperty("is.app.key.value");
    protected final HttpClient httpClient = new HttpClient();
    protected final String environment = propertiesManager.getProperty("productUnderTest.ui.url");

    protected final String applicationServerIpUrl = "http://" + System.getProperty("application.server.ip")  + ":9991/";

    protected Response response = null;
    protected ScenarioContext scenarioContext = ScenarioContext.getTSNTance();
    private EnvironmentVariables environmentVariables = null;
    protected static final Map<String, String> defaultHeaders = new HashMap<>();

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

    protected Map<String, String> defaultHeaders(String aTSNTssToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        if (aTSNTssToken != null) {
            headers.put("Authorization", "Bearer " + aTSNTssToken);
        }
        return headers;
    }

    protected Map<String, String> getATSNTssTokenHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        return headers;
    }

    protected Map<String, String> getQueryParamsDeleteUser(String userName) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", userName);
        return queryParams;
    }

    protected Map<String, String> getQueryParamsDeleteProfile(String profileName) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("profileName", profileName);
        return queryParams;
    }

    protected Map<String, String> createApplicationHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("IS-Api-Key", appKeyValue);
        return headers;
    }

    protected Map<String, String> createServiceOperationHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    protected HttpRequest buildRequest(String uri, String body, Map<String, String> headers,
                                       Map<String, String> queryParams) {
        return HttpRequest.builder().url(environment + uri).headers(headers).queryParams(queryParams).requestBody(body)
                .build();
    }

    protected HttpRequest buildRequestApplication(String uri, String body, Map<String, String> headers,
                                                  Map<String, String> queryParams) {
        return HttpRequest.builder().url(uri).headers(headers).queryParams(queryParams).requestBody(body)
                .build();
    }

    protected HttpRequest buildServiceOperation(String uri, String body, Map<String, String> headers) {
        return HttpRequest.builder().url(applicationServerIpUrl + uri).headers(headers).requestBody(body)
                .build();
    }

    public Response callTokenAPI() {
        String getATSNTssTokenRequestBody = "";
        if (productVersion.equals("")||(productVersion.equals(".0") ))
            getATSNTssTokenRequestBody = ApplicationConstants.ATSNTSS_TOKEN_PLAIN_REQUEST_BODY_6_3;
        if (productVersion.equals(".0")||(productVersion.equals(".2") ))
            getATSNTssTokenRequestBody = ApplicationConstants.ATSNTSS_TOKEN_PLAIN_REQUEST_BODY_6_2;
        response = SerenityRest.given().request().headers(getATSNTssTokenHeader()).body(getATSNTssTokenRequestBody).post(environment+getATSNTssTokenUrl);

        return response;
    }

    public void getATSNTssToken() throws ParseException {
        response = callTokenAPI();
        setAuthHeader(response);

    }

    public void getNonAdminATSNTssToken() throws ParseException {
        String getATSNTssTokenRequestBody = "";
            getATSNTssTokenRequestBody = ApplicationConstants.ATSNTSS_TOKEN_NONADMIN_PLAIN_REQUEST_BODY_6_2;
        response = SerenityRest.given().request().headers(getATSNTssTokenHeader()).body(getATSNTssTokenRequestBody).post(environment+getATSNTssTokenUrl);
        setAuthHeader(response);
    }

    public void setAuthHeader(Response signInResponse) throws ParseException {
        String token = JSONHelper.parseJSONObject(signInResponse.getBody().asString()).get("aTSNTss_token").toString();
        scenarioContext.setATSNTssToken(token);
        defaultHeaders.put("Authorization", "Bearer " + token);
        defaultHeaders.put("Content-type", "application/json");
        Assert.assertTrue("Auth cookie has not been added to headers collection.", defaultHeaders.containsKey("Authorization") && defaultHeaders.containsValue("Bearer " + token));
    }

}
