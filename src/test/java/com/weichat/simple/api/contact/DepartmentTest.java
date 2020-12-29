package com.weichat.simple.api.contact;

import com.weichat.simple.api.common.WeChatCommon;
import com.weichat.simple.api.object.contact.DepartmentObject;
import com.weichat.simple.api.task.EnvHelperTask;
import com.weichat.simple.api.util.FakerUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("企业微信接口测试")
@Feature(("部门测试"))
public class DepartmentTest extends WeChatCommon {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentTest.class);


    @BeforeEach
    @AfterEach
    void clearDep(){
        EnvHelperTask.clearDepTask(accessToken);
    }

    @Description("创建部门测试-入参数据驱动")
    @Story("Story创建部门测试")
    @DisplayName("DisplayName创建部门")
    @ParameterizedTest
    @CsvFileSource(resources = "src/test/resources/department_create.csv", numLinesToSkip = 1)
    void createDepartment01(String creatName, String creatEnName, String returnCode) {
        Response creatResponse = DepartmentObject.creatDepartMent(creatName, creatEnName, accessToken);
        assertEquals(returnCode, creatResponse.path("errcode").toString());
    }




    @Description("Description这个测试方法会测试修改部门的功能")
    @Story("Story修改部门测试")
    @DisplayName("DisplayName修改部门")
    @Test
    void updateDepartment() {

        //创建部门
        String departmentId = DepartmentObject.creatDepartMent(accessToken);

        //修改部门
        String updateName = "name" + FakerUtils.getTimeStamp();
        String updateEnName = "en_name" + FakerUtils.getTimeStamp();
        Response updateResponse = DepartmentObject.updateDepartMent(updateName, updateEnName,departmentId, accessToken);
        assertEquals("0", updateResponse.path("errcode").toString());
    }

    @DisplayName("DisplayName查询部门")
    @Description("Description这个测试方法会测试查询部门的功能")
    @Story("Story查询部门测试")
    @Test
    void listDepartment() {
        String creatName = "name" + FakerUtils.getTimeStamp();
        String creatEnName = "en_name" + FakerUtils.getTimeStamp();

        Response creatResponse = DepartmentObject.creatDepartMent(creatName, creatEnName, accessToken);
        String departmentId = creatResponse.path("id") != null ? creatResponse.path("id").toString() : null;
        Response listResponse = DepartmentObject.listDepartMent(departmentId, accessToken);

        assertAll("返回值校验！",
                () -> assertEquals("0", listResponse.path("errcode").toString()),
                () -> assertEquals(departmentId, listResponse.path("department.id[0]").toString()),
                () -> assertEquals(creatName, listResponse.path("department.name[0]").toString()),
                () -> assertEquals(creatEnName, listResponse.path("department.name_en[0]").toString())

        );
    }

    @DisplayName("DisplayName删除部门")
    @Description("Description这个测试方法会测试删除部门的功能")
    @Story("Story删除部门测试")
    @Test
    void deleteDepartment() {
        String departmentId = DepartmentObject.creatDepartMent(accessToken);

        Response deletResponse = DepartmentObject.deletDepartMent(departmentId, accessToken);

        assertEquals("0", deletResponse.path("errcode").toString());
    }

}
