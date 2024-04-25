package com..test.utilities;

public class FilterPopup {
    private String field;
    private String operator;
    private String  value;
    public FilterPopup(String field, String operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getField()
    {
        return field;
    }
    public String getOperator()
    {
        return operator;
    }
    public String getValue()
    {
        return value;
    }
}
