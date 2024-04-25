package com..test.context;

import com..test.models.ProcessTransaction;
import lombok.Data;

@Data
public class ProcessTransactionContext {
    private ProcessTransaction processTransaction;
    private String scenario;

}