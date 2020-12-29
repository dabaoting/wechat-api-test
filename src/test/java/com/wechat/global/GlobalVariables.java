package com.wechat.global;

import java.util.HashMap;

public class GlobalVariables {

    static  private HashMap<String, String > globalVariables =new HashMap<>();


    public static HashMap<String, String> getGlobalVariables() {
        return globalVariables;
    }

    public static void setGlobalVariables(HashMap<String, String> globalVariables) {
        GlobalVariables.globalVariables = globalVariables;
    }
}
