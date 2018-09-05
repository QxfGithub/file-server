package com.qxf.fileserver.dto;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ExcelDTO {

    @ApiModelProperty("资金类型  ")
    private String fundType;
    @ApiModelProperty("监管协议号（三方协议号）")
    private String tripleAgreementNo;
    @ApiModelProperty("网签合同编号")
    private String netSignContractNo;

    @ApiModelProperty("收入金额（分）")
    private BigDecimal incomeAmount;

    @ApiModelProperty("支出金额（分）")
    private BigDecimal expensesAmount;

    @ApiModelProperty("手续费（分）")
    private BigDecimal poundage;
    @ApiModelProperty("流水类型： 收入（0），支出(1),冲减(2)")
    private String amountType;

    @ApiModelProperty("付款方式： POS（0）,转账(1)")
    private String paymentType;

    @ApiModelProperty("付款卡号")
    private String paymentBankAccount;
    @ApiModelProperty("所属银行（付款卡开户银行）")
    private String paymentBankName;
    @ApiModelProperty("监管银行名称")
    private String supervisionBankName;
    @ApiModelProperty(" 银行流水号")
    private String transactionNo;
    @ApiModelProperty("日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transactionTime;

    @ApiModelProperty("监管信息id 点击查看详情时用来跳转用")
    private Long caseId;

    @ApiModelProperty("甲方姓名(卖方)")
    private String sellerName;

    @ApiModelProperty(" 甲方受托人姓名")
    private String sellerTrusteeName;

    @ApiModelProperty(" 乙方姓名(买方)")
    private String buyerName;

    @ApiModelProperty(" 乙方受托人姓名")
    private String buyerTrusteeName;

    @ApiModelProperty("支出金额（元）")
    private Double expensesAmountYuan;

    @ApiModelProperty("收入金额（元）")
    private Double incomeAmountYuan;

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getTripleAgreementNo() {
        return tripleAgreementNo;
    }

    public void setTripleAgreementNo(String tripleAgreementNo) {
        this.tripleAgreementNo = tripleAgreementNo;
    }

    public String getNetSignContractNo() {
        return netSignContractNo;
    }

    public void setNetSignContractNo(String netSignContractNo) {
        this.netSignContractNo = netSignContractNo;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public BigDecimal getExpensesAmount() {
        return expensesAmount;
    }

    public void setExpensesAmount(BigDecimal expensesAmount) {
        this.expensesAmount = expensesAmount;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentBankAccount() {
        return paymentBankAccount;
    }

    public void setPaymentBankAccount(String paymentBankAccount) {
        this.paymentBankAccount = paymentBankAccount;
    }

    public String getPaymentBankName() {
        return paymentBankName;
    }

    public void setPaymentBankName(String paymentBankName) {
        this.paymentBankName = paymentBankName;
    }

    public String getSupervisionBankName() {
        return supervisionBankName;
    }

    public void setSupervisionBankName(String supervisionBankName) {
        this.supervisionBankName = supervisionBankName;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerTrusteeName() {
        return sellerTrusteeName;
    }

    public void setSellerTrusteeName(String sellerTrusteeName) {
        this.sellerTrusteeName = sellerTrusteeName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerTrusteeName() {
        return buyerTrusteeName;
    }

    public void setBuyerTrusteeName(String buyerTrusteeName) {
        this.buyerTrusteeName = buyerTrusteeName;
    }

    public Double getExpensesAmountYuan() {
        return expensesAmountYuan;
    }

    public void setExpensesAmountYuan(Double expensesAmountYuan) {
        this.expensesAmountYuan = expensesAmountYuan;
    }

    public Double getIncomeAmountYuan() {
        return incomeAmountYuan;
    }

    public void setIncomeAmountYuan(Double incomeAmountYuan) {
        this.incomeAmountYuan = incomeAmountYuan;
    }
}
