package com.ruoyi.web.core.tencent;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/12/10
 *     desc  :
 * </pre>
 */
public class BusinessLicenseDTO {

    private String imageUrl;
    private String companyName;
    private String legalPerson;
    private String registerDate;
    private Integer registerCapital;
    private String registerCapitalName;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public Integer getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(Integer registerCapital) {
        this.registerCapital = registerCapital;
    }

    public String getRegisterCapitalName() {
        return registerCapitalName;
    }

    public void setRegisterCapitalName(String registerCapitalName) {
        this.registerCapitalName = registerCapitalName;
    }
}
