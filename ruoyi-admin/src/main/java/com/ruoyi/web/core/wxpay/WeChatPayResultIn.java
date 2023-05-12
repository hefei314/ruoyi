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
public class WeChatPayResultIn {

    private String id;
    private String create_time;
    private String resource_type;
    private String event_type;
    private String summary;
    private WechatPayResourceIn resource;

}
