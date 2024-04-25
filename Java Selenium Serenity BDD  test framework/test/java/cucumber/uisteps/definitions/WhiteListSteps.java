package com..cucumber.uisteps.definitions;
import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.definitions.WhiteListPage;
import cucumber.api.java.en.And;
import net.thucydides.core.annotations.Shared;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class WhiteListSteps extends BaseUISteps {

        @Shared
        WhiteListPage whiteListPage;

        @And("^I verify the White List record$")
        public void iVerifyTheWhiteListRecord () throws IOException, ParseException {
        whiteListPage.verifyWhitelistRecord();
    }

    @And("^I Delete the Whitelist Record$")
    public void iDeleteTheWhitelistRecord() throws IOException, ParseException {
            whiteListPage.deleteWhiteListRecord();
    }
}

