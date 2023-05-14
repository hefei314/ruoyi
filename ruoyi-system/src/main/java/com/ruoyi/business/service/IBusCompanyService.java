package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.BusCompany;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 商户信息Service接口
 * 
 * @author ruoyi
 * @date 2022-11-09
 */
public interface IBusCompanyService 
{
    /**
     * 查询商户信息
     * 
     * @param companyId 商户信息主键
     * @return 商户信息
     */
    public BusCompany selectBusCompanyByCompanyId(Long companyId);

    /**
     * 查询商户信息
     *
     * @param userId 商户信息主键
     * @return 商户信息
     */
    public BusCompany selectBusCompanyByUserId(Long userId);

    /**
     * 查询当前最大的排序商户
     *
     * @return 商户信息
     */
    public BusCompany selectBusCompanyMaxMemberOrder();

    /**
     * 查询商户信息列表
     * 
     * @param busCompany 商户信息
     * @return 商户信息集合
     */
    public List<BusCompany> selectBusCompanyList(BusCompany busCompany);

    /**
     * 新增商户信息
     * 
     * @param busCompany 商户信息
     * @return 结果
     */
    public int insertBusCompany(BusCompany busCompany);

    /**
     * 修改商户信息
     * 
     * @param busCompany 商户信息
     * @return 结果
     */
    public int updateBusCompany(BusCompany busCompany);

    /**
     * 批量删除商户信息
     * 
     * @param companyIds 需要删除的商户信息主键集合
     * @return 结果
     */
    public int deleteBusCompanyByCompanyIds(String companyIds);

    /**
     * 删除商户信息信息
     * 
     * @param companyId 商户信息主键
     * @return 结果
     */
    public int deleteBusCompanyByCompanyId(Long companyId);

    /**
     * 查看用户收藏的商户列表
     * @param userId 用户ID
     * @return 结果
     */
    public List<BusCompany> selectUserCompanyByUserId(Long userId);

    /**
     * 检验用户是否收藏商户
     * @param userId 用户ID
     * @param companyId 公司ID
     * @return
     */
    public int checkCompanyCollect(Long userId, Long companyId);

    /**
     * 更新用户是否收藏商户状态
     * @param userId 用户ID
     * @param companyId 公司ID
     * @param status 0 取消收藏 1 收藏
     * @return
     */
    public int updateCompanyCollectStatus(Long userId, Long companyId, int status);

    /**
     * 导入用户数据
     *
     * @param companyList 商户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importCompany(List<BusCompany> companyList, Boolean isUpdateSupport, String operName);

    /**
     * 获取公司数量
     */
    public int countCompany();

    /**
     * 获取已开通会员公司数量
     */
    public int countMemberCompany();
}
