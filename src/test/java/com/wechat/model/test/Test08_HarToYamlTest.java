package com.wechat.model.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wechat.model.action.ApiActionModel;
import com.wechat.model.api.ApiObjectModel;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HttpMethod;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Test08_HarToYamlTest {

    public static final Logger logger = LoggerFactory.getLogger(Test08_HarToYamlTest.class);

    private static String TOKEN_HAR_PATH = "src/test/resources/har/qyapi.weixin.qq.com.har";
    @Test
    void harToYmal() throws Exception{
        HarReader harReader = new HarReader();
        Har har = harReader.readFromFile(new File(TOKEN_HAR_PATH));
        List<HarEntry> entries = har.getLog().getEntries();

        ApiObjectModel apiObjectModel =  new ApiObjectModel();
        ApiActionModel apiActionModel = new ApiActionModel();
        HashMap<String, ApiActionModel> actions = new HashMap<>();
        HashMap<String,String> queryMap = new HashMap<>(); //url参数

        entries.forEach(entry -> {

            HarRequest harRequest = entry.getRequest();

            String method = harRequest.getMethod().toString();
            String url = harRequest.getUrl();
           //url参数
            harRequest.getQueryString().forEach(query->{
                queryMap.put(query.getName(),query.getValue());
            });apiActionModel.setQuery(queryMap);
            if(method.equals("get")){
                apiActionModel.setGet(url);
            }else{
                apiActionModel.setPost(url);
            }
            actions.put(getRequestName(url),apiActionModel);

        });

        apiObjectModel.setName("tokenhelper");
        apiObjectModel.setActions(actions);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("src/test/resources/har/token_helper.yaml"),apiObjectModel);

    }

    @Test
    public void runTest() throws Exception {
        ApiObjectModel apiObjectModel = ApiObjectModel.load("src/test/resources/har/tokenhelper.yaml");
        apiObjectModel.getActions().get("gettoken").run(null);
    }

    public String getRequestName(String url) {
        String[] suburl = url.split("\\u003F")[0].split("/");
        String name = "";
        if (suburl.length > 1) {
            name = suburl[suburl.length - 1];
        }else if(1==suburl.length){
            name = suburl[0];
        }
        return name;
    }


}
