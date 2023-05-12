package com.ruoyi.web.core.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/05/08
 *     desc  :
 * </pre>
 */
@Component
@ConfigurationProperties(prefix = "alipay")
@Data
public class AliPayBean {
    private String appId;
    private String privateKey;
    private String publicKey;
    private String serverUrl;
    private String domain;
    private String format;
    private String charset;
    private String signType;
    private String alipayPublicKey;
}