package com..test.utilities;

import com..test.clients.TAFSCLIENT;
import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

public class ServiceHelper extends TAFSCLIENT {
    private final String serviceOperationUrl = propertiesManager.getProperty("qamanager.api.svcmng.endpoint");
    private final String organisationCode = System.getProperty("productUnderTest.organisationundertest") + System.getProperty("productUnderTest.organisation").replaceAll("^.*?_","");
    private final String serviceVersionNumber = System.getProperty("service.version.number");
    public String batchServiceName  = "productUnderTestServiceBatch" +  organisationCode + serviceVersionNumber;
    public String communicationServiceName= "productUnderTestServiceCommunication" +  organisationCode + serviceVersionNumber;
    public String actionServiceName= "productUnderTestServiceAction" +  organisationCode + serviceVersionNumber;
    public String onlineServiceName= "productUnderTestServiceOnline" +  organisationCode + serviceVersionNumber;

    public String serviceOperation(String operationName, String serviceName) throws ParseException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("reqarg1", operationName);
        requestBody.put("reqarg2", serviceName);
        System.out.println("Operation: " + operationName + ", ServiceName: " + serviceName);
        HttpRequest executeOperation = buildServiceOperation(serviceOperationUrl,requestBody.toString(),createServiceOperationHeader());
        HttpResponse response = httpClient.post(executeOperation);
        JSONParser parser = new JSONParser();
        JSONObject jsonResult = (JSONObject) parser.parse(response.getResponseBody());
        String commandOutput = jsonResult.get("command_output").toString();
        System.out.println(commandOutput);

        String returnHolder = "initState";
        if((commandOutput.contains("PENDING") && ( operationName.equals("start") || operationName.equals("stop"))) || operationName.equals("query")) {
            returnHolder= StringUtils.substringBetween(commandOutput, "STATE", "(");
        }

        if(commandOutput.contains("1056")) {
            returnHolder= "Default Running 1056";
        }

        if(commandOutput.contains("1062")) {
            returnHolder= "Default Stopped 1062";
        }

        return returnHolder;
        /**
         *https://learn.microsoft.com/en-us/windows/win32/services/service-status-transitions
         *START_PENDING
         *RUNNING
         *PAUSE_PENDING
         *PAUSED
         *STOPPED
         *STOP_PENDING
         **
         * C:\WINDOWS\system32>sc.exe stop "Machine Learning Windows Service"
         *
         * SERVICE_NAME: Machine Learning Windows Service
         *         TYPE               : 10  WIN32_OWN_PROCESS
         *         STATE              : 3  STOP_PENDING
         *                                 (STOPPABLE, NOT_PAUSABLE, ATSNTPTS_SHUTDOWN)
         *         WIN32_EXIT_CODE    : 0  (0x0)
         *         SERVICE_EXIT_CODE  : 0  (0x0)
         *         CHECKPOINT         : 0x0
         *         WAIT_HINT          : 0x0
         *
         * C:\WINDOWS\system32>sc.exe stop "Machine Learning Windows Service"
         * [SC] ControlService FAILED 1061:
         *
         * The service cannot aTSNTpt control messages at this time.
         *
         *
         * C:\WINDOWS\system32>sc.exe stop "Machine Learning Windows Service"
         * [SC] ControlService FAILED 1062:
         *
         * The service has not been started.
         *
         *
         * C:\WINDOWS\system32>sc.exe start "Machine Learning Windows Service"
         *
         * SERVICE_NAME: Machine Learning Windows Service
         *         TYPE               : 10  WIN32_OWN_PROCESS
         *         STATE              : 2  START_PENDING
         *                                 (STOPPABLE, NOT_PAUSABLE, ATSNTPTS_SHUTDOWN)
         *         WIN32_EXIT_CODE    : 0  (0x0)
         *         SERVICE_EXIT_CODE  : 0  (0x0)
         *         CHECKPOINT         : 0x0
         *         WAIT_HINT          : 0x0
         *         PID                : 10704
         *         FLAGS              :
         *
         * C:\WINDOWS\system32>sc.exe start "Machine Learning Windows Service"
         * [SC] StartService FAILED 1056:
         *
         * An TSNTance of the service is already running.
         *
         *
         *
         *
         *
         **/


    }

    public void servicePooling(String operationName, String serviceName) throws InterruptedException, ParseException {
        String pendingState = "";
        String finalState = "";
        String errorMsg = "";
        switch (operationName){
            case "start":
                pendingState = "START_PENDING";
                finalState = "RUNNING";
                errorMsg = "1056";
                break;
            case "stop":
                pendingState = "STOP_PENDING";
                finalState = "STOPPED";
                errorMsg = "1062";
                break;
            default:
                System.out.println("Error on state init");
        }

        String returnedStatus = serviceOperation(operationName, serviceName);
        if( returnedStatus.contains(pendingState)){
            int counter = 0;
            while(counter <= 5){
                if (!serviceOperation("query", serviceName).contains(finalState)){
                    Thread.sleep(5000);
                } else {
                    break;
                }
                counter++;
            }
            if(counter >= 5) {
                System.out.println(serviceOperation("query", serviceName));
                Assert.assertTrue(false);
            }
        } else if(returnedStatus.contains(errorMsg)){
            System.out.println("Service default state is " + finalState);

        }
        else {
            System.out.println(returnedStatus);
            Assert.assertTrue(false);
        }
    }
}
