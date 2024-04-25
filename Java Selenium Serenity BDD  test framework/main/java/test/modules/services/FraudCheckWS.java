package com..test.modules.services;

import com..test.clients.TAFSCLIENT;
import net.serenitybdd.rest.SerenityRest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class FraudCheckWS extends TAFSCLIENT {

    private final String fcwsURL = fcwsBaseURI+"productUnderTestFraudCheck.asmx";

    public void sendRequest(String request) {
        request=request.replace("{{current_date}}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        request=request.replace("{{organisation}}", scenarioContext.getOrganisation());
        request=request.replace("{{country}}", scenarioContext.getCountry());

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/xml");
        headers.put("SOAPAction", "\"http://dectechsolutions.com/productUnderTest/productUnderTestFraudCheck_String\"");
        SerenityRest.given()
                .headers(headers)
                .body("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-TSNTance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "    <soap:Body>\n" +
                        "        <productUnderTestFraudCheck_String xmlns=\"http://dectechsolutions.com/productUnderTest\">\n" +
                        "            <inputString>"+request+"</inputString>\n" +
                        "        </productUnderTestFraudCheck_String>\n" +
                        "    </soap:Body>\n" +
                        "</soap:Envelope>")
                .post(fcwsURL)
                .then()
                .statusCode(200)
                .log()
        .body();
    }

}
