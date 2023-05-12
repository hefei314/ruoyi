package com.ruoyi.web.core.wxpay;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.app.AppServiceExtension;
import com.wechat.pay.java.service.refund.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/05/09
 *     desc  :
 * </pre>
 */
@Configuration
public class WxPayConfig {

    @Autowired
    private WxPayBean wxPayBean;

    @Bean(name = {"RSAAutoCertificateConfig"})
    public Config getWxPayConfig() {
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(wxPayBean.getMerchantId())
                .privateKeyFromPath(wxPayBean.getPrivateKeyPath())
                .merchantSerialNumber(wxPayBean.getMerchantSerialNumber())
                .apiV3Key(wxPayBean.getApiV3key())
                .build();
    }

    @Bean(name = {"wxPayService"})
    public AppServiceExtension getWxPayService() {
        return new AppServiceExtension.Builder().config(getWxPayConfig()).build();
    }

    @Bean(name = {"wxRefundService"})
    public RefundService getWxRefundService() {
        return new RefundService.Builder().config(getWxPayConfig()).build();
    }
}
