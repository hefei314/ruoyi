package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.BusClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类信息Mapper接口
 *
 * @author ruoyi
 * @date 2022-11-07
 */
public interface BusClassifyMapper {
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
     * 修改子元素关系
     *
     * @param classifys 子元素
     * @return 结果
     */
    public int updateClassifyChildren(@Param("classifys") List<BusClassify> classifys);

    /**
     * 删除分类信息
     *
     * @param classifyId 分类信息主键
     * @return 结果
     */
    public int deleteBusClassifyByClassifyId(Long classifyId);

    /**
     * 批量删除分类信息
     *
     * @param classifyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusClassifyByClassifyIds(String[] classifyIds);

    /**
     * 根据ID查询所有子分类
     *
     * @param classifyId 分类ID
     * @return 分类列表
     */
    public List<BusClassify> selectChildrenClassifyById(Long classifyId);

    /**
     * 修改所在分类正常状态
     *
     * @param classifyIds 分类ID组
     */
    public void updateBusClassifyStatusNormal(Long[] classifyIds);
}
