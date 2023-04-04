package com.ruoyi.business.service;

import com.ruoyi.business.domain.BusArea;

import java.util.List;

/**
 * 省市县信息Service接口
 *
 * @author ruoyi
 * @date 2022-11-09
 */
public interface IBusAreaService {
    /**
     * 查询省市县信息
     *
     * @param areaId 省市县信息主键
     * @return 省市县信息
     */
    public BusArea selectBusAreaByAreaId(Long areaId);

    /**
     * 查询省市县信息
     *
     * @param parentId 省市县信息父级ID
     * @return 省市县信息
     */
    public List<BusArea> selectBusAreaListByParentId(Long parentId);

    /**
     * 查询省市县信息
     *
     * @param areaName 省市县信息名称
     * @return 省市县信息
     */
    public BusArea selectBusAreaByAreaName(String areaName);

    /**
     * 查询省市县信息列表
     *
     * @param busArea 省市县信息
     * @return 省市县信息集合
     */
    public List<BusArea> selectBusAreaList(BusArea busArea);

}
