package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/11/25
 *     desc  : 用户和公司关联 bus_user_company
 * </pre>
 */
public class BusUserCompany {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 公司ID
     */
    private Long companyId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("companyId", getCompanyId())
                .toString();
    }
}
