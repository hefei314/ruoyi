package com.ruoyi.web.core.tencent;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.VerifyBasicBizLicenseRequest;
import com.tencentcloudapi.ocr.v20181119.models.VerifyBasicBizLicenseResponse;
import com.tencentcloudapi.ocr.v20181119.models.VerifyBizLicenseRequest;
import com.tencentcloudapi.ocr.v20181119.models.VerifyBizLicenseResponse;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/12/10
 *     desc  :
 * </pre>
 */
public class OcrUtils {

    private final static String SECRET_ID = "AKIDZ4QaU7kHUWRBi02xf8MUUL4iK8IH6xeF";
    private final static String SECRET_KEY = "fJrBIDuX7h9ZyGZmwynOZbHGxfwBNWsG";

    public static VerifyBasicBizLicenseResponse basicVerifyBizLicense(String imageUrl) throws Exception {
        Credential cred = new Credential(SECRET_ID, SECRET_KEY);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("ocr.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);
        VerifyBasicBizLicenseRequest req = new VerifyBasicBizLicenseRequest();
        req.setImageUrl(imageUrl);
        req.setRegCapital(1L);
        VerifyBasicBizLicenseResponse resp = client.VerifyBasicBizLicense(req);

        return resp;
    }

    public static String verifyBizLicense(String imageUrl) throws Exception {
        Credential cred = new Credential(SECRET_ID, SECRET_KEY);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("ocr.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);
        VerifyBizLicenseRequest req = new VerifyBizLicenseRequest();
        req.setImageUrl(imageUrl);
        VerifyBizLicenseResponse resp = client.VerifyBizLicense(req);

        return VerifyBizLicenseResponse.toJsonString(resp);
    }

}
