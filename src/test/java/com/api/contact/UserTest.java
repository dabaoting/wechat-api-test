package com.api.contact;

import com.api.common.WeChatCommon;
import com.api.object.contact.User;
import com.api.object.contact.UserObject;
import com.api.task.EnvHelperTask;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("企业微信接口测试")
@Feature("成员管理测试")
public class UserTest extends WeChatCommon {

    private static String MEMBERS_YAML = "src/test/resources/users.yaml";

    @BeforeEach
    @AfterEach
    void clearUsers(){
        //清除成员
        EnvHelperTask.clearUsers(accessToken);
    }

    @Description("创建成员测试")
    @Story("Story创建成员测试")
    @DisplayName("创建成员")
    @ParameterizedTest
    @MethodSource("usersData")
    void userAddTest(List<User> users){

        for (User user:users) {
            Response response = UserObject.createUser(user, accessToken);
            assertEquals("0",response.path("errcode").toString());
        }
    }

    @Description("邀请成员测试")
    @Story("Story邀请成员测试")
    @DisplayName("邀请成员")
    @Test
    void userInviteTest(){
        Response response = UserObject.inviteUser(accessToken);
        assertEquals("0",response.path("errcode").toString());
    }


    /**
     * 组装成员测试数据
     * @return
     * @throws Exception
     */
    static Stream<List<User>> usersData() throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference typeReference = new TypeReference<List<User>>() {};
        List<User> users = (List<User>)mapper.readValue(new File(MEMBERS_YAML), typeReference);
        return Stream.of(users);
    }
}
