package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.BusPayLog;
import com.ruoyi.business.mapper.BusPayLogMapper;
import com.ruoyi.business.service.IBusPayLogService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付记录Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-06
 */
@Service
public class BusPayLogServiceImpl implements IBusPayLogService {
    @Autowired
    private BusPayLogMapper busPayLogMapper;

    /**
     * 查询支付记录
     *
     * @param outTradeNo 支付记录流水号
     * @return 支付记录
     */
    @Override
    public BusPayLog selectBusPayLogByOutTradeNo(String outTradeNo) {
        return busPayLogMapper.selectBusPayLogByOutTradeNo(outTradeNo);
    }


    /**
     * 查询支付记录列表
     *
     * @param busPayLog 支付记录
     * @return 支付记录
     */
    @Override
    public List<BusPayLog> selectBusPayLogList(BusPayLog busPayLog) {
        return busPayLogMapper.selectBusPayLogList(busPayLog);
    }

    /**
     * 新增支付记录
     *
     * @param busPayLog 支付记录
     * @return 结果
     */
    @Override
    public int insertBusPayLog(BusPayLog busPayLog) {
        return busPayLogMapper.insertBusPayLog(busPayLog);
    }

    /**
     * 修改支付记录
     *
     * @param busPayLog 支付记录
     * @return 结果
     */
    @Override
    public int updateBusPayLog(BusPayLog busPayLog) {
        return busPayLogMapper.updateBusPayLog(busPayLog);
    }

    /**
     * 批量删除支付记录
     *
     * @param outTradeNos 需要删除的支付记录流水号
     * @return 结果
     */
    @Override
    public int deleteBusPayLogByOutTradeNos(String outTradeNos) {
        return busPayLogMapper.deleteBusPayLogByOutTradeNos(Convert.toStrArray(outTradeNos));
    }

    /**
     * 删除支付记录信息
     *
     * @param outTradeNo 支付记录流水号
     * @return 结果
     */
    @Override
    public int deleteBusPayLogByOutTradeNo(String outTradeNo) {
        return busPayLogMapper.deleteBusPayLogByOutTradeNo(outTradeNo);
    }
}
