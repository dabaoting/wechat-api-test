package com.weichat.simple.api.assured;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.ArrayMatching.arrayContainingInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIn.isIn;

public class UserTest {

    private static String accessToken;
    
//    @BeforeAll
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

    @Test
    void testDemo(){
        ArrayList<String> actual = new ArrayList();
        actual.add("1");
        actual.add("2");

        ArrayList<String> expect = new ArrayList();
        expect.add("1");
        expect.add("2");

        assertThat("","1",isIn(actual));
    }
}
