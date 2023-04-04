package com.ruoyi.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 意见反馈信息对象 bus_feedback
 * 
 * @author ruoyi
 * @date 2022-11-08
 */
public class BusFeedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 意见反馈ID */
    private Long feedbackId;

    /** 用户ID */
    private Long userId;

    /** 意见反馈内容 */
    @Excel(name = "意见反馈内容")
    private String feedbackContent;

    /** 反馈者 */
    @Excel(name = "反馈者")
    private String feedbackBy;

    /** 反馈时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date feedbackTime;

    public void setFeedbackId(Long feedbackId) 
    {
        this.feedbackId = feedbackId;
    }

    public Long getFeedbackId() 
    {
        return feedbackId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setFeedbackContent(String feedbackContent) 
    {
        this.feedbackContent = feedbackContent;
    }

    public String getFeedbackContent() 
    {
        return feedbackContent;
    }
    public void setFeedbackBy(String feedbackBy) 
    {
        this.feedbackBy = feedbackBy;
    }

    public String getFeedbackBy() 
    {
        return feedbackBy;
    }
    public void setFeedbackTime(Date feedbackTime) 
    {
        this.feedbackTime = feedbackTime;
    }

    public Date getFeedbackTime() 
    {
        return feedbackTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("feedbackId", getFeedbackId())
            .append("userId", getUserId())
            .append("feedbackContent", getFeedbackContent())
            .append("feedbackBy", getFeedbackBy())
            .append("feedbackTime", getFeedbackTime())
            .toString();
    }
}
