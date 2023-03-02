package com.atguigu.gulimall.thirdparty.component;

import com.atguigu.common.utils.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("spring.cloud.alicloud.sms")
@Data
@Component
public class SmsCompont {
    private String host;
    private String path;
    private String template_id;
    private String appcode;


    public void sendSmsCode(String phone,String Code){
//    String host = "https://dfsns.market.alicloudapi.com";
//    String path = "/data/send_sms";
    String method = "POST";
//    String appcode = "de23ee66bcca4f99820b9ace4bfe3ed4";
    Map<String, String> headers = new HashMap<String, String>();
    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
    //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    Map<String, String> querys = new HashMap<String, String>();
    Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+Code);
        bodys.put("phone_number", phone);
        bodys.put("template_id", template_id);


        try {
        /**
         * 重要提示如下:
         * HttpUtils请从
         * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
         * 下载
         *
         * 相应的依赖请参照
         * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
         */
        HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
        System.out.println(response.toString());
        //获取response的body
        //System.out.println(EntityUtils.toString(response.getEntity()));
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
