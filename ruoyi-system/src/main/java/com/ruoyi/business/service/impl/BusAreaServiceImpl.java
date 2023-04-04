package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.BusArea;
import com.ruoyi.business.mapper.BusAreaMapper;
import com.ruoyi.business.service.IBusAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 省市县信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-09
 */
@Service
public class BusAreaServiceImpl implements IBusAreaService {
    @Autowired
    private BusAreaMapper busAreaMapper;

    /**
     * 查询省市县信息
     *
     * @param areaId 省市县信息主键
     * @return 省市县信息
     */
    @Override
    public BusArea selectBusAreaByAreaId(Long areaId) {
        return busAreaMapper.selectBusAreaByAreaId(areaId);
    }

    /**
     * 查询省市县信息
     *
     * @param parentId 省市县信息父级ID
     * @return 省市县信息
     */
    @Override
    public List<BusArea> selectBusAreaListByParentId(Long parentId) {
        return busAreaMapper.selectBusAreaListByParentId(parentId);
    }

    /**
     * 查询省市县信息
     *
     * @param areaName 省市县信息名称
     * @return 省市县信息
     */
    @Override
    public BusArea selectBusAreaByAreaName(String areaName) {
        return busAreaMapper.selectBusAreaByAreaName(areaName);
    }

    /**
     * 查询省市县信息列表
     *
     * @param busArea 省市县信息
     * @return 省市县信息
     */
    @Override
    public List<BusArea> selectBusAreaList(BusArea busArea) {
        return busAreaMapper.selectBusAreaList(busArea);
    }
}
