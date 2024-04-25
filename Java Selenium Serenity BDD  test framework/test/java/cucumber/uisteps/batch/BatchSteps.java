package com..cucumber.uisteps.batch;
import com..cucumber.uisteps.BaseUISteps;
import com..test.clients.ftpClient;
import com..test.utilities.FileHelper;
import cucumber.api.java.en.Given;
import org.junit.Assert;

public class BatchSteps extends BaseUISteps {

    private  ftpClient ftp = new ftpClient();

    @Given("^I transfer file \"([^\"]*)\" to batch service input directory$")
    public void iTransferFileToBatchServiceInputDirectory(String filename) throws Throwable{
        int attempts = 0;

        if(!filename.contains("xls")) {
            if(!filename.contains("xlsx")) {
                System.out.println("This Is Not Excel File.");
                FileHelper.findAndReplaceFileContent(dataDir + "Batch/" + filename);
            }
        }

        ftp.uploadFile(filename,"INPUT\\" + filename);

        while(ftp.findFile("INPUT\\",filename) && attempts < 20)
        {
            waitABit(1000);
            attempts++;
        }
        Assert.assertFalse(attempts>=20);
    }
}
