package com..cucumber.apisteps.soap;

import com..cucumber.apisteps.BaseAPI;
import com..test.utilities.Properties;
import com.google.gson.Gson;
import io.restassured.response.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseSOAPSteps extends BaseAPI {
    protected final Properties properties = new Properties("predator.properties");
    protected final String dataDir = System.getProperty("user.dir")+"/src/main/resources/data/API/soap/";
    protected final String baseURI = properties.getProperty("predator.api.soap.baseURI");
    protected static final Gson GSON = new Gson();
    protected Response response = null;

    protected Map<String, String> setHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/xml");
        headers.put("SOAPAction", "http://.co.uk/Predator/IPredatorWebService/ProcessTransaction");
        return headers;
    }

    protected String getDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

}
