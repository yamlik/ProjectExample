package com..test.enums;

public class AllEnums {


    public enum Administrators {
        ADMINISTRATOR,
        NONADMIN
    }

    public static boolean containsAdministrators(String user) {
        for (Administrators a : Administrators.values()) {
            if (a.name().equals(user)) {
                return true;
            }
        }
        return false;
    }
}
