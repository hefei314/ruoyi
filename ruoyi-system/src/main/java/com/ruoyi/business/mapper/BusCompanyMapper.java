package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.BusCompany;

import java.util.List;

/**
 * 商户信息Mapper接口
 *
 * @author ruoyi
 * @date 2022-11-09
 */
public interface BusCompanyMapper {
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
     * 查询商户信息
     *
     * @param companyName 商户名称
     * @return 商户信息
     */
    public BusCompany selectBusCompanyByCompanyName(String companyName);

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
     * 删除商户信息
     *
     * @param companyId 商户信息主键
     * @return 结果
     */
    public int deleteBusCompanyByCompanyId(Long companyId);

    /**
     * 批量删除商户信息
     *
     * @param companyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusCompanyByCompanyIds(String[] companyIds);

    /**
     * 查询用户收藏的商户列表
     *
     * @param useId 用户ID
     * @return
     */
    public List<BusCompany> selectUserCompanyByUserId(Long useId);

    /**
     * 获取公司数量
     */
    public int countCompany();

    /**
     * 获取已开通会员公司数量
     */
    public int countMemberCompany();
}
