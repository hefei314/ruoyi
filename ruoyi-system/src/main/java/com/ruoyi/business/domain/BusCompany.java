package com.ruoyi.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 商户信息对象 bus_company
 *
 * @author ruoyi
 * @date 2022-11-11
 */
public class BusCompany extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 分类ID
     */
    @Excel(name = "分类ID")
    private Long classifyId;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String classifyName;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 公司简称
     */
    @Excel(name = "公司简称")
    private String shortName;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 公司LOGO
     */
    @Excel(name = "公司LOGO")
    private String companyLogo;

    /**
     * 公司官网
     */
    @Excel(name = "公司官网")
    private String website;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String contactWay;

    /**
     * 法定代表
     */
    @Excel(name = "法定代表")
    private String legalPerson;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registerDate;

    /**
     * 营业开始时间
     */
    @Excel(name = "营业开始时间")
    private String openingStartTime;

    /**
     * 营业结束时间
     */
    @Excel(name = "营业结束时间")
    private String openingEndTime;

    /**
     * 公司规模
     */
    @Excel(name = "公司规模")
    private String scale;

    /**
     * 公司规模
     */
    @Excel(name = "公司规模名称")
    private String scaleName;

    /**
     * 注册资金
     */
    @Excel(name = "注册资金")
    private String registerCapital;

    /**
     * 注册资金
     */
    @Excel(name = "注册资金名称")
    private String registerCapitalName;

    /**
     * 主营业务
     */
    @Excel(name = "主营业务")
    private String companyBusiness;

    /**
     * 公司介绍
     */
    @Excel(name = "公司介绍")
    private String introduction;

    /**
     * 省ID
     */
    @Excel(name = "省ID")
    private Long provinceId;

    /**
     * 省名
     */
    @Excel(name = "省名")
    private String provinceName;

    /**
     * 市ID
     */
    @Excel(name = "市ID")
    private Long cityId;

    /**
     * 市名
     */
    @Excel(name = "市名")
    private String cityName;

    /**
     * 区ID
     */
    @Excel(name = "区ID")
    private Long areaId;

    /**
     * 区名
     */
    @Excel(name = "区名")
    private String areaName;

    /**
     * 公司地址
     */
    @Excel(name = "公司地址")
    private String address;

    /**
     * 公司经度
     */
    @Excel(name = "公司经度")
    private Double latitude;

    /**
     * 公司纬度
     */
    @Excel(name = "公司纬度")
    private Double longitude;

    /**
     * 营业执照
     */
    @Excel(name = "营业执照")
    private String businessLicense;

    /**
     * 身份证正面
     */
    @Excel(name = "身份证正面")
    private String identityCardFront;

    /**
     * 身份证背面
     */
    @Excel(name = "身份证背面")
    private String identityCardBack;

    /**
     * 公司状态（0正常 1停用）
     */
    @Excel(name = "公司状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 会员标志（Y代表是 N代表否）
     */
    @Excel(name = "会员标志", readConverterExp = "Y=代表是,N=代表否")
    private String memberFlag;

    /**
     * 会员开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "会员开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date memberBeginDate;

    /**
     * 会员结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "会员结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date memberEndDate;

    /**
     * 会员开通区域-省ID
     */
    @Excel(name = "会员开通区域-省ID")
    private Long memberProvinceId;

    /**
     * 会员开通区域-省名称
     */
    @Excel(name = "会员开通区域-省名称")
    private String memberProvinceName;

    /**
     * 会员开通区域-市ID
     */
    @Excel(name = "会员开通区域-市ID")
    private Long memberCityId;

    /**
     * 会员开通区域-市名称
     */
    @Excel(name = "会员开通区域-市名称")
    private String memberCityName;

    /**
     * 会员开通区域-区/县ID
     */
    @Excel(name = "会员开通区域-区/县ID")
    private Long memberAreaId;

    /**
     * 会员开通区域-区/县名称
     */
    @Excel(name = "会员开通区域-区/县名称")
    private String memberAreaName;

    /**
     * 会员排序
     */
    @Excel(name = "会员排序")
    private Long memberOrder;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setOpeningStartTime(String openingStartTime) {
        this.openingStartTime = openingStartTime;
    }

    public String getOpeningStartTime() {
        return openingStartTime;
    }

    public void setOpeningEndTime(String openingEndTime) {
        this.openingEndTime = openingEndTime;
    }

    public String getOpeningEndTime() {
        return openingEndTime;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getScale() {
        return scale;
    }

    public String getScaleName() {
        return scaleName;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

    public void setRegisterCapital(String registerCapital) {
        this.registerCapital = registerCapital;
    }

    public String getRegisterCapital() {
        return registerCapital;
    }

    public String getRegisterCapitalName() {
        return registerCapitalName;
    }

    public void setRegisterCapitalName(String registerCapitalName) {
        this.registerCapitalName = registerCapitalName;
    }

    public void setCompanyBusiness(String companyBusiness) {
        this.companyBusiness = companyBusiness;
    }

    public String getCompanyBusiness() {
        return companyBusiness;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setIdentityCardFront(String identityCardFront) {
        this.identityCardFront = identityCardFront;
    }

    public String getIdentityCardFront() {
        return identityCardFront;
    }

    public void setIdentityCardBack(String identityCardBack) {
        this.identityCardBack = identityCardBack;
    }

    public String getIdentityCardBack() {
        return identityCardBack;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setMemberFlag(String memberFlag) {
        this.memberFlag = memberFlag;
    }

    public String getMemberFlag() {
        return memberFlag;
    }

    public void setMemberBeginDate(Date memberBeginDate) {
        this.memberBeginDate = memberBeginDate;
    }

    public Date getMemberBeginDate() {
        return memberBeginDate;
    }

    public void setMemberEndDate(Date memberEndDate) {
        this.memberEndDate = memberEndDate;
    }

    public Date getMemberEndDate() {
        return memberEndDate;
    }

    public void setMemberProvinceId(Long memberProvinceId) {
        this.memberProvinceId = memberProvinceId;
    }

    public Long getMemberProvinceId() {
        return memberProvinceId;
    }

    public void setMemberProvinceName(String memberProvinceName) {
        this.memberProvinceName = memberProvinceName;
    }

    public String getMemberProvinceName() {
        return memberProvinceName;
    }

    public void setMemberCityId(Long memberCityId) {
        this.memberCityId = memberCityId;
    }

    public Long getMemberCityId() {
        return memberCityId;
    }

    public void setMemberCityName(String memberCityName) {
        this.memberCityName = memberCityName;
    }

    public String getMemberCityName() {
        return memberCityName;
    }

    public void setMemberAreaId(Long memberAreaId) {
        this.memberAreaId = memberAreaId;
    }

    public Long getMemberAreaId() {
        return memberAreaId;
    }

    public void setMemberAreaName(String memberAreaName) {
        this.memberAreaName = memberAreaName;
    }

    public String getMemberAreaName() {
        return memberAreaName;
    }

    public void setMemberOrder(Long memberOrder) {
        this.memberOrder = memberOrder;
    }

    public Long getMemberOrder() {
        return memberOrder;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("companyId", getCompanyId())
                .append("classifyId", getClassifyId())
                .append("classifyName", getClassifyName())
                .append("userId", getUserId())
                .append("shortName", getShortName())
                .append("companyName", getCompanyName())
                .append("companyLogo", getCompanyLogo())
                .append("website", getWebsite())
                .append("contactWay", getContactWay())
                .append("legalPerson", getLegalPerson())
                .append("registerDate", getRegisterDate())
                .append("openingStartTime", getOpeningStartTime())
                .append("openingEndTime", getOpeningEndTime())
                .append("scale", getScale())
                .append("scaleName", getScaleName())
                .append("registerCapital", getRegisterCapital())
                .append("registerCapitalName", getRegisterCapitalName())
                .append("companyBusiness", getCompanyBusiness())
                .append("introduction", getIntroduction())
                .append("provinceId", getProvinceId())
                .append("provinceName", getProvinceName())
                .append("cityId", getCityId())
                .append("cityName", getCityName())
                .append("areaId", getAreaId())
                .append("areaName", getAreaName())
                .append("address", getAddress())
                .append("latitude", getLatitude())
                .append("longitude", getLongitude())
                .append("businessLicense", getBusinessLicense())
                .append("identityCardFront", getIdentityCardFront())
                .append("identityCardBack", getIdentityCardBack())
                .append("status", getStatus())
                .append("memberFlag", getMemberFlag())
                .append("memberBeginDate", getMemberBeginDate())
                .append("memberEndDate", getMemberEndDate())
                .append("memberProvinceId", getMemberProvinceId())
                .append("memberProvinceName", getMemberProvinceName())
                .append("memberCityId", getMemberCityId())
                .append("memberCityName", getMemberCityName())
                .append("memberAreaId", getMemberAreaId())
                .append("memberAreaName", getMemberAreaName())
                .append("memberOrder", getMemberOrder())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createDate", getCreateDate())
                .append("updateBy", getUpdateBy())
                .append("updateDate", getUpdateDate())
                .toString();
    }
}
