package com..test.models;

import lombok.Data;

@Data
public class ProcessTransaction {
    private String channel;
    private String transactionDateTime;
    private String customerNo;
    private String staffID;
    private String accountNo;
    private String senderAccount;
    private String transactionType;
}
