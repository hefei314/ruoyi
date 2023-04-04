package com.ruoyi.business.service.impl;

import java.util.List;

import com.ruoyi.business.domain.BusCompany;
import com.ruoyi.business.mapper.BusUserCompanyMapper;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.BusUserMapper;
import com.ruoyi.business.domain.BusUser;
import com.ruoyi.business.service.IBusUserService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-04
 */
@Service
public class BusUserServiceImpl implements IBusUserService 
{
    @Autowired
    private BusUserMapper userMapper;

    /**
     * 查询用户信息
     * 
     * @param userId 用户信息主键
     * @return 用户信息
     */
    @Override
    public BusUser selectBusUserByUserId(Long userId)
    {
        return userMapper.selectBusUserByUserId(userId);
    }

    /**
     * 查询用户信息
     *
     * @param phonenumber 用户手机号码
     * @return 用户信息
     */
    @Override
    public BusUser selectBusUserByPhone(String phonenumber) {
        return userMapper.selectBusUserByPhone(phonenumber);
    }

    /**
     * 查询用户信息列表
     * 
     * @param busUser 用户信息
     * @return 用户信息
     */
    @Override
    public List<BusUser> selectBusUserList(BusUser busUser)
    {
        return userMapper.selectBusUserList(busUser);
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param busUser 用户信息
     * @return 结果
     */
    @Override
    public String checkPhoneUnique(BusUser busUser) {
        Long userId = StringUtils.isNull(busUser.getUserId()) ? -1L : busUser.getUserId();
        BusUser info = userMapper.checkPhoneUnique(busUser.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 新增用户信息
     * 
     * @param busUser 用户信息
     * @return 结果
     */
    @Override
    public int insertBusUser(BusUser busUser)
    {
        busUser.setCreateTime(DateUtils.getNowDate());
        return userMapper.insertBusUser(busUser);
    }

    /**
     * 修改用户信息
     * 
     * @param busUser 用户信息
     * @return 结果
     */
    @Override
    public int updateBusUser(BusUser busUser)
    {
        busUser.setUpdateTime(DateUtils.getNowDate());
        return userMapper.updateBusUser(busUser);
    }

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteBusUserByUserIds(String userIds)
    {
        return userMapper.deleteBusUserByUserIds(Convert.toStrArray(userIds));
    }

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteBusUserByUserId(Long userId)
    {
        return userMapper.deleteBusUserByUserId(userId);
    }

    /**
     * 获取用户数量
     */
    @Override
    public int countUser() {
        return userMapper.countUser();
    }
}
