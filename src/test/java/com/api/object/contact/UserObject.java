package com.api.object.contact;

import io.restassured.response.Response;
import javafx.animation.PathTransitionBuilder;

import java.util.List;

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


    public static void deletUsers(String users, String accessToken) {
        given().log().all()
                .body(users)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=" + accessToken);
    }
}
