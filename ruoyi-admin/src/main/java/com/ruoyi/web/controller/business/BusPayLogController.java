package com.ruoyi.web.controller.business;

import com.ruoyi.business.domain.BusPayLog;
import com.ruoyi.business.service.IBusPayLogService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 支付记录Controller
 *
 * @author ruoyi
 * @date 2023-05-06
 */
@Controller
@RequestMapping("/business/payLog")
public class BusPayLogController extends BaseController {
    private String prefix = "business/payLog";

    @Autowired
    private IBusPayLogService busPayLogService;

    @RequiresPermissions("business:payLog:view")
    @GetMapping()
    public String payLog() {
        return prefix + "/payLog";
    }

    /**
     * 查询支付记录列表
     */
    @RequiresPermissions("business:payLog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusPayLog busPayLog) {
        startPage();
        List<BusPayLog> list = busPayLogService.selectBusPayLogList(busPayLog);
        return getDataTable(list);
    }

    /**
     * 导出支付记录列表
     */
    @RequiresPermissions("business:payLog:export")
    @Log(title = "支付记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusPayLog busPayLog) {
        List<BusPayLog> list = busPayLogService.selectBusPayLogList(busPayLog);
        ExcelUtil<BusPayLog> util = new ExcelUtil<BusPayLog>(BusPayLog.class);
        return util.exportExcel(list, "支付记录数据");
    }
}
