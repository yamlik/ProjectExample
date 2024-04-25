package com..test.soaphandlers;

public class ProcessTransactionHandler {

    private String channel;
    private String transactionDateTime;
    private String customerNo;
    private String staffID;
    private String accountNo;
    private String senderAccount;
    private String transactionType;


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Method to create the xml structured request for ProcessTransaction - this will use the set values from the getters/setters above
     * @return A string containing the XML structure
     */
    public String createProcessTransactionRequest() {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:pred=\"http://.co.uk/Predator/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <pred:ProcessTransaction>\n" +
                "         <!--Optional:-->\n" +
                "         <pred:transactionData><![CDATA[\n" +
                "          <PredatorMessage>\n" +
                "            <Channel>\n" +
                "                    <Id>"+getChannel()+"</Id>\n" +
                "            </Channel>\n" +
                "            <Transaction>\n" +
                "              <Transaction_Datetime>"+getTransactionDateTime()+"</Transaction_Datetime>\n" +
                "              <Customer_No>"+getCustomerNo()+"</Customer_No>\n" +
                "              <Staff_Id>"+getStaffID()+"</Staff_Id>\n" +
                "              <Account_No>"+getAccountNo()+"</Account_No>\n" +
                "              <Sender_Account_No>"+getSenderAccount()+"</Sender_Account_No>\n" +
                "              <Transaction_Type>"+getTransactionType()+"</Transaction_Type>\n" +
                "            </Transaction>\n" +
                "          </PredatorMessage>\n" +
                "         ]]></pred:transactionData>\n" +
                "      </pred:ProcessTransaction>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

}
