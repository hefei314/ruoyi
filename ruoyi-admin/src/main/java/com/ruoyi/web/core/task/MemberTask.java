package com.ruoyi.web.core.task;

import com.ruoyi.business.domain.BusCompany;
import com.ruoyi.business.service.IBusCompanyService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2023/05/15
 *     desc  : 会员到期检测定时任务
 * </pre>
 */
@Component("memberTask")
public class MemberTask {

    @Autowired
    private IBusCompanyService companyService;

    public void checkMember() {
        System.out.println("执行无参方法");
        BusCompany company = new BusCompany();
        company.setMemberFlag("Y");
        List<BusCompany> list = companyService.selectBusCompanyList(company);
        for (BusCompany item : list) {
            if (DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD).equalsIgnoreCase(DateUtils.dateTime(item.getMemberEndDate()))) {
                BusCompany update = new BusCompany();
                update.setCompanyId(item.getCompanyId());
                update.setMemberFlag("N");
                update.setMemberBeginDate(null);
                update.setMemberEndDate(null);
                update.setMemberOrder(9999999999L);
                companyService.updateBusCompany(update);
            }
        }
    }

}
