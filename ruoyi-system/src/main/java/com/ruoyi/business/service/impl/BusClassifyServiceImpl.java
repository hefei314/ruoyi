package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.BusClassify;
import com.ruoyi.business.mapper.BusClassifyMapper;
import com.ruoyi.business.service.IBusClassifyService;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-07
 */
@Service
public class BusClassifyServiceImpl implements IBusClassifyService {
    @Autowired
    private BusClassifyMapper busClassifyMapper;

    /**
     * 查询分类信息
     *
     * @param classifyId 分类信息主键
     * @return 分类信息
     */
    @Override
    public BusClassify selectBusClassifyByClassifyId(Long classifyId) {
        return busClassifyMapper.selectBusClassifyByClassifyId(classifyId);
    }

    /**
     * 查询分类信息
     *
     * @param parentId 分类信息父级主键
     * @return 分类信息
     */
    @Override
    public List<BusClassify> selectBusClassifyByParentId(Long parentId) {
        return busClassifyMapper.selectBusClassifyByParentId(parentId);
    }

    /**
     * 查询分类信息列表
     *
     * @param busClassify 分类信息
     * @return 分类信息
     */
    @Override
    public List<BusClassify> selectBusClassifyList(BusClassify busClassify) {
        return busClassifyMapper.selectBusClassifyList(busClassify);
    }

    /**
     * 新增分类信息
     *
     * @param busClassify 分类信息
     * @return 结果
     */
    @Override
    public int insertBusClassify(BusClassify busClassify) {
        BusClassify info = busClassifyMapper.selectBusClassifyByClassifyId(busClassify.getParentId());
        // 如果父节点不为"正常"状态,则不允许新增子节点
        if (UserConstants.CLASSIFY_DISABLE.equals(info.getStatus())) {
            throw new ServiceException("分类停用，不允许新增");
        }
        // 当前最多二级分类
        if (info.getAncestors().split(",").length > 2) {
            throw new ServiceException("分类最多二级，不允许新增");
        }
        busClassify.setAncestors(info.getAncestors() + "," + busClassify.getParentId());
        busClassify.setCreateDate(DateUtils.getNowDate());
        return busClassifyMapper.insertBusClassify(busClassify);
    }

    /**
     * 修改分类信息
     *
     * @param busClassify 分类信息
     * @return 结果
     */
    @Override
    public int updateBusClassify(BusClassify busClassify) {
        BusClassify newParentClassify = busClassifyMapper.selectBusClassifyByClassifyId(busClassify.getParentId());
        BusClassify oldClassify = selectBusClassifyByClassifyId(busClassify.getClassifyId());
        if (StringUtils.isNotNull(newParentClassify) && StringUtils.isNotNull(oldClassify)) {
            String newAncestors = newParentClassify.getAncestors() + "," + newParentClassify.getClassifyId();
            String oldAncestors = oldClassify.getAncestors();
            busClassify.setAncestors(newAncestors);
            updateClassifyChildren(busClassify.getClassifyId(), newAncestors, oldAncestors);
        }
        busClassify.setUpdateTime(DateUtils.getNowDate());
        int result = busClassifyMapper.updateBusClassify(busClassify);
        if (UserConstants.CLASSIFY_NORMAL.equals(busClassify.getStatus()) && StringUtils.isNotEmpty(busClassify.getAncestors())
                && !StringUtils.equals("0", busClassify.getAncestors())) {
            // 如果该分类是启用状态，则启用该分类的所有上级分类
            updateParentClassifyStatusNormal(busClassify);
        }
        return result;
    }

    /**
     * 修改该分类的父级分类状态
     *
     * @param busClassify 当前分类
     */
    private void updateParentClassifyStatusNormal(BusClassify busClassify) {
        String ancestors = busClassify.getAncestors();
        Long[] classifyIds = Convert.toLongArray(ancestors);
        busClassifyMapper.updateBusClassifyStatusNormal(classifyIds);
    }

    /**
     * 修改子元素关系
     *
     * @param classifyId   被修改的分类ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateClassifyChildren(Long classifyId, String newAncestors, String oldAncestors) {
        List<BusClassify> children = busClassifyMapper.selectChildrenClassifyById(classifyId);
        for (BusClassify child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            busClassifyMapper.updateClassifyChildren(children);
        }
    }

    /**
     * 批量删除分类信息
     *
     * @param classifyIds 需要删除的分类信息主键
     * @return 结果
     */
    @Override
    public int deleteBusClassifyByClassifyIds(String classifyIds) {
        return busClassifyMapper.deleteBusClassifyByClassifyIds(Convert.toStrArray(classifyIds));
    }

    /**
     * 删除分类信息信息
     *
     * @param classifyId 分类信息主键
     * @return 结果
     */
    @Override
    public int deleteBusClassifyByClassifyId(Long classifyId) {
        return busClassifyMapper.deleteBusClassifyByClassifyId(classifyId);
    }

    /**
     * 查询分类信息树列表
     *
     * @return 所有分类信息信息
     */
    @Override
    public List<Ztree> selectBusClassifyTree() {
        List<BusClassify> busClassifyList = busClassifyMapper.selectBusClassifyList(new BusClassify());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (BusClassify busClassify : busClassifyList) {
            Ztree ztree = new Ztree();
            ztree.setId(busClassify.getClassifyId());
            ztree.setpId(busClassify.getParentId());
            ztree.setName(busClassify.getClassifyName());
            ztree.setTitle(busClassify.getClassifyName());
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
