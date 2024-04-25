package com..cucumber.apisteps.rest;
import com..cucumber.apisteps.BaseAPI;
import com..test.utilities.Properties;
import com..test.utilities.JSONHelper;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import java.util.HashMap;
import java.util.Map;

public class BaseRESTSteps extends BaseAPI {
    public static Map<String, String> headers = new HashMap<>();
    protected final String dataDir = System.getProperty("user.dir") + "/src/main/resources/data/API/rest/";
    protected final Properties properties = new Properties("productUnderTest.properties");
    protected Response response = null;
    protected final String baseURI = System.getProperty("webdriver.base.url")==null?
                                     properties.getProperty("productUnderTest.ui.url"):
                                     System.getProperty("webdriver.base.url");

    /**
     * Method to apply the cookie used for authentication into the request header
     *
     * @param signInResponse The response from the sign in attempt
     */
    public void setAuthHeader(Response signInResponse) throws ParseException {
        String token = JSONHelper.parseJSONObject(signInResponse.getBody().asString()).get("aTSNTss_token").toString();
        headers.put("Authorization", "Bearer " + token);
        headers.put("Content-type", "application/json");
        Assert.assertTrue("Auth cookie has not been added to headers collection.", headers.containsKey("Authorization") && headers.containsValue("Bearer " + token));
    }



}
