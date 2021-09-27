package ${pkg};

<#list  imports as impt>import ${impt};
</#list>

import so.dian.qa.heimdallr.*;
import so.dian.qa.heimdallrutil.*;
import so.dian.qa.heimdallr.util.rest.RestUtil;
import so.dian.qa.heimdallr.annotation.TestData;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;
/**
* the ${className}
* @author yourself
*/
@SpringBootTest(classes = ${extProperty["SpringBootTest"]})
public class ${className} extends RestApiGuard {

    @Autowired
    RestUtil restUtil;

    @Autowired
    RestTemplate restTemplate;

    @Value("<#noparse>${test.application}</#noparse>")
    private String testApplication;


<#list  methods as method>
    /**
    * 测试 ${method.methodName}
    */
    @Test(dataProvider = "json")
    @TestData("/${className}/${method.methodName}.json")
    public ${method.methodType} ${method.methodName}(TestCase testCase) throws JSONException {

    System.out.println(testCase.getDesc());
    /*接口调用*/
    Object response = restUtil.doRequest(testApplication,testCase);
    /*返回值校验*/
    JSONAssert.assertEquals(JSON.toJSONString(testCase.getExpect()),JSON.parseObject(new Gson().toJson(response)).toString(),false);
    }


</#list>

}