package com..cucumber.hooks;
import org.apache.commons.io.FileUtils;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.ZoneId;
import static net.serenitybdd.rest.RestRequests.given;
public class ImportTestResultXray {

    public static void main(String[] args) {

        init();
    }

     static String aTSNTssToken = "";
    @PostConstruct
    static private  void init() {
        File updatedTemplate = getUpdateTemplate();
        given()
                .urlEncodingEnabled(false)
                .contentType("multipart/form-data")
                .baseUri("https://jira.plc.com/")
                .basePath("rest/raven/1.0/import/execution/cucumber/multipart")
                .auth()
                .oauth2(aTSNTssToken)
                .multiPart("info", updatedTemplate)
                .multiPart("result", new File(Paths.get(Paths.get("").toString(), "target", "cucumber.json").toString()))
                .when()
                .post().then().assertThat().statusCode(200);
    }

    static private  File getUpdateTemplate(){
        File executionTemplate = new File(Paths.get(Paths.get("").toString(), "src", "test", "resources", "executionTemplate.json").toString());
        File updatedTemplate = new File(Paths.get(Paths.get("").toString(), "src", "test", "resources", "UpdatedTemplate.json").toString());
        ZoneId zoneId = ZoneId.of("GMT+5");
        try {
            String data = FileUtils.readFileToString(executionTemplate, StandardCharsets.UTF_8);
           data = data.replaceAll("TODAYDATE", java.time.LocalDate.now(zoneId).toString());
            FileUtils.writeStringToFile(updatedTemplate, data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updatedTemplate;
    }
}