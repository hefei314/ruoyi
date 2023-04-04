package com.ruoyi.web.controller.business;

import com.alibaba.fastjson.JSON;
import com.ruoyi.business.domain.BusClassify;
import com.ruoyi.business.domain.BusCompany;
import com.ruoyi.business.service.IBusAreaService;
import com.ruoyi.business.service.IBusClassifyService;
import com.ruoyi.business.service.IBusCompanyService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商户信息Controller
 *
 * @author ruoyi
 * @date 2022-11-09
 */
@Controller
@RequestMapping("/business/company")
public class BusCompanyController extends BaseController {
    private String prefix = "business/company";

    @Autowired
    private IBusAreaService busAreaService;
    @Autowired
    private IBusCompanyService busCompanyService;
    @Autowired
    private IBusClassifyService busClassifyService;
    @Autowired
    private ISysDictDataService sysDictDataService;

    @RequiresPermissions("business:company:view")
    @GetMapping()
    public String company() {
        return prefix + "/company";
    }

    /**
     * 查询商户信息列表
     */
    @RequiresPermissions("business:company:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusCompany busCompany) {
        startPage();
        List<BusCompany> list = busCompanyService.selectBusCompanyList(busCompany);
        return getDataTable(list);
    }

    /**
     * 导出商户信息列表
     */
    @RequiresPermissions("business:company:export")
    @Log(title = "商户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusCompany busCompany) {
        List<BusCompany> list = busCompanyService.selectBusCompanyList(busCompany);
        ExcelUtil<BusCompany> util = new ExcelUtil<BusCompany>(BusCompany.class);
        return util.exportExcel(list, "商户信息数据");
    }

    @RequiresPermissions("business:company:import")
    @Log(title = "商户信息", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<BusCompany> util = new ExcelUtil<BusCompany>(BusCompany.class);
        List<BusCompany> companyList = util.importExcel(file.getInputStream());
        String message = busCompanyService.importCompany(companyList, updateSupport, getLoginName());
        return AjaxResult.success(message);
    }

    @RequiresPermissions("business:company:detail")
    @GetMapping("/detail/{companyId}")
    public String detail(@PathVariable("companyId") Long companyId, ModelMap mmap) {
        BusCompany busCompany = busCompanyService.selectBusCompanyByCompanyId(companyId);
        if (StringUtils.isNotNull(busCompany.getClassifyId())) {
            BusClassify busClassify = busClassifyService.selectBusClassifyByClassifyId(busCompany.getClassifyId());
            busCompany.setClassifyName(busClassify.getClassifyName());
        }
        mmap.put("scaleName", sysDictDataService.selectDictLabel("bus_company_scale", busCompany.getScale()));
        mmap.put("registerCapitalName", sysDictDataService.selectDictLabel("bus_register_capital", busCompany.getRegisterCapital()));
        mmap.put("statusName", sysDictDataService.selectDictLabel("sys_normal_disable", busCompany.getStatus()));
        mmap.put("memberFlagName", sysDictDataService.selectDictLabel("sys_yes_no", busCompany.getMemberFlag()));
        mmap.put("busCompany", busCompany);
        return prefix + "/detail";
    }

    /**
     * 新增商户信息
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("classifys", busClassifyService.selectBusClassifyByParentId(1L));
        return prefix + "/add";
    }

    /**
     * 新增保存商户信息
     */
    @RequiresPermissions("business:company:add")
    @Log(title = "商户信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusCompany busCompany) {
        if (busCompany.getMemberBeginDate() != null) {
            busCompany.setMemberOrder(busCompany.getMemberBeginDate().getTime());
        }
        if (busCompany.getProvinceId() != null && busCompany.getProvinceId() > 0) {
            busCompany.setProvinceName(busAreaService.selectBusAreaByAreaId(busCompany.getProvinceId()).getName());
        }
        if (busCompany.getCityId() != null && busCompany.getCityId() > 0) {
            busCompany.setCityName(busAreaService.selectBusAreaByAreaId(busCompany.getCityId()).getName());
        }
        if (busCompany.getAreaId() != null && busCompany.getAreaId() > 0) {
            busCompany.setAreaName(busAreaService.selectBusAreaByAreaId(busCompany.getAreaId()).getName());
        }
        if (busCompany.getMemberProvinceId() != null && busCompany.getMemberProvinceId() > 0) {
            busCompany.setMemberProvinceName(busAreaService.selectBusAreaByAreaId(busCompany.getMemberProvinceId()).getName());
        }
        if (busCompany.getMemberCityId() != null && busCompany.getMemberCityId() > 0) {
            busCompany.setMemberCityName(busAreaService.selectBusAreaByAreaId(busCompany.getMemberCityId()).getName());
        }
        if (busCompany.getMemberAreaId() != null && busCompany.getMemberAreaId() > 0) {
            busCompany.setMemberAreaName(busAreaService.selectBusAreaByAreaId(busCompany.getMemberAreaId()).getName());
        }
        if (busCompany.getScale() != null) {
            busCompany.setScaleName(DictUtils.getDictLabel("bus_company_scale", busCompany.getScale()));
        }
        if (busCompany.getRegisterCapital() != null) {
            busCompany.setRegisterCapitalName(DictUtils.getDictLabel("bus_register_capital", busCompany.getRegisterCapital()));
        }
        busCompany.setCreateBy(getLoginName());
        return toAjax(busCompanyService.insertBusCompany(busCompany));
    }

