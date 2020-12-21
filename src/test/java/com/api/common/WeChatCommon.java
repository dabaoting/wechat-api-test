package com.api.common;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class WeChatCommon {

    public static String accessToken;

    @BeforeAll
    static void init(){
        accessToken = given()
                .when()
                .param("corpid","ww1aa64e79931be685")
                .param("corpsecret","82N9nLXm5CH7ZtY6LgkWHaMqEYdWfbIIXbxcT_Z-MeA")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .extract().response().path("access_token");
    }

}
