package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.BusUserCompany;

/**
 * 用户与公司关联表 数据层
 *
 * @author ruoyi
 */
public interface BusUserCompanyMapper {

    /**
     * 新增操作日志
     *
     * @param userCompany 用户与公司关联信息
     */
    public int insertUserCompany(BusUserCompany userCompany);

    /**
     * 删除用户和公司关联
     *
     * @param userCompany 用户与公司关联信息
     * @return 结果
     */
    public int deleteUserCompany(BusUserCompany userCompany);

    /**
     * 通过用户ID删除用户和公司关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserCompanyByUserId(Long userId);

    /**
     * 查询用户和公司使用数量
     *
     * @param userCompany 用户与公司关联信息
     * @return 结果
     */
    public int countUserCompany(BusUserCompany userCompany);

    /**
     * 通过角色ID查询公司使用数量
     *
     * @param companyId 公司ID
     * @return 结果
     */
    public int countUserCompanyByCompanyId(Long companyId);

}
