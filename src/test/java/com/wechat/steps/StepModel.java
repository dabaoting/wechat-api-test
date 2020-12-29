package com.wechat.steps;

import com.utils.PlaceholderUtils;
import com.wechat.global.ApiLoader;
import com.wechat.global.GlobalVariables;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StepModel {

    public static final Logger logger = LoggerFactory.getLogger(StepModel.class);

    //=====yaml文件=====
    private String api;
    private String action;
    private ArrayList<String> actualParameter;
    private ArrayList<AssertModel> asserts;
    private HashMap<String, String> save;
    private HashMap<String, String> saveGlobal;
    //======yaml文件====

    private ArrayList<String> finalActualParameter = new ArrayList<>();//最终的实参
    private HashMap<String, String> stepVariables = new HashMap<>();//存储action中返回的结果,用于其他参数值
    private ArrayList<Executable> assertList = new ArrayList<>();//存储每一步的断言结果
    private StepResult stepResult = new StepResult();

    public StepResult run(HashMap<String, String> testCaseVariables){

        if(actualParameter != null){
            finalActualParameter = PlaceholderUtils.resolveList(actualParameter,testCaseVariables);
        }

        //获取对应的api和action执行
        Response response = ApiLoader.getAction(api, action).run(finalActualParameter);

        //存储save中定义的内容
        if(save != null && save.size() > 0){
            save.forEach((variablesName,path) -> {
                String value = response.path(path).toString();
                stepVariables.put(variablesName,value);
                logger.info("step变量更新： " + variablesName + "=" + value);
            });
        }

        //存储全局变量
        if(saveGlobal != null){
            saveGlobal.forEach((variablesName,path) -> {
                String value = response.path(path).toString();
                GlobalVariables.getGlobalVariables().put(variablesName, value);
                logger.info("全局变量更新： " + variablesName + "=" + value);
            });
        }

        //软断言需要的中间断言，不会终结测试将断言结果存入断言结果列表中，在用例最后进行统一结果输出
        if(asserts != null){
            asserts.forEach(assertModel ->{
                assertList.add(() -> {

                    String reason = assertModel.getReason();
                    String actualPath = assertModel.getActual().toString();
                    String matcher = assertModel.getMatcher();
                    String expect = assertModel.getExpect();

                    //利用反射获取需要断言的方式
                    Class<Matchers> matchersClazz = (Class<Matchers>) Class.forName("org.hamcrest.Matchers");
                    Method matherMethod = matchersClazz.getDeclaredMethod(matcher, Object.class);
                    Matcher invoke = (Matcher)matherMethod.invoke(matchersClazz.newInstance(), expect);

                    //断言
                    assertThat(reason,response.path(actualPath),invoke);

                });
            });
        }

        //组装response和断言结果返回
        stepResult.setAssertList(assertList);
        stepResult.setStepVariables(stepVariables);
        stepResult.setResponse(response);

        return stepResult;

    }


    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<String> getActualParameter() {
        return actualParameter;
    }

    public void setActualParameter(ArrayList<String> actualParameter) {
        this.actualParameter = actualParameter;
    }

    public List<AssertModel> getAsserts() {
        return asserts;
    }

    public void setAsserts(ArrayList<AssertModel> asserts) {
        this.asserts = asserts;
    }

    public HashMap<String, String> getSave() {
        return save;
    }

    public void setSave(HashMap<String, String> save) {
        this.save = save;
    }

    public HashMap<String, String> getSaveGlobal() {
        return saveGlobal;
    }

    public void setSaveGlobal(HashMap<String, String> saveGlobal) {
        this.saveGlobal = saveGlobal;
    }
}
