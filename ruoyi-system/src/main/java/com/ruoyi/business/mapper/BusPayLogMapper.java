package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.BusPayLog;

import java.util.List;

/**
 * 支付记录Mapper接口
 *
 * @author ruoyi
 * @date 2023-05-06
 */
public interface BusPayLogMapper {

    /**
     * 查询支付记录
     *
     * @param outTradeNo 支付记录流水号
     * @return 支付记录
     */
    public BusPayLog selectBusPayLogByOutTradeNo(String outTradeNo);

    /**
     * 查询支付记录列表
     *
     * @param busPayLog 支付记录
     * @return 支付记录集合
     */
    public List<BusPayLog> selectBusPayLogList(BusPayLog busPayLog);

    /**
     * 新增支付记录
     *
     * @param busPayLog 支付记录
     * @return 结果
     */
    public int insertBusPayLog(BusPayLog busPayLog);

    /**
     * 修改支付记录
     *
     * @param busPayLog 支付记录
     * @return 结果
     */
    public int updateBusPayLog(BusPayLog busPayLog);

    /**
     * 删除支付记录
     *
     * @param outTradeNo 支付记录流水号
     * @return 结果
     */
    public int deleteBusPayLogByOutTradeNo(String outTradeNo);

    /**
     * 批量删除支付记录
     *
     * @param outTradeNos 需要删除的支付记录流水号集合
     * @return 结果
     */
    public int deleteBusPayLogByOutTradeNos(String[] outTradeNos);

    /**
     * 获取总收入
     */
    public double countTotalAmount();
}
