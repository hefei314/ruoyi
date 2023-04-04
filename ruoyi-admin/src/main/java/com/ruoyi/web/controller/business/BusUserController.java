package com.ruoyi.web.controller.business;

import com.ruoyi.business.domain.BusUser;
import com.ruoyi.business.service.IBusUserService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息Controller
 *
 * @author ruoyi
 * @date 2022-11-04
 */
@Controller
@RequestMapping("/business/user")
public class BusUserController extends BaseController {
    private String prefix = "business/user";

    @Autowired
    private IBusUserService busUserService;

    @RequiresPermissions("business:user:view")
    @GetMapping()
    public String user() {
        return prefix + "/user";
    }

    /**
     * 查询用户信息列表
     */
    @RequiresPermissions("business:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusUser busUser) {
        startPage();
        List<BusUser> list = busUserService.selectBusUserList(busUser);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @RequiresPermissions("business:user:export")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusUser busUser) {
        List<BusUser> list = busUserService.selectBusUserList(busUser);
        ExcelUtil<BusUser> util = new ExcelUtil<BusUser>(BusUser.class);
        return util.exportExcel(list, "用户信息数据");
    }

    /**
     * 新增用户信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存用户信息
     */
    @RequiresPermissions("business:user:add")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusUser busUser) {
        if (StringUtils.isNotEmpty(busUser.getPhonenumber())
                && UserConstants.USER_PHONE_NOT_UNIQUE.equals(busUserService.checkPhoneUnique(busUser))) {
            if (StringUtils.isNotEmpty(busUser.getUserName())) {
                return error("新增用户'" + busUser.getUserName() + "'失败，手机号码已存在");
            } else {
                return error("新增用户失败，手机号码已存在");
            }
        }
        busUser.setCreateBy(getLoginName());
        return toAjax(busUserService.insertBusUser(busUser));
    }

    /**
     * 修改用户信息
     */
    @RequiresPermissions("business:user:edit")
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        BusUser busUser = busUserService.selectBusUserByUserId(userId);
        mmap.put("busUser", busUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户信息
     */
    @RequiresPermissions("business:user:edit")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusUser busUser) {
        if (StringUtils.isNotEmpty(busUser.getPhonenumber())
                && UserConstants.USER_PHONE_NOT_UNIQUE.equals(busUserService.checkPhoneUnique(busUser))) {
            if (StringUtils.isNotEmpty(busUser.getUserName())) {
                return error("修改用户'" + busUser.getUserName() + "'失败，手机号码已存在");
            } else {
                return error("修改用户失败，手机号码已存在");
            }
        }
        busUser.setUpdateBy(getLoginName());
        return toAjax(busUserService.updateBusUser(busUser));
    }

    /**
     * 删除用户信息
     */
    @RequiresPermissions("business:user:remove")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(busUserService.deleteBusUserByUserIds(ids));
    }
}