    /**
     * 修改商户信息
     */
    @RequiresPermissions("business:company:edit")
    @GetMapping("/edit/{companyId}")
    public String edit(@PathVariable("companyId") Long companyId, ModelMap mmap) {
        BusCompany busCompany = busCompanyService.selectBusCompanyByCompanyId(companyId);
        List<BusClassify> classifyList = busClassifyService.selectBusClassifyByParentId(1L);
        mmap.put("busCompany", busCompany);
        mmap.put("classifys", classifyList);
        return prefix + "/edit";
    }

    /**
     * 修改保存商户信息
     */
    @RequiresPermissions("business:company:edit")
    @Log(title = "商户信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusCompany busCompany) {
        if (busCompany.getMemberBeginDate() != null) {
            busCompany.setMemberOrder(busCompany.getMemberBeginDate().getTime());
        }
        if (busCompany.getProvinceId() != null && busCompany.getProvinceId() > 0) {
            busCompany.setProvinceName(busAreaService.selectBusAreaByAreaId(busCompany.getProvinceId()).getName());
        }
        if (busCompany.getCityId() != null && busCompany.getCityId() > 0) {
            busCompany.setCityName(busAreaService.selectBusAreaByAreaId(busCompany.getCityId()).getName());
        }
        if (busCompany.getAreaId() != null && busCompany.getAreaId() > 0) {
            busCompany.setAreaName(busAreaService.selectBusAreaByAreaId(busCompany.getAreaId()).getName());
        }
        if (busCompany.getMemberProvinceId() != null && busCompany.getMemberProvinceId() > 0) {
            busCompany.setMemberProvinceName(busAreaService.selectBusAreaByAreaId(busCompany.getMemberProvinceId()).getName());
        }
        if (busCompany.getMemberCityId() != null && busCompany.getMemberCityId() > 0) {
            busCompany.setMemberCityName(busAreaService.selectBusAreaByAreaId(busCompany.getMemberCityId()).getName());
        }
        if (busCompany.getMemberAreaId() != null && busCompany.getMemberAreaId() > 0) {
            busCompany.setMemberAreaName(busAreaService.selectBusAreaByAreaId(busCompany.getMemberAreaId()).getName());
        }
        if (busCompany.getScale() != null) {
            busCompany.setScaleName(DictUtils.getDictLabel("bus_company_scale", busCompany.getScale()));
        }
        if (busCompany.getRegisterCapital() != null) {
            busCompany.setRegisterCapitalName(DictUtils.getDictLabel("bus_register_capital", busCompany.getRegisterCapital()));
        }
        busCompany.setUpdateBy(getLoginName());
        return toAjax(busCompanyService.updateBusCompany(busCompany));
    }

    /**
     * 删除商户信息
     */
    @RequiresPermissions("business:company:remove")
    @Log(title = "商户信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(busCompanyService.deleteBusCompanyByCompanyIds(ids));
    }

    /**
     * 省数据
     */
    @GetMapping("/provinceData")
    @ResponseBody
    public String provinceData() {
        return JSON.toJSONString(busAreaService.selectBusAreaListByParentId(0L));
    }

    /**
     * 市数据
     */
    @GetMapping("/cityData")
    @ResponseBody
    public String cityData(@RequestParam(value = "provinceId") Long provinceId) {
        return JSON.toJSONString(busAreaService.selectBusAreaListByParentId(provinceId));
    }

    /**
     * 县/区数据
     */
    @GetMapping("/areaData")
    @ResponseBody
    public String areaData(@RequestParam(value = "cityId") Long cityId) {
        return JSON.toJSONString(busAreaService.selectBusAreaListByParentId(cityId));
    }

    /**
     * 市数据
     */
    @GetMapping("/memberCityData")
    @ResponseBody
    public String memberCityData(@RequestParam(value = "memberProvinceId") Long memberProvinceId) {
        return JSON.toJSONString(busAreaService.selectBusAreaListByParentId(memberProvinceId));
    }

    /**
     * 县/区数据
     */
    @GetMapping("/memberAreaData")
    @ResponseBody
    public String memberAreaData(@RequestParam(value = "memberCityId") Long memberCityId) {
        return JSON.toJSONString(busAreaService.selectBusAreaListByParentId(memberCityId));
    }
}
