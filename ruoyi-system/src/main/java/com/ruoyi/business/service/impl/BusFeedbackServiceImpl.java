package com.ruoyi.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.BusFeedbackMapper;
import com.ruoyi.business.domain.BusFeedback;
import com.ruoyi.business.service.IBusFeedbackService;
import com.ruoyi.common.core.text.Convert;

/**
 * 意见反馈信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-08
 */
@Service
public class BusFeedbackServiceImpl implements IBusFeedbackService 
{
    @Autowired
    private BusFeedbackMapper busFeedbackMapper;

    /**
     * 查询意见反馈信息
     * 
     * @param feedbackId 意见反馈信息主键
     * @return 意见反馈信息
     */
    @Override
    public BusFeedback selectBusFeedbackByFeedbackId(Long feedbackId)
    {
        return busFeedbackMapper.selectBusFeedbackByFeedbackId(feedbackId);
    }

    /**
     * 查询意见反馈信息列表
     * 
     * @param busFeedback 意见反馈信息
     * @return 意见反馈信息
     */
    @Override
    public List<BusFeedback> selectBusFeedbackList(BusFeedback busFeedback)
    {
        return busFeedbackMapper.selectBusFeedbackList(busFeedback);
    }

    /**
     * 新增意见反馈信息
     * 
     * @param busFeedback 意见反馈信息
     * @return 结果
     */
    @Override
    public int insertBusFeedback(BusFeedback busFeedback)
    {
        return busFeedbackMapper.insertBusFeedback(busFeedback);
    }

    /**
     * 修改意见反馈信息
     * 
     * @param busFeedback 意见反馈信息
     * @return 结果
     */
    @Override
    public int updateBusFeedback(BusFeedback busFeedback)
    {
        return busFeedbackMapper.updateBusFeedback(busFeedback);
    }

    /**
     * 批量删除意见反馈信息
     * 
     * @param feedbackIds 需要删除的意见反馈信息主键
     * @return 结果
     */
    @Override
    public int deleteBusFeedbackByFeedbackIds(String feedbackIds)
    {
        return busFeedbackMapper.deleteBusFeedbackByFeedbackIds(Convert.toStrArray(feedbackIds));
    }

    /**
     * 删除意见反馈信息信息
     * 
     * @param feedbackId 意见反馈信息主键
     * @return 结果
     */
    @Override
    public int deleteBusFeedbackByFeedbackId(Long feedbackId)
    {
        return busFeedbackMapper.deleteBusFeedbackByFeedbackId(feedbackId);
    }
}
