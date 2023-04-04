package com.ruoyi.web.core.tencent;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/12/10
 *     desc  :
 * </pre>
 */
public class SmsUtils {

    private final static String SECRET_ID = "AKIDZ4QaU7kHUWRBi02xf8MUUL4iK8IH6xeF";
    private final static String SECRET_KEY = "fJrBIDuX7h9ZyGZmwynOZbHGxfwBNWsG";

    public static SendStatus[] sendSms(String phone, String verifyCode) throws Exception {
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(SECRET_ID, SECRET_KEY);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
        // 实例化一个请求对象,每个接口都会对应一个request对象
        SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet1 = {phone};
        req.setPhoneNumberSet(phoneNumberSet1);

        req.setSmsSdkAppId("1400778080");
        req.setSignName("南京游商科技有限公司");
        req.setTemplateId("1635141");

        String[] templateParamSet1 = {verifyCode};
        req.setTemplateParamSet(templateParamSet1);

        // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
        SendSmsResponse resp = client.SendSms(req);
        System.out.println(SendSmsResponse.toJsonString(resp));
        return resp.getSendStatusSet();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            return Pattern.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", phoneNumber);
        }
        return false;
    }

    public static String generateVerifyCode() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            result.append(new Random().nextInt(10));
        }
        return result.toString();
    }
}
