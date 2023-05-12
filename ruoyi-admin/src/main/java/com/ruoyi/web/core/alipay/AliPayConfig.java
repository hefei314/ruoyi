package com.ruoyi.web.core.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/04/26
 *     desc  :
 * </pre>
 */
@Configuration
public class AliPayConfig {

    @Autowired
    private AliPayBean aliPayBean;

    @Bean(name = {"alipayClient"})
    public AlipayClient alipayClientServce() throws Exception {
        AlipayConfig alipayConfig = new AlipayConfig();
        //设置网关地址
        alipayConfig.setServerUrl(aliPayBean.getServerUrl());
        //设置应用ID
        alipayConfig.setAppId(aliPayBean.getAppId());
        //设置应用私钥
        alipayConfig.setPrivateKey(aliPayBean.getPrivateKey());
        //设置请求格式，固定值json
        alipayConfig.setFormat(aliPayBean.getFormat());
        //设置字符集
        alipayConfig.setCharset(aliPayBean.getCharset());
        //设置签名类型
        alipayConfig.setSignType(aliPayBean.getSignType());
        //设置支付宝公钥
        alipayConfig.setAlipayPublicKey(aliPayBean.getAlipayPublicKey());
        return new DefaultAlipayClient(alipayConfig);
    }
}
