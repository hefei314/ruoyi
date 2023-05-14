package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.BusCompany;
import com.ruoyi.business.domain.BusUserCompany;
import com.ruoyi.business.mapper.BusCompanyMapper;
import com.ruoyi.business.mapper.BusUserCompanyMapper;
import com.ruoyi.business.service.IBusAreaService;
import com.ruoyi.business.service.IBusClassifyService;
import com.ruoyi.business.service.IBusCompanyService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商户信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-08
 */
@Service
public class BusCompanyServiceImpl implements IBusCompanyService {

    @Autowired
    private IBusAreaService busAreaService;

    @Autowired
    private IBusClassifyService busClassifyService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Autowired
    private BusCompanyMapper companyMapper;

    @Autowired
    private BusUserCompanyMapper userCompanyMapper;

    /**
     * 查询商户信息
     *
     * @param companyId 商户信息主键
     * @return 商户信息
     */
    @Override
    public BusCompany selectBusCompanyByCompanyId(Long companyId) {
        return companyMapper.selectBusCompanyByCompanyId(companyId);
    }

    /**
     * 查询商户信息
     *
     * @param userId 商户信息主键
     * @return 商户信息
     */
    @Override
    public BusCompany selectBusCompanyByUserId(Long userId) {
        return companyMapper.selectBusCompanyByUserId(userId);
    }

    /**
     * 查询当前最大的排序商户
     *
     * @return 商户信息
     */
    @Override
    public BusCompany selectBusCompanyMaxMemberOrder() {
        return companyMapper.selectBusCompanyMaxMemberOrder();
    }

    /**
     * 查询商户信息列表
     *
     * @param busCompany 商户信息
     * @return 商户信息
     */
    @Override
    public List<BusCompany> selectBusCompanyList(BusCompany busCompany) {
        return companyMapper.selectBusCompanyList(busCompany);
    }

    /**
     * 新增商户信息
     *
     * @param busCompany 商户信息
     * @return 结果
     */
    @Override
    public int insertBusCompany(BusCompany busCompany) {
        busCompany.setCreateDate(DateUtils.getNowDate());
        return companyMapper.insertBusCompany(busCompany);
    }

    /**
     * 修改商户信息
     *
     * @param busCompany 商户信息
     * @return 结果
     */
    @Override
    public int updateBusCompany(BusCompany busCompany) {
        busCompany.setUpdateDate(DateUtils.getNowDate());
        return companyMapper.updateBusCompany(busCompany);
    }

    /**
     * 批量删除商户信息
     *
     * @param companyIds 需要删除的商户信息主键
     * @return 结果
     */
    @Override
    public int deleteBusCompanyByCompanyIds(String companyIds) {
        return companyMapper.deleteBusCompanyByCompanyIds(Convert.toStrArray(companyIds));
    }

    /**
     * 删除商户信息信息
     *
     * @param companyId 商户信息主键
     * @return 结果
     */
    @Override
    public int deleteBusCompanyByCompanyId(Long companyId) {
        return companyMapper.deleteBusCompanyByCompanyId(companyId);
    }

    /**
     * 查看用户收藏的商户列表
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<BusCompany> selectUserCompanyByUserId(Long userId) {
        return companyMapper.selectUserCompanyByUserId(userId);
    }

    /**
     * 检验用户是否收藏商户
     *
     * @param userId    用户ID
     * @param companyId 公司ID
     * @return 结果
     */
    @Override
    public int checkCompanyCollect(Long userId, Long companyId) {
        BusUserCompany userCompany = new BusUserCompany();
        userCompany.setUserId(userId);
        userCompany.setCompanyId(companyId);
        return userCompanyMapper.countUserCompany(userCompany);
    }

    /**
     * 更新用户是否收藏商户状态
     *
     * @param userId    用户ID
     * @param companyId 公司ID
     * @param status    0 取消收藏 1 收藏
     * @return
     */
    @Override
    public int updateCompanyCollectStatus(Long userId, Long companyId, int status) {
        BusUserCompany userCompany = new BusUserCompany();
        userCompany.setUserId(userId);
        userCompany.setCompanyId(companyId);
        if (status == 0) {
            return userCompanyMapper.deleteUserCompany(userCompany);
        } else {
            return userCompanyMapper.insertUserCompany(userCompany);
        }
    }

    /**
     * 导入用户数据
     *
     * @param companyList     商户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importCompany(List<BusCompany> companyList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(companyList) || companyList.size() == 0) {
            throw new ServiceException("导入商户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (BusCompany company : companyList) {
            try {
                company.setClassifyName(busClassifyService.selectBusClassifyByClassifyId(company.getClassifyId()).getClassifyName());
                company.setScaleName(sysDictDataService.selectDictLabel("bus_company_scale", company.getScale()));
                company.setRegisterCapitalName(sysDictDataService.selectDictLabel("bus_register_capital", company.getRegisterCapital()));
                company.setProvinceId(busAreaService.selectBusAreaByAreaName(company.getProvinceName()).getAreaId());
                company.setCityId(busAreaService.selectBusAreaByAreaName(company.getCityName()).getAreaId());
                company.setAreaId(busAreaService.selectBusAreaByAreaName(company.getAreaName()).getAreaId());
                // 验证是否存在这个商户
                BusCompany u = companyMapper.selectBusCompanyByCompanyName(company.getCompanyName());
                if (StringUtils.isNull(u)) {
                    company.setCreateBy(operName);
                    this.insertBusCompany(company);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、商户 " + company.getCompanyName() + " 导入成功");
                } else if (isUpdateSupport) {
                    company.setCompanyId(u.getCompanyId());
                    company.setUpdateBy(operName);
                    this.updateBusCompany(company);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、商户 " + company.getCompanyName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、商户 " + company.getCompanyName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、商户 " + company.getCompanyName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 获取公司数量
     */
    @Override
    public int countCompany() {
        return companyMapper.countCompany();
    }

    /**
     * 获取已开通会员公司数量
     */
    @Override
    public int countMemberCompany() {
        return companyMapper.countMemberCompany();
    }
}
