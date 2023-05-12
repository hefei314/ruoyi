package com.ruoyi.web.core.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/05/05
 *     desc  :
 * </pre>
 */
public class OrderUtil {

    public static String getOutTradeNo() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String localDate = LocalDateTime.now().format(dateTimeFormatter);
        String randomNumeric = RandomStringUtils.randomNumeric(3);
        return localDate + randomNumeric;
    }

}
