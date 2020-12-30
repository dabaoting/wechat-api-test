package com.wechat.model.testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wechat.model.steps.StepModel;
import com.wechat.model.steps.StepResult;
import com.weichat.simple.api.util.FakerUtils;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;

public class ApiTestCaseModel {

    public static final Logger logger = LoggerFactory.getLogger(ApiTestCaseModel.class);

    //===yaml文件==
    private String name;
    private String description;
    private ArrayList<StepModel> steps;
    //===yaml文件==

    HashMap<String, String> testCaseVariables = new HashMap<>();//用例变量
    ArrayList<Executable> assertList = new ArrayList<>();//收集step返回的断言

    /**
     * 加载yaml文件，映射实体
     * @param path
     * @return
     * @throws IOException
     */
    public static ApiTestCaseModel load(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path),ApiTestCaseModel.class);
    }

    public void run(){
        // 1.加载用例层关键字变量
        this.testCaseVariables.put("getTimeStamp", FakerUtils.getTimeStamp());
        logger.info("用例变量更新： "+ testCaseVariables);

        // 2.执行step，收集返回结果
        steps.forEach(step -> {

            StepResult stepResult = step.run(testCaseVariables);
            //2.1 处理save变量
            if(stepResult.getStepVariables().size()>0){
                testCaseVariables.putAll(stepResult.getStepVariables());
                logger.info("testcae变量更新： "+ testCaseVariables);
            }

            //2.2 收集assertList
            if(stepResult.getAssertList().size()>0){

                assertList.addAll(stepResult.getAssertList());
            }

        });
        // 3.统一断言
        assertAll("",assertList.stream());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<StepModel> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepModel> steps) {
        this.steps = steps;
    }

    public HashMap<String, String> getTestCaseVariables() {
        return testCaseVariables;
    }

    public void setTestCaseVariables(HashMap<String, String> testCaseVariables) {
        this.testCaseVariables = testCaseVariables;
    }

    public ArrayList<Executable> getAssertList() {
        return assertList;
    }

    public void setAssertList(ArrayList<Executable> assertList) {
        this.assertList = assertList;
    }
}
