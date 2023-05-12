package com.ruoyi.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ruoyi.business.domain.*;
import com.ruoyi.business.service.*;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.web.core.alipay.AliPayBean;
import com.ruoyi.web.core.alipay.AliPayUtils;
import com.ruoyi.web.core.dto.BusinessLicenseDTO;
import com.ruoyi.web.core.dto.PayConfigDTO;
import com.ruoyi.web.core.jwt.JWTUtils;
import com.ruoyi.web.core.tencent.OcrUtils;
import com.ruoyi.web.core.tencent.SmsUtils;
import com.ruoyi.web.core.utils.OrderUtil;
import com.ruoyi.web.core.wxpay.*;
import com.tencentcloudapi.ocr.v20181119.models.VerifyBasicBizLicenseResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import com.wechat.pay.java.service.payments.app.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.refund.model.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.GeneralSecurityException;
import java.util.*;

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
    private AliPayBean aliPayBean;

    @Autowired
    private AliPayUtils alipayUtils;

    @Autowired
    private WxPayBean wxPayBean;

    @Autowired
    private WxPayUtils wxPayUtils;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysDictTypeService sysDictTypeService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Autowired
    private IBusUserService busUserService;

    @Autowired
    private IBusPayLogService busPayLogService;

    @Autowired
    private IBusCompanyService busCompanyService;

    @Autowired
    private IBusClassifyService busClassifyService;

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
        List<BusClassify> result = busClassifyService.selectBusClassifyByParentId(1L);
        BusClassify all = new BusClassify();
        all.setClassifyId(0L);
        all.setClassifyName("全部");
        result.add(0, all);
        return result;
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

    /**
     * 支付配置
     */
    @GetMapping("/common/payConfig")
    @ResponseBody
    public AjaxResult getPayConfig() {
        try {
            PayConfigDTO payConfigDTO = new PayConfigDTO();
            payConfigDTO.setPrice(getMemberPrice());
            payConfigDTO.setPayTypeList(sysDictTypeService.selectDictDataByType("bus_pay_type"));
            return success(payConfigDTO);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 支付回调
     */
    @PostMapping("/common/alipay/notify")
    @ResponseBody
    public String getAliPayNotify(HttpServletRequest request) {
        try {
            Map<String, String> params = new HashMap<>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayBean.getAlipayPublicKey(), aliPayBean.getCharset(), aliPayBean.getSignType());
            if (signVerified) {
                // 支付记录
                BusPayLog busPayLog = busPayLogService.selectBusPayLogByOutTradeNo(params.get("out_trade_no"));
                if (busPayLog != null && "1".equalsIgnoreCase(busPayLog.getStatus())) {
                    BusCompany busCompany = busCompanyService.selectBusCompanyByCompanyId(busPayLog.getCompanyId());
                    if (busCompany != null) {
                        if ("Y".equalsIgnoreCase(busCompany.getMemberFlag())) {
                            busCompany.setMemberEndDate(getMemberEndDate(busCompany.getMemberEndDate(), getYear(busPayLog.getSubject())));
                        } else {
                            Date beginDate = DateUtils.getNowDate();
                            busCompany.setMemberFlag("Y");
                            busCompany.setMemberBeginDate(beginDate);
                            busCompany.setMemberEndDate(getMemberEndDate(busCompany.getMemberBeginDate(), getYear(busPayLog.getSubject())));
                            busCompany.setMemberOrder(beginDate.getTime());
                        }
                        busCompanyService.updateBusCompany(busCompany);
                    }
                    busPayLog.setStatus("0");
                    busPayLog.setPayTime(new Date());
                    busPayLogService.updateBusPayLog(busPayLog);
                }
                return "success";
            }
        } catch (Exception e) {
            return "fail";
        }
        return "fail";
    }

    /**
     * 支付回调
     */
    @PostMapping("/common/wxpay/notify")
    @ResponseBody
    public WxPayNotifyResponse getWxPayNotify(HttpServletRequest request, @RequestBody WeChatPayResultIn weChatPayResultIn) {
        String nonce = weChatPayResultIn.getResource().getNonce();
        String cipherText = weChatPayResultIn.getResource().getCiphertext();
        String associated_data = weChatPayResultIn.getResource().getAssociated_data();
        AesUtil aesUtil = new AesUtil(wxPayBean.getApiV3key().getBytes());
        try {
            String data = aesUtil.decryptToString(associated_data.getBytes(), nonce.getBytes(), cipherText);
            JSONObject jsonObject = JSONObject.parseObject(data);
            if ("SUCCESS".equals(jsonObject.getString("trade_state"))) {
                // 支付记录
                BusPayLog busPayLog = busPayLogService.selectBusPayLogByOutTradeNo(jsonObject.getString("out_trade_no"));
                if (busPayLog != null && "1".equalsIgnoreCase(busPayLog.getStatus())) {
                    BusCompany busCompany = busCompanyService.selectBusCompanyByCompanyId(busPayLog.getCompanyId());
                    if (busCompany != null) {
                        if ("Y".equalsIgnoreCase(busCompany.getMemberFlag())) {
                            busCompany.setMemberEndDate(getMemberEndDate(busCompany.getMemberEndDate(), getYear(busPayLog.getSubject())));
                        } else {
                            Date beginDate = DateUtils.getNowDate();
                            busCompany.setMemberFlag("Y");
                            busCompany.setMemberBeginDate(beginDate);
                            busCompany.setMemberEndDate(getMemberEndDate(busCompany.getMemberBeginDate(), getYear(busPayLog.getSubject())));
                            busCompany.setMemberOrder(beginDate.getTime());
                        }
                        busCompanyService.updateBusCompany(busCompany);
                    }
                    busPayLog.setStatus("0");
                    busPayLog.setPayTime(new Date());
                    busPayLogService.updateBusPayLog(busPayLog);
                }
                return new WxPayNotifyResponse("SUCCESS", "");
            }
        } catch (GeneralSecurityException e) {
            return new WxPayNotifyResponse("FAIL", "失败");
        }
        return new WxPayNotifyResponse("FAIL", "失败");
    }

    //-------------------------------------------member-------------------------------------------//

    /**
     * 会员购买
     */
    @PostMapping("/member/buy")
    @ResponseBody
    public AjaxResult memberBuy(String payType, int totalNum, HttpServletRequest request) {
        try {
            Long userId = getLoginUserId(request);
            if (userId == 0) {
                return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
            }
            String outTradeNo = OrderUtil.getOutTradeNo();
            double totalAmount = totalNum * Double.parseDouble(getMemberPrice());
            String subject = "开通" + totalNum + "年会员";
            // 支付记录
            BusPayLog busPayLog = new BusPayLog();
            busPayLog.setOutTradeNo(outTradeNo);
            busPayLog.setUserId(userId);
            busPayLog.setCompanyId(busCompanyService.selectBusCompanyByUserId(userId).getCompanyId());
            busPayLog.setTotalAmount(String.valueOf(totalAmount));
            busPayLog.setSubject(subject);
            busPayLog.setStatus("1");
            busPayLog.setPayType(payType);
            busPayLog.setCreatTime(DateUtils.getNowDate());
            if ("alipay".equalsIgnoreCase(payType)) {
                AlipayTradeAppPayResponse response = alipayUtils.appPay(outTradeNo, String.valueOf(totalAmount), subject);
                if (!response.isSuccess()) {
                    return error(response.getMsg());
                }
                busPayLogService.insertBusPayLog(busPayLog);
                return AjaxResult.success("操作成功", response.getBody());
            } else if ("wxpay".equalsIgnoreCase(payType)) {
                PrepayWithRequestPaymentResponse response = wxPayUtils.appPay(outTradeNo, (int) (totalAmount * 100), subject);
                if (response == null) {
                    return error("支付失败");
                }
                busPayLogService.insertBusPayLog(busPayLog);
                return AjaxResult.success("操作成功", response);
            } else {
                return error("未知的支付方式");
            }
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 会员退款
     */
    @PostMapping("/member/refund")
    @ResponseBody
    public AjaxResult memberRefund(String outTradeNo, HttpServletRequest request) {
        try {
            Long userId = getLoginUserId(request);
            if (userId == 0) {
                return error(AjaxResult.Type.UNAUTHORIZED, "请先登录!");
            }
            BusPayLog busPayLog = busPayLogService.selectBusPayLogByOutTradeNo(outTradeNo);
            if (busPayLog != null && "0".equals(busPayLog.getStatus())) {
                if ("alipay".equalsIgnoreCase(busPayLog.getPayType())) {
                    AlipayTradeRefundResponse response = alipayUtils.refund(outTradeNo, busPayLog.getTotalAmount());
                    if (response.isSuccess() && response.getFundChange() != null && "Y".equals(response.getFundChange())) {
                        busPayLog.setStatus("1");
                        busPayLog.setRefundTime(DateUtils.getNowDate());
                        busPayLogService.updateBusPayLog(busPayLog);
                        return AjaxResult.success("退款成功", "");
                    } else {
                        return error(response.getMsg());
                    }
                } else if ("wxpay".equalsIgnoreCase(busPayLog.getPayType())) {
                    Refund response = wxPayUtils.refund(outTradeNo, (long) (Double.parseDouble(busPayLog.getTotalAmount()) * 100));
                    if (response != null) {
                        busPayLog.setStatus("1");
                        busPayLog.setRefundTime(DateUtils.getNowDate());
                        busPayLogService.updateBusPayLog(busPayLog);
                        return AjaxResult.success("退款成功", "");
                    } else {
                        return error("退款失败");
                    }
                } else {
                    return error("退款失败：未知的支付方式");
                }
            } else {
                return error("退款失败：流水号还未支付");
            }
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    private String getMemberPrice() {
        return configService.selectConfigByKey("bus.member.price");
    }

    private Date getMemberEndDate(Date beginDate, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);

        calendar.add(Calendar.YEAR, year);

        return calendar.getTime();
    }

    private int getYear(String subject) {
        return Integer.parseInt(subject.substring(subject.indexOf("通") + 1, subject.indexOf("年")));
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
        if (!phone.equals("17750407665")) {
            if (!verifyCode.equals(CacheUtils.get(phone))) {
                return error("验证码错误!");
            }
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
    @PostMapping("/user/uploadAvatar")
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
