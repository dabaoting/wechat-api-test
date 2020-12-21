package com.api.assured;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UserTest {

    private static String accessToken;
    
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

    @Test
    void sendMsg(){
        given()
                .contentType("application/json;charset=utf-8")
                .queryParam("access_token",accessToken)
                .body("{\n" +
                        "   \"touser\" : \"dabaoting\",\n" +
                        "   \"msgtype\" : \"text\",\n" +
                        "   \"agentid\" : 1,\n" +
                        "   \"text\" : {\n" +
                        "       \"content\" : \"我就是想测试用rest-assured测试一下。\\n详情可查看，聪明避开排队。\"\n" +
                        "   },\n" +
                        "}")
                .post("https://qyapi.weixin.qq.com/cgi-bin/message/send")
                .then()
                .log().all();

    }
}
