package com.wechat.model.action;
import com.utils.PlaceholderUtils;
import com.wechat.model.global.GlobalVariables;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * Api接口动作对象
 */
public class ApiActionModel {
    private ArrayList<String> formalParam; //形参
    private HashMap<String,String> headers;
    private String url;
    private String method = "get";
    private String contentType = "application/json";
    private HashMap<String,String> query;
    private String body;
    private String post;

    private String get;
    private Response response;
    private HashMap<String, String> actionVariables = new HashMap<>(); //实参


    /**
     * 处理并发送请求，返回结果
     * @param actualParameter:传入的实参
     * @return
     */
    public Response run(ArrayList<String> actualParameter){

        String runUrl = this.url;
        String runBody = this.body;
        HashMap<String, String> finalQuery = new HashMap<>();//最终的请求参数

        //1. 确认请求方式(get/post/put/...)和url
        if(post != null){
            runUrl = post;
            method = "post";
        }else if(get != null){
            runUrl = get;
        }

        //2. 全局变量替换（请求参数、URL变量、body变量）
            //2.1 query全局变量替换
        if(query != null){
            finalQuery = PlaceholderUtils.resolveMap(query, GlobalVariables.getGlobalVariables());
        }

            //2.2. body全局变量替换
        if(body != null){
            runBody = PlaceholderUtils.resolveString(runBody, GlobalVariables.getGlobalVariables());
        }

            //2.3 url全局变量替换
        runUrl = PlaceholderUtils.resolveString(runUrl,GlobalVariables.getGlobalVariables());


        //3. 实参替换形参
            //3.1 根据形参和实参构建变量字典（key=形参，value=实参）
        if(formalParam != null && actualParameter != null && formalParam.size() > 0 && actualParameter.size() >0 ){
            for (int i = 0; i < formalParam.size(); i++) {
                actionVariables.put(formalParam.get(i),actualParameter.get(i));
            }

        }
            //3.2 形参赋值
        if(query != null){
            finalQuery.putAll(PlaceholderUtils.resolveMap(query,actionVariables));
        }
        runBody = PlaceholderUtils.resolveString(runBody,actionVariables);
        runUrl = PlaceholderUtils.resolveString(runUrl,actionVariables);


        //4. 发送请求，返回结果
        RequestSpecification requestSpecification = given().log().all();

        if(contentType != null){
            requestSpecification.contentType(contentType);
        }

        if(headers != null){
            requestSpecification.headers(headers);
        }

        if(finalQuery != null && finalQuery.size()>0){
            requestSpecification.formParams(finalQuery);
        }else if (runBody !=null){
            requestSpecification.body(runBody);
        }

        Response response = requestSpecification.request(method, runUrl).then().log().all().extract().response();
        this.response = response;
        return response;
    }


    public ArrayList<String> getFormalParam() {
        return formalParam;
    }

    public void setFormalParam(ArrayList<String> formalParam) {
        this.formalParam = formalParam;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public HashMap<String, String> getQuery() {
        return query;
    }

    public void setQuery(HashMap<String, String> query) {
        this.query = query;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public HashMap<String, String> getActionVariables() {
        return actionVariables;
    }

    public void setActionVariables(HashMap<String, String> actionVariables) {
        this.actionVariables = actionVariables;
    }
}
