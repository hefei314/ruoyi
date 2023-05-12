package com.ruoyi.web.core.wxpay;

import com.ruoyi.web.core.utils.OrderUtil;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.service.payments.app.AppServiceExtension;
import com.wechat.pay.java.service.payments.app.model.Amount;
import com.wechat.pay.java.service.payments.app.model.PrepayRequest;
import com.wechat.pay.java.service.payments.app.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/05/09
 *     desc  :
 * </pre>
 */
@Slf4j
@Service
public class WxPayUtils {

    public static final String NOTIFY_URL = "http://www.sijieshu.com/api/common/wxpay/notify";

    @Autowired
    private WxPayBean wxPayBean;

    @Autowired
    @Qualifier("wxPayService")
    private AppServiceExtension wxPayService;
    @Autowired
    @Qualifier("wxRefundService")
    private RefundService wxRefundService;

    public Refund refund(String outTradeNo, long refundAmount) {
        try {
            CreateRequest request = new CreateRequest();
            request.setOutTradeNo(outTradeNo);
            request.setOutRefundNo(OrderUtil.getOutTradeNo());
            AmountReq amount = new AmountReq();
            amount.setRefund(refundAmount);
            amount.setTotal(refundAmount);
            amount.setCurrency("CNY");
            request.setAmount(amount);
            return wxRefundService.create(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrepayWithRequestPaymentResponse appPay(String outTradeNo, int totalAmount, String subject) {
        try {
            PrepayRequest request = new PrepayRequest();
            request.setAppid(wxPayBean.getAppId());
            request.setMchid(wxPayBean.getMerchantId());
            request.setOutTradeNo(outTradeNo);
            request.setDescription(subject);
            request.setNotifyUrl(NOTIFY_URL);
            Amount amount = new Amount();
            amount.setTotal(totalAmount);
            amount.setCurrency("CNY");
            request.setAmount(amount);
            return wxPayService.prepayWithRequestPayment(request);
        } catch (HttpException e) {
            // 调用微信支付服务，当发生 HTTP 请求异常时抛出该异常
            e.printStackTrace();
        } catch (ValidationException e) {
            // 当验证微信支付签名失败时抛出该异常
            e.printStackTrace();
        } catch (ServiceException e) {
            // 调用微信支付服务，发送 HTTP 请求成功，HTTP 状态码小于200或大于等于300
            e.printStackTrace();
        } catch (MalformedMessageException e) {
            // 服务返回成功，返回体类型不合法，或者解析返回体失败
            e.printStackTrace();
        }
        return null;
    }
}
