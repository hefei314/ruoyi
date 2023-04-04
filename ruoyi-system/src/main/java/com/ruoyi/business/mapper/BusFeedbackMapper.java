package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.BusFeedback;

/**
 * 意见反馈信息Mapper接口
 * 
 * @author ruoyi
 * @date 2022-11-08
 */
public interface BusFeedbackMapper 
{
    /**
     * 查询意见反馈信息
     * 
     * @param feedbackId 意见反馈信息主键
     * @return 意见反馈信息
     */
    public BusFeedback selectBusFeedbackByFeedbackId(Long feedbackId);

    /**
     * 查询意见反馈信息列表
     * 
     * @param busFeedback 意见反馈信息
     * @return 意见反馈信息集合
     */
    public List<BusFeedback> selectBusFeedbackList(BusFeedback busFeedback);

    /**
     * 新增意见反馈信息
     * 
     * @param busFeedback 意见反馈信息
     * @return 结果
     */
    public int insertBusFeedback(BusFeedback busFeedback);

    /**
     * 修改意见反馈信息
     * 
     * @param busFeedback 意见反馈信息
     * @return 结果
     */
    public int updateBusFeedback(BusFeedback busFeedback);

    /**
     * 删除意见反馈信息
     * 
     * @param feedbackId 意见反馈信息主键
     * @return 结果
     */
    public int deleteBusFeedbackByFeedbackId(Long feedbackId);

    /**
     * 批量删除意见反馈信息
     * 
     * @param feedbackIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusFeedbackByFeedbackIds(String[] feedbackIds);
}
