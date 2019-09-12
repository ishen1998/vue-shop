package com.shop.module.addressManager.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * customer_address_r
 * @author 
 */
public class CustomerAddressR implements Serializable {
    /**
     * 客户地址id
     */
    private String customerAddressId;

    /**
     * 客户id
     */
    private String cId;

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    /**
     * 客户id
     */
    private String consigneeName;

    /**
     * 收件人手机号
     */
    private String consigneeMobile;

    /**
     * 收件人地址
     */
    private String consigineeAddress;

    /**
     * 邮政编号
     */
    private String postCode;

    /**
     * 默认地址状态
     */
    private String addressStatus;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(String customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public String getConsigineeAddress() {
        return consigineeAddress;
    }

    public void setConsigineeAddress(String consigineeAddress) {
        this.consigineeAddress = consigineeAddress;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(String addressStatus) {
        this.addressStatus = addressStatus;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CustomerAddressR other = (CustomerAddressR) that;
        return (this.getCustomerAddressId() == null ? other.getCustomerAddressId() == null : this.getCustomerAddressId().equals(other.getCustomerAddressId()))
            && (this.getcId() == null ? other.getcId() == null : this.getcId().equals(other.getcId()))
            && (this.getConsigneeMobile() == null ? other.getConsigneeMobile() == null : this.getConsigneeMobile().equals(other.getConsigneeMobile()))
            && (this.getConsigineeAddress() == null ? other.getConsigineeAddress() == null : this.getConsigineeAddress().equals(other.getConsigineeAddress()))
            && (this.getPostCode() == null ? other.getPostCode() == null : this.getPostCode().equals(other.getPostCode()))
            && (this.getAddressStatus() == null ? other.getAddressStatus() == null : this.getAddressStatus().equals(other.getAddressStatus()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCustomerAddressId() == null) ? 0 : getCustomerAddressId().hashCode());
        result = prime * result + ((getcId() == null) ? 0 : getcId().hashCode());
        result = prime * result + ((getConsigneeMobile() == null) ? 0 : getConsigneeMobile().hashCode());
        result = prime * result + ((getConsigineeAddress() == null) ? 0 : getConsigineeAddress().hashCode());
        result = prime * result + ((getPostCode() == null) ? 0 : getPostCode().hashCode());
        result = prime * result + ((getAddressStatus() == null) ? 0 : getAddressStatus().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", customerAddressId=").append(customerAddressId);
        sb.append(", cId=").append(cId);
        sb.append(", consigneeMobile=").append(consigneeMobile);
        sb.append(", consigineeAddress=").append(consigineeAddress);
        sb.append(", postCode=").append(postCode);
        sb.append(", addressStatus=").append(addressStatus);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}