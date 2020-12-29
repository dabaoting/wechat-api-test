package com.wechat;

import io.restassured.response.Response;

public class BaseResult {

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    private Response response;
}
