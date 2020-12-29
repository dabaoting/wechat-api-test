package com.weichat.simple.api.object.contact;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserObject {

    public static Response createUser(User user, String accessToken) {

        Response response = given().log().all()
                .contentType("application/json")
                .body(user)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token="+accessToken)
                .then()
                .log().body()
                .extract().response();
        return response;
    }

    public static Response listUsers(String depId, String accessToken) {

        Response response = given().log().all()
                .param("access_token", accessToken)
                .param("department_id", depId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/simplelist")
                .then()
                .log().body()
                .extract()
                .response();
        return response;
    }


    public static Response deletUsers(String users, String accessToken) {
        Response response = given().log().all()
                .body(users)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=" + accessToken);
        return response;
    }

    /**
     * 邀请用户
     * @param accessToken
     * @return
     */
    public static Response inviteUser(String accessToken){

        Response response = given().log().all()
                .body("{\n" +
                        "   \"user\" : [\"dabaoting\"],\n" +
                        "}")
                .post("https://qyapi.weixin.qq.com/cgi-bin/batch/invite?access_token=" + accessToken)
                .then()
                .log().body()
                .extract()
                .response();
        return response;


    }


}
