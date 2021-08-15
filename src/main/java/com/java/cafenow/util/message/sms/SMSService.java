package com.java.cafenow.util.message.sms;

import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class SMSService {

    private final Message message;

    public void sendSMS(String businessNumber, String receiverPhoneNumber) {
        try {
            HashMap<String, String> params = setParams(businessNumber, receiverPhoneNumber);
            message.send(params);
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
        }
    }

    public HashMap<String, String> setParams(String businessNumber, String receiverPhoneNumber) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", "01040789726");
        params.put("from", receiverPhoneNumber);
        params.put("type", "SMS");
        params.put("text", "사업자 번호" + businessNumber + "님 승인이 완료 되었습니다.");
        params.put("app_version", "test app 1.2");
        return params;
    }

}
