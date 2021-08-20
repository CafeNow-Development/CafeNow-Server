package com.java.cafenow.util.message.sms;

import com.java.cafenow.advice.exception.CustomException;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class SMSService {

    private final String API_KEY = "";
    private final String API_SECRET = "";

    public void sendSMS(String businessNumber, String receiverPhoneNumber) {
        try {
            Message message = new Message(API_KEY, API_SECRET);
            HashMap<String, String> params = setParams(businessNumber, receiverPhoneNumber);
            message.send(params);
        } catch (CoolsmsException e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public HashMap<String, String> setParams(String businessNumber, String receiverPhoneNumber) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", "01040789726");
        params.put("from", receiverPhoneNumber);
        params.put("type", "SMS");
        params.put("text", "CafeNow 사업자 번호 \n"
                 + "[" + businessNumber + "]" + " 님의 매장 승인이 완료 되었습니다.");
        params.put("app_version", "test app 1.2");
        return params;
    }

}
