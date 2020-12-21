package com.api.task;

import com.alibaba.fastjson.JSON;
import com.api.object.contact.DepartmentObject;
import com.api.object.contact.UserObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvHelperTask {

    private static String DEP_ID = "32593";

    private static final Logger logger = LoggerFactory.getLogger(EnvHelperTask.class);

    /**
     * 清除部门
     * @param accessToken
     */
    public static void clearDepTask(String accessToken){

        ArrayList<Integer> departmentIds = DepartmentObject.listDepartMent(DEP_ID,accessToken).path("department.id");
        for( int departmentId:departmentIds){
            if(Integer.valueOf(DEP_ID)==departmentId) continue;
            DepartmentObject.deletDepartMent(departmentId+"",accessToken);
        }
    }

    /**
     * 清除用户
     * @param accessToken
     */
    public static void clearUsers(String accessToken){

        ArrayList<Integer> userIds = UserObject.listUsers(DEP_ID,accessToken).path("userlist.userid");
        Map delUsersBody= new HashMap<String, List<Integer>>();
        delUsersBody.put("useridlist",userIds);
        String userIdJ = JSON.toJSON(delUsersBody).toString();
        UserObject.deletUsers(userIdJ,accessToken);

    }



}
