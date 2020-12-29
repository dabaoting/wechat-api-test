package com.weichat.simple.api.contact;

import com.weichat.simple.api.common.WeChatCommon;
import com.weichat.simple.api.object.contact.DepartmentObject;
import com.weichat.simple.api.util.FakerUtils;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.parallel.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

public class DepartmentThreadTest extends WeChatCommon {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentThreadTest.class);

    @DisplayName("创建部门repeat")
    @RepeatedTest(10)
    void createDepartment() {
        String backendStr = Thread.currentThread().getId()+ FakerUtils.getTimeStamp()+"";

        String name = "name"+ backendStr;
        String enName = "en_name"+backendStr;

        Response response= DepartmentObject.creatDepartMent(name,enName,accessToken);
        assertEquals("0",response.path("errcode").toString());

    }

    @DisplayName("创建部门concurrent")
    @Description("创建部门")
    @RepeatedTest(10)
    @Execution(CONCURRENT)
    void createDepartmentCon() {
        String backendStr = Thread.currentThread().getId()+ FakerUtils.getTimeStamp()+"";

        String name = "name"+ backendStr;
        String enName = "en_name"+backendStr;

        Response response= DepartmentObject.creatDepartMent(name,enName,accessToken);
        assertEquals("0",response.path("errcode").toString());

    }

}
