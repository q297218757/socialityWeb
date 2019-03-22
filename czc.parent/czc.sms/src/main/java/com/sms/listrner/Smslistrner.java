package com.sms.listrner;

import com.aliyuncs.exceptions.ClientException;
import com.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues ="sms")
public class Smslistrner {
    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;
    @RabbitHandler
    public void executeSms(Map<String, Object> map){
        String chekcode = (String) map.get("chekcode");
        String mobile = (String)map.get("mobile");
        System.out.println("手机号:"+map.get("mobile"));
        System.out.println("验证码:"+map.get("chekcode"));
        try {
            smsUtil.sendSms(mobile, template_code, sign_name, "{\"code\":\"" + chekcode + "\"}");
        }catch (ClientException e){
            e.printStackTrace();
        }
    }
}
