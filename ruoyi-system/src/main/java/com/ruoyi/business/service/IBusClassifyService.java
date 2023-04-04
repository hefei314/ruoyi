package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.BusClassify;
import com.ruoyi.common.core.domain.Ztree;

/**
 * 分类信息Service接口
 * 
 * @author ruoyi
 * @date 2022-11-07
 */
public interface IBusClassifyService 
{
    /**
     * 查询分类信息
     * 
     * @param classifyId 分类信息主键
     * @return 分类信息
     */
    public BusClassify selectBusClassifyByClassifyId(Long classifyId);

    /**
     * 查询分类信息
     *
     * @param parentId 分类信息父级主键
     * @return 分类信息
     */
    public List<BusClassify> selectBusClassifyByParentId(Long parentId);

    /**
     * 查询分类信息列表
     * 
     * @param busClassify 分类信息
     * @return 分类信息集合
     */
    public List<BusClassify> selectBusClassifyList(BusClassify busClassify);

    /**
     * 新增分类信息
     * 
     * @param busClassify 分类信息
     * @return 结果
     */
    public int insertBusClassify(BusClassify busClassify);

    /**
     * 修改分类信息
     * 
     * @param busClassify 分类信息
     * @return 结果
     */
    public int updateBusClassify(BusClassify busClassify);

    /**
     * 批量删除分类信息
     * 
     * @param classifyIds 需要删除的分类信息主键集合
     * @return 结果
     */
    public int deleteBusClassifyByClassifyIds(String classifyIds);

    /**
     * 删除分类信息信息
     * 
     * @param classifyId 分类信息主键
     * @return 结果
     */
    public int deleteBusClassifyByClassifyId(Long classifyId);

    /**
     * 查询分类信息树列表
     * 
     * @return 所有分类信息信息
     */
    public List<Ztree> selectBusClassifyTree();
}
