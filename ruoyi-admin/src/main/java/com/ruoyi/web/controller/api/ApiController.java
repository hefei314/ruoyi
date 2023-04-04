package com.ruoyi.web.controller.api;

import com.ruoyi.business.domain.BusClassify;
import com.ruoyi.business.domain.BusCompany;
import com.ruoyi.business.domain.BusFeedback;
import com.ruoyi.business.domain.BusUser;
import com.ruoyi.business.service.*;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.web.core.jwt.JWTUtils;
import com.ruoyi.web.core.tencent.BusinessLicenseDTO;
import com.ruoyi.web.core.tencent.OcrUtils;
import com.ruoyi.web.core.tencent.SmsUtils;
import com.tencentcloudapi.ocr.v20181119.models.VerifyBasicBizLicenseResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/11/22
 *     desc  :
 * </pre>
 */
@Controller
@RequestMapping("/api")
public class ApiController extends BaseController {

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IBusAreaService busAreaService;
    @Autowired
    private ISysDictDataService sysDictDataService;
    @Autowired
    private IBusClassifyService busClassifyService;
    @Autowired
    private IBusUserService busUserService;
    @Autowired
    private IBusCompanyService busCompanyService;
    @Autowired
    private IBusFeedbackService busFeedbackService;

    /**
     * 查询公司规模列表
     */
    @GetMapping("/common/getScaleList")
    @ResponseBody
    public List<SysDictData> getScaleList() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("bus_company_scale");
        return sysDictDataService.selectDictDataList(sysDictData);
    }

    /**
     * 查询公司注册资金列表
     */
    @GetMapping("/common/getRegisterCapitalList")
    @ResponseBody
    public List<SysDictData> getRegisterCapitalList() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("bus_register_capital");
        return sysDictDataService.selectDictDataList(sysDictData);
    }

    /**
     * 查询公司分类列表
     */
    @GetMapping("/common/getClassifyList")
    @ResponseBody
    public List<BusClassify> getClassifyList() {
        return busClassifyService.selectBusClassifyByParentId(1L);
    }

    /**
     * 查询公司分类树
     */
    @GetMapping("/common/getAllClassifyList")
    @ResponseBody
    public List<BusClassify> getAllClassifyList() {
        List<BusClassify> list = busClassifyService.selectBusClassifyByParentId(1L);
        for (BusClassify item : list) {
            item.setChild(busClassifyService.selectBusClassifyByParentId(item.getClassifyId()));
        }
        return list;
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/common/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file) {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    //-------------------------------------------company-------------------------------------------//

    /**
     * 公司列表
     */
    @PostMapping("/company/list")
    @ResponseBody
    public AjaxResult getCompanyList(String companyName, Long classifyId, String registerCapital,
                                     Long provinceId, Long cityId, Long areaId) {
        try {
            BusCompany busCompany = new BusCompany();
            if (StringUtils.isNotEmpty(companyName)) {
                busCompany.setCompanyName(companyName);
            }
            if (classifyId != null && classifyId > 0) {
                busCompany.setClassifyId(classifyId);
            }
            if (StringUtils.isNotEmpty(registerCapital)) {
                busCompany.setRegisterCapital(registerCapital);
            }
            if (provinceId != null && provinceId > 0) {
                busCompany.setProvinceId(provinceId);
            }
            if (cityId != null && cityId > 0) {
                busCompany.setCityId(cityId);
            }
            if (areaId != null && areaId > 0) {
                busCompany.setAreaId(areaId);
            }
            busCompany.setStatus("0");
            startPage();
            List<BusCompany> list = busCompanyService.selectBusCompanyList(busCompany);
            return success(getDataTable(list));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 公司详情
     */
    @PostMapping("/company/detail")
    @ResponseBody
    public AjaxResult getCompany(Long companyId) {
        try {
            BusCompany company = busCompanyService.selectBusCompanyByCompanyId(companyId);
            return company != null ? success(company) : error("暂未创建公司");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 公司新增
     */
    @PostMapping("/company/save")
    @ResponseBody
    public AjaxResult saveCompany(@RequestBody BusCompany company, HttpServletRequest request) {
        try {
            Long userId = getLoginUserId(request);
            if (userId == 0) {
                return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
            }
            BusCompany busCompany = busCompanyService.selectBusCompanyByUserId(userId);
            if (busCompany == null) {
                // 设置用户ID
                company.setUserId(userId);
                busCompanyService.insertBusCompany(company);
            } else {
                // 更新公司信息
                busCompanyService.updateBusCompany(company);
            }
            return AjaxResult.success(busCompanyService.selectBusCompanyByUserId(userId).getCompanyId());
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 是否收藏
     */
    @PostMapping("/company/checkCompanyCollect")
    @ResponseBody
    public AjaxResult checkCompanyCollect(Long companyId, HttpServletRequest request) {
        Long userId = getLoginUserId(request);
        if (userId == 0) {
            return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
        }
        return success(busCompanyService.checkCompanyCollect(userId, companyId) > 0);
    }

    /**
     * 上传营业执照
     */
    @PostMapping("/company/uploadBusinessLicenseFile")
    @ResponseBody
    public AjaxResult uploadBusinessLicenseFile(MultipartFile file, HttpServletRequest request) {
        try {
            Long userId = getLoginUserId(request);
            if (userId == 0) {
                return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
            }
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            // 营业执照文字识别
            VerifyBasicBizLicenseResponse response = OcrUtils.basicVerifyBizLicense(url);

            BusinessLicenseDTO businessLicenseDTO = new BusinessLicenseDTO();
            if (response != null) {
                businessLicenseDTO.setImageUrl(url);
                businessLicenseDTO.setCompanyName(response.getEntname());
                businessLicenseDTO.setLegalPerson(response.getFrname());
                businessLicenseDTO.setRegisterDate(response.getOpfrom());

                if (StringUtils.isNotEmpty(response.getRegCapital())) {
                    double regCapital = Double.parseDouble(response.getRegCapital());
                    if (regCapital < 100) {
                        businessLicenseDTO.setRegisterCapital(1);
                    } else if (regCapital < 200) {
                        businessLicenseDTO.setRegisterCapital(2);
                    } else if (regCapital < 500) {
                        businessLicenseDTO.setRegisterCapital(3);
                    } else if (regCapital < 1000) {
                        businessLicenseDTO.setRegisterCapital(4);
                    } else {
                        businessLicenseDTO.setRegisterCapital(5);
                    }
                    businessLicenseDTO.setRegisterCapitalName(response.getRegCapital() + "万元");
                } else {
                    businessLicenseDTO.setRegisterCapital(0);
                    businessLicenseDTO.setRegisterCapitalName("");
                }
            }
            return AjaxResult.success(businessLicenseDTO);
        } catch (Exception e) {
            return AjaxResult.error(e.toString());
        }
    }

    //-------------------------------------------user-------------------------------------------//\

    /**
     * 登录状态
     */
    @GetMapping("/user/getLoginStatus")
    @ResponseBody
    public AjaxResult getLoginStatus(HttpServletRequest request) {
        Long userId = getLoginUserId(request);
        if (userId == 0) {
            return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
        }
        return success();
    }

    /**
     * 发送短信
     */
    @PostMapping("/user/sendVerifyCode")
    @ResponseBody
    public AjaxResult sendVerifyCode(String phone) {
        try {
            if (!SmsUtils.isValidPhoneNumber(phone)) {
                return error("请输入正确的手机号码!");
            }

            String verifyCode = SmsUtils.generateVerifyCode();

            SendStatus[] sendStatuses = SmsUtils.sendSms(phone, verifyCode);
            if (sendStatuses != null && sendStatuses.length == 1) {
                SendStatus sendStatus = sendStatuses[0];
                if ("Ok".equalsIgnoreCase(sendStatus.getCode())) {
                    CacheUtils.put(phone, verifyCode);
                    return success("短信发送成功!");
                } else {
                    return error("短信发送失败!");
                }
            } else {
                return error("短信发送失败!");
            }
        } catch (Exception e) {
            return error(e.toString());
        }
    }

    /**
     * 登录
     */
    @PostMapping("/user/login")
    @ResponseBody
    public AjaxResult login(String phone, String verifyCode, HttpServletResponse response) {
        JWTUtils jwtUtils = new JWTUtils();
        if (StringUtils.isEmpty(phone)) {
            return error("手机号码不能为空!");
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return error("验证码不能为空!");
        }
        if (!verifyCode.equals(CacheUtils.get(phone))) {
            return error("验证码错误!");
        }
        BusUser user = busUserService.selectBusUserByPhone(phone);
        if (user == null) {
            BusUser newUser = new BusUser();
            newUser.setUserName(phone);
            newUser.setPhonenumber(phone);
            int result = busUserService.insertBusUser(newUser);
            if (result > 0) {
                user = busUserService.selectBusUserByPhone(phone);
            } else {
                return error("注册失败!");
            }
        } else {
            BusCompany company = busCompanyService.selectBusCompanyByUserId(user.getUserId());
            user.setCompanyId(company != null ? company.getCompanyId() : 0L);
        }

        String userId = String.valueOf(user.getUserId());

        String token = jwtUtils.generateToken(userId);

        user.setToken(token);

        CacheUtils.remove(phone);
        CacheUtils.put(userId, token);

        response.setHeader(JWTUtils.TOKEN_HEADER, token);

        return success(user);
    }

    /**
     * 退出登录
     */
    @PostMapping("/user/logout")
    @ResponseBody
    public AjaxResult logout(HttpServletRequest request) {
        JWTUtils jwtUtils = new JWTUtils();
        String token = request.getHeader(JWTUtils.TOKEN_HEADER);
        if (!jwtUtils.validateToken(token)) {
            return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
        }

        CacheUtils.remove(jwtUtils.getUserIdFromToken(token));

        return success();
    }

    /**
     * 上传头像
     */
    @PostMapping("/common/uploadAvatar")
    @ResponseBody
    public AjaxResult uploadAvatar(MultipartFile file, HttpServletRequest request) {
        try {
            Long userId = getLoginUserId(request);
            if (userId == 0) {
                return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
            }

            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(RuoYiConfig.getUploadPath(), file);
            String url = serverConfig.getUrl() + fileName;

            BusUser busUser = busUserService.selectBusUserByUserId(userId);
            busUser.setAvatar(url);
            busUserService.updateBusUser(busUser);

            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 意见反馈
     */
    @PostMapping("/user/saveFeedback")
    @ResponseBody
    public AjaxResult saveFeedback(String content, HttpServletRequest request) {
        Long userId = getLoginUserId(request);
        if (userId == 0) {
            return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
        }
        if (StringUtils.isEmpty(content)) {
            return error("意见反馈内容为空!");
        }
        BusUser user = busUserService.selectBusUserByUserId(userId);
        BusFeedback busFeedback = new BusFeedback();
        busFeedback.setUserId(userId);
        busFeedback.setFeedbackContent(content);
        busFeedback.setFeedbackBy(user.getUserName());
        busFeedback.setFeedbackTime(DateUtils.getNowDate());
        return toAjax(busFeedbackService.insertBusFeedback(busFeedback));
    }

    /**
     * 收藏商户 0 取消收藏 1 收藏
     */
    @PostMapping("/user/updateCompanyCollectStatus")
    @ResponseBody
    public AjaxResult updateCompanyCollectStatus(Long companyId, int collectStatus, HttpServletRequest request) {
        Long userId = getLoginUserId(request);
        if (userId == 0) {
            return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
        }
        BusCompany company = busCompanyService.selectBusCompanyByCompanyId(companyId);
        if (company == null) {
            return error("没找到该公司!");
        }
        return toAjax(busCompanyService.updateCompanyCollectStatus(userId, companyId, collectStatus) > 0);
    }

    /**
     * 用户收藏的商户数量
     */
    @GetMapping("/user/getCollectCompanyNum")
    @ResponseBody
    public AjaxResult getCollectCompanyNum(HttpServletRequest request) {
        Long userId = getLoginUserId(request);
        if (userId == 0) {
            return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
        }
        return success(busCompanyService.selectUserCompanyByUserId(userId).size());
    }

    /**
     * 用户收藏的商户列表
     */
    @GetMapping("/user/getCollectCompanyList")
    @ResponseBody
    public AjaxResult getUserCollectList(HttpServletRequest request) {
        Long userId = getLoginUserId(request);
        if (userId == 0) {
            return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
        }
        return success(busCompanyService.selectUserCompanyByUserId(userId));
    }

    private Long getLoginUserId(HttpServletRequest request) {
        try {
            JWTUtils jwtUtils = new JWTUtils();
            String token = request.getHeader(JWTUtils.TOKEN_HEADER);
            logger.error(token);
            if (StringUtils.isEmpty(token) || !jwtUtils.validateToken(token)) {
                return 0L;
            }

            String userId = jwtUtils.getUserIdFromToken(token);

            String cacheToken = (String) CacheUtils.get(userId);
            if (StringUtils.isEmpty(cacheToken)) {
                return 0L;
            }

            BusUser busUser = busUserService.selectBusUserByUserId(Long.parseLong(userId));

            return busUser != null ? busUser.getUserId() : 0L;
        } catch (Exception e) {
            return 0L;
        }
    }
}
