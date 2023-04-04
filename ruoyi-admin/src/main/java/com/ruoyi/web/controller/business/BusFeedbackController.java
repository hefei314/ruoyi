package com.ruoyi.web.controller.business;

import java.util.List;
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
import com.ruoyi.business.domain.BusFeedback;
import com.ruoyi.business.service.IBusFeedbackService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 意见反馈信息Controller
 * 
 * @author ruoyi
 * @date 2022-11-08
 */
@Controller
@RequestMapping("/business/feedback")
public class BusFeedbackController extends BaseController
{
    private String prefix = "business/feedback";

    @Autowired
    private IBusFeedbackService busFeedbackService;

    @RequiresPermissions("business:feedback:view")
    @GetMapping()
    public String feedback()
    {
        return prefix + "/feedback";
    }

    /**
     * 查询意见反馈信息列表
     */
    @RequiresPermissions("business:feedback:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusFeedback busFeedback)
    {
        startPage();
        List<BusFeedback> list = busFeedbackService.selectBusFeedbackList(busFeedback);
        return getDataTable(list);
    }

    /**
     * 导出意见反馈信息列表
     */
    @RequiresPermissions("business:feedback:export")
    @Log(title = "意见反馈信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusFeedback busFeedback)
    {
        List<BusFeedback> list = busFeedbackService.selectBusFeedbackList(busFeedback);
        ExcelUtil<BusFeedback> util = new ExcelUtil<BusFeedback>(BusFeedback.class);
        return util.exportExcel(list, "意见反馈信息数据");
    }

    @RequiresPermissions("business:feedback:detail")
    @GetMapping("/detail/{feedbackId}")
    public String detail(@PathVariable("feedbackId") Long feedbackId, ModelMap mmap)
    {
        mmap.put("busFeedback", busFeedbackService.selectBusFeedbackByFeedbackId(feedbackId));
        return prefix + "/detail";
    }

    /**
     * 新增意见反馈信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存意见反馈信息
     */
    @RequiresPermissions("business:feedback:add")
    @Log(title = "意见反馈信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusFeedback busFeedback)
    {
        return toAjax(busFeedbackService.insertBusFeedback(busFeedback));
    }

    /**
     * 修改意见反馈信息
     */
    @RequiresPermissions("business:feedback:edit")
    @GetMapping("/edit/{feedbackId}")
    public String edit(@PathVariable("feedbackId") Long feedbackId, ModelMap mmap)
    {
        BusFeedback busFeedback = busFeedbackService.selectBusFeedbackByFeedbackId(feedbackId);
        mmap.put("busFeedback", busFeedback);
        return prefix + "/edit";
    }

    /**
     * 修改保存意见反馈信息
     */
    @RequiresPermissions("business:feedback:edit")
    @Log(title = "意见反馈信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusFeedback busFeedback)
    {
        return toAjax(busFeedbackService.updateBusFeedback(busFeedback));
    }

    /**
     * 删除意见反馈信息
     */
    @RequiresPermissions("business:feedback:remove")
    @Log(title = "意见反馈信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(busFeedbackService.deleteBusFeedbackByFeedbackIds(ids));
    }
}
