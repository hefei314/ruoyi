package com.ruoyi.web.core.wxpay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/05/09
 *     desc  :
 * </pre>
 */
@Component
@ConfigurationProperties(prefix = "wxpay")
@Data
public class WxPayBean {
    /**
     * 商户号
     */
    private String appId = "";
    /**
     * 商户号
     */
    private String merchantId = "";
    /**
     * 商户API私钥路径
     */
    private String privateKeyPath = "";
    /**
     * 商户证书序列号
     */
    private String merchantSerialNumber = "";
    /**
     * 商户APIV3密钥
     */
    private String apiV3key = "";
}
