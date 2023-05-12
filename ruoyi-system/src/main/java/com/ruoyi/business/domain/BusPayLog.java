package com.ruoyi.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 支付记录对象 bus_pay_log
 *
 * @author ruoyi
 * @date 2023-05-06
 */
public class BusPayLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;
    /**
     * 用户名称
     */
    @Excel(name = "用户名称")
    private String userName;

    /**
     * 商户ID
     */
    @Excel(name = "商户ID")
    private Long companyId;

    /**
     * 商户名称
     */
    @Excel(name = "商户名称")
    private String companyName;

    /**
     * 订单流水号
     */
    @Excel(name = "订单流水号")
    private String outTradeNo;

    /**
     * 订单金额
     */
    @Excel(name = "订单金额")
    private String totalAmount;

    /**
     * 订单描述
     */
    @Excel(name = "订单描述")
    private String subject;

    /**
     * 支付状态 0 成功 1 失败
     */
    @Excel(name = "支付状态 0 成功 1 失败")
    private String status;

    /**
     * 支付方式
     */
    @Excel(name = "支付方式")
    private String payType;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /**
     * 退款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date refundTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date creatTime;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("companyId", getCompanyId())
                .append("outTradeNo", getOutTradeNo())
                .append("totalAmount", getTotalAmount())
                .append("subject", getSubject())
                .append("status", getStatus())
                .append("payType", getPayType())
                .append("payTime", getPayTime())
                .append("refundTime", getRefundTime())
                .append("createTime", getCreatTime())
                .toString();
    }
}
