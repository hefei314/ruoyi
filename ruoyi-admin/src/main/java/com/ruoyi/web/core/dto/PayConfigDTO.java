package com.ruoyi.web.core.dto;

import com.ruoyi.common.core.domain.entity.SysDictData;

import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/05/05
 *     desc  :
 * </pre>
 */
public class PayConfigDTO {

    private String price;

    private List<SysDictData> payTypeList;

    private List<SysDictData> payYearList;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<SysDictData> getPayTypeList() {
        return payTypeList;
    }

    public void setPayTypeList(List<SysDictData> payTypeList) {
        this.payTypeList = payTypeList;
    }

    public List<SysDictData> getPayYearList() {
        return payYearList;
    }

    public void setPayYearList(List<SysDictData> payYearList) {
        this.payYearList = payYearList;
    }
}
