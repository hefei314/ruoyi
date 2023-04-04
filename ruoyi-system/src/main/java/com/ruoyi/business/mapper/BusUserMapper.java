package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.BusUser;

import java.util.List;

/**
 * 用户信息Mapper接口
 *
 * @author ruoyi
 * @date 2022-11-04
 */
public interface BusUserMapper {
    /**
     * 查询用户信息
     *
     * @param userId 用户信息主键
     * @return 用户信息
     */
    public BusUser selectBusUserByUserId(Long userId);

    /**
     * 查询用户信息
     *
     * @param phonenumber 用户手机号码
     * @return 用户信息
     */
    public BusUser selectBusUserByPhone(String phonenumber);

    /**
     * 查询用户信息列表
     *
     * @param busUser 用户信息
     * @return 用户信息集合
     */
    public List<BusUser> selectBusUserList(BusUser busUser);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public BusUser checkPhoneUnique(String phonenumber);

    /**
     * 新增用户信息
     *
     * @param busUser 用户信息
     * @return 结果
     */
    public int insertBusUser(BusUser busUser);

    /**
     * 修改用户信息
     *
     * @param busUser 用户信息
     * @return 结果
     */
    public int updateBusUser(BusUser busUser);

    /**
     * 删除用户信息
     *
     * @param userId 用户信息主键
     * @return 结果
     */
    public int deleteBusUserByUserId(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusUserByUserIds(String[] userIds);

    /**
     * 获取用户数量
     */
    public int countUser();

}
