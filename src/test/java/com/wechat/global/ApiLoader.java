package com.wechat.global;

import com.wechat.action.ApiActionModel;
import com.wechat.api.ApiObjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 接口对象加载器
 */
public class ApiLoader {

    public static final Logger logger = LoggerFactory.getLogger(ApiLoader.class);

    private static List<ApiObjectModel> apis = new ArrayList<>();

    public static void load(String dir){
        Arrays.stream((new File(dir)).list()).forEach(path ->{
            try {
                apis.add(ApiObjectModel.load(dir + "/" + path));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static ApiActionModel getAction(String apiName,String actionName){
        final ApiActionModel[] apiActionModel = new ApiActionModel[1];
        apis.stream().filter(api -> api.getName().equals(apiName)).forEach(api -> {
            apiActionModel[0] = api.getActions().get(actionName);
        });
        if(apiActionModel[0]!=null){
            return apiActionModel[0];
        }else{
            logger.info("没有找到接口对象： "+apiName+"中的action: "+actionName);
        }
        return null;
    }



}
