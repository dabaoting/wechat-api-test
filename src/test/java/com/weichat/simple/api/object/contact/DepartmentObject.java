package com.weichat.simple.api.object.contact;

import com.weichat.simple.api.util.FakerUtils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DepartmentObject {

    /**
     * 获取指定部门下的所有部门
     * @param departmentId
     * @param accessToken
     * @return
     */
    public static Response listDepartMent(String departmentId, String accessToken){
        Response listResponse =given().log().all()
                .when()
                .param("id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token="+accessToken)
                .then()
                .log().body()
                .extract()
                .response();
        return listResponse;
    }

    /**
     * 删除指定部门
     * @param departmentId
     * @param accessToken
     * @return
     */
    public static Response deletDepartMent(String departmentId,String accessToken){
        Response deletResponse = given().log().all()
                .contentType("application/json")
                .param("access_token",accessToken)
                .param("id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .log().body()
                .extract().response()
                ;
        return deletResponse;
    }


    /**
     * 创建部门
     * @param creatName
     * @param creatEnName
     * @param accessToken
     * @return
     */
    public static Response creatDepartMent(String creatName,String creatEnName,String accessToken){
        String creatBody ="{\n" +
                "   \"name\": \""+creatName+"\",\n" +
                "   \"name_en\": \""+creatEnName+"\",\n" +
                "   \"parentid\": 32593}";
        Response creatResponse=given().log().all()
                .contentType("application/json")
                .body(creatBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+accessToken+"")
                .then()
                .log().body()
                .extract()
                .response()
                ;
        return creatResponse;
    }

    public static String creatDepartMent(String accessToken){
        String creatName= "name"+ FakerUtils.getTimeStamp();
        String creatEnName="en_name"+ FakerUtils.getTimeStamp();
        Response creatResponse = creatDepartMent(creatName,creatEnName,accessToken);
        String departmentId= creatResponse.path("id")!=null ? creatResponse.path("id").toString():null;
        return departmentId;
    }


    /**
     * 修改部门
     * @param updateName
     * @param updateEnName
     * @param departmentId
     * @param accessToken
     * @return
     */
    public static Response updateDepartMent(String updateName,String updateEnName,String departmentId,String accessToken){
        String updateBody ="{\n" +
                "   \"id\": "+departmentId+",\n" +
                "   \"name\": \""+updateName+"\",\n" +
                "   \"name_en\": \""+updateEnName+"\",\n" +
                "   \"order\": 1\n" +
                "}\n";
        Response updateResponse=given().log().all()
                .contentType("application/json")
                .body(updateBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token="+accessToken+"")
                .then()
                .log().body()
                .extract().response();
        return updateResponse;
    }
}
