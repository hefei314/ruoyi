package com.ruoyi.web.core.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/04/26
 *     desc  :
 * </pre>
 */
@Slf4j
@Service
public class AliPayUtils {

    public static final String NOTIFY_URL = "http://www.sijieshu.com/api/common/alipay/notify";

    @Autowired
    @Qualifier("alipayClient")
    private AlipayClient alipayClient;

    /**
     * 交易查询接口
     */
    public boolean isTradeQuery(AlipayTradeQueryModel model) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.certificateExecute(request);
        if (alipayTradeQueryResponse.isSuccess()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 退款
     */
    public AlipayTradeRefundResponse refund(String outTradeNo, String refundAmount) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(outTradeNo);
        model.setRefundAmount(refundAmount);
        request.setBizModel(model);
        return alipayClient.execute(request);
    }

    /**
     * app支付
     */
    public AlipayTradeAppPayResponse appPay(String outTradeNo, String totalAmount, String subject) throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount(totalAmount);
        model.setSubject(subject);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(NOTIFY_URL);
        return alipayClient.sdkExecute(request);
    }

}
