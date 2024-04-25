package com..test.context;
import com..test.models.LoginInformation;
import lombok.Data;

@Data
public class LoginContext {
    private LoginInformation loginInformation;
    private String scenario;

    public void setLoginInformation(LoginInformation loginInformation) {
        this.loginInformation = loginInformation;
    }
}
