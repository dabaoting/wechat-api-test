package com.wechat.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wechat.action.ApiActionModel;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ApiObjectModel {

    private String name;

    private HashMap<String,ApiActionModel> actions;

    private HashMap<String ,String> obVariables = new HashMap<>();

    public static ApiObjectModel load(String path) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path),ApiObjectModel.class);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, ApiActionModel> getActions() {
        return actions;
    }

    public void setActions(HashMap<String, ApiActionModel> actions) {
        this.actions = actions;
    }

    public HashMap<String, String> getObVariables() {
        return obVariables;
    }

    public void setObVariables(HashMap<String, String> obVariables) {
        this.obVariables = obVariables;
    }
}
