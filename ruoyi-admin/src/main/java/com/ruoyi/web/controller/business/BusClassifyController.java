package com.ruoyi.web.controller.business;

import java.util.List;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysDept;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.business.domain.BusClassify;
import com.ruoyi.business.service.IBusClassifyService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.domain.Ztree;

/**
 * 分类信息Controller
 * 
 * @author ruoyi
 * @date 2022-11-07
 */
@Controller
@RequestMapping("/business/classify")
public class BusClassifyController extends BaseController
{
    private String prefix = "business/classify";

    @Autowired
    private IBusClassifyService busClassifyService;

    @RequiresPermissions("business:classify:view")
    @GetMapping()
    public String classify()
    {
        return prefix + "/classify";
    }

    /**
     * 查询分类信息树列表
     */
    @RequiresPermissions("business:classify:list")
    @PostMapping("/list")
    @ResponseBody
    public List<BusClassify> list(BusClassify busClassify)
    {
        List<BusClassify> list = busClassifyService.selectBusClassifyList(busClassify);
        return list;
    }

    /**
     * 导出分类信息列表
     */
    @RequiresPermissions("business:classify:export")
    @Log(title = "分类信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusClassify busClassify)
    {
        List<BusClassify> list = busClassifyService.selectBusClassifyList(busClassify);
        ExcelUtil<BusClassify> util = new ExcelUtil<BusClassify>(BusClassify.class);
        return util.exportExcel(list, "分类信息数据");
    }

    /**
     * 新增分类信息
     */
    @GetMapping(value = { "/add/{classifyId}", "/add/" })
    public String add(@PathVariable(value = "classifyId", required = false) Long classifyId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(classifyId))
        {
            mmap.put("busClassify", busClassifyService.selectBusClassifyByClassifyId(classifyId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存分类信息
     */
    @RequiresPermissions("business:classify:add")
    @Log(title = "分类信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusClassify busClassify)
    {
        busClassify.setCreateBy(getLoginName());
        return toAjax(busClassifyService.insertBusClassify(busClassify));
    }

    /**
     * 修改分类信息
     */
    @RequiresPermissions("business:classify:edit")
    @GetMapping("/edit/{classifyId}")
    public String edit(@PathVariable("classifyId") Long classifyId, ModelMap mmap)
    {
        BusClassify busClassify = busClassifyService.selectBusClassifyByClassifyId(classifyId);
        if (StringUtils.isNotNull(busClassify) && 0L == busClassify.getParentId())
        {
            busClassify.setParentName("无");
        }
        mmap.put("busClassify", busClassify);
        return prefix + "/edit";
    }

    /**
     * 修改保存分类信息
     */
    @RequiresPermissions("business:classify:edit")
    @Log(title = "分类信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusClassify busClassify)
    {
        if (busClassify.getParentId().equals(busClassify.getClassifyId()))
        {
            return error("修改分类'" + busClassify.getClassifyName() + "'失败，上级分类不能是自己");
        }
        busClassify.setUpdateBy(getLoginName());
        return toAjax(busClassifyService.updateBusClassify(busClassify));
    }

    /**
     * 删除
     */
    @RequiresPermissions("business:classify:remove")
    @Log(title = "分类信息", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{classifyId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("classifyId") Long classifyId)
    {
        return toAjax(busClassifyService.deleteBusClassifyByClassifyId(classifyId));
    }

    /**
     * 选择分类信息树
     */
    @GetMapping(value = { "/selectClassifyTree/{classifyId}", "/selectClassifyTree/" })
    public String selectClassifyTree(@PathVariable(value = "classifyId", required = false) Long classifyId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(classifyId))
        {
            mmap.put("busClassify", busClassifyService.selectBusClassifyByClassifyId(classifyId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载分类信息树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = busClassifyService.selectBusClassifyTree();
        return ztrees;
    }
}
