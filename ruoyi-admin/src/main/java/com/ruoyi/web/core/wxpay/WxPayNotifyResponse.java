package com.ruoyi.web.core.wxpay;

import lombok.Data;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/05/11
 *     desc  :
 * </pre>
 */
@Data
public class WxPayNotifyResponse {

    private String code;
    private String message;

    public WxPayNotifyResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
