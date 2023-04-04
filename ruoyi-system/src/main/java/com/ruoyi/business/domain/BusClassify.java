package com.ruoyi.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 分类信息对象 bus_classify
 *
 * @author ruoyi
 * @date 2022-11-07
 */
public class BusClassify extends TreeEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    private Long classifyId;

    /**
     * 分类Logo
     */
    private String classifyLogo;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String classifyName;

    /**
     * 分类状态（0正常 1停用）
     */
    @Excel(name = "分类状态", readConverterExp = "0=正常,1=停用")
    private String status;

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

    private List<BusClassify> child;

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public String getClassifyLogo() {
        return classifyLogo;
    }

    public void setClassifyLogo(String classifyLogo) {
        this.classifyLogo = classifyLogo;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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

    public List<BusClassify> getChild() {
        return child;
    }

    public void setChild(List<BusClassify> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("classifyId", getClassifyId())
                .append("parentId", getParentId())
                .append("ancestors", getAncestors())
                .append("classifyName", getClassifyName())
                .append("orderNum", getOrderNum())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createDate", getCreateDate())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
