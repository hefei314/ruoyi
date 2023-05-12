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
public class WechatPayResourceIn {

    private String original_type;
    private String algorithm;
    private String ciphertext;
    private String associated_data;
    private String nonce;

}
