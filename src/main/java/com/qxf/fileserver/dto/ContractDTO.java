package com.qxf.fileserver.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ContractDTO {

    private String tripartiteAgreementNo;

    private Double interest;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getTripartiteAgreementNo() {
        return tripartiteAgreementNo;
    }

    public void setTripartiteAgreementNo(String tripartiteAgreementNo) {
        this.tripartiteAgreementNo = tripartiteAgreementNo;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
