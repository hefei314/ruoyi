package com.ruoyi.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 省市县信息对象 bus_area
 *
 * @author ruoyi
 * @date 2022-11-09
 */
public class BusArea extends TreeEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 区域ID
     */
    private Long areaId;

    /**
     * 区域名称
     */
    @Excel(name = "区域名称")
    private String name;

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("areaId", getAreaId())
                .append("parentId", getParentId())
                .append("name", getName())
                .toString();
    }
}
