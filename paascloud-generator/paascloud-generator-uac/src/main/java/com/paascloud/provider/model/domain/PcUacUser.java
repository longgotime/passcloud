package com.paascloud.provider.model.domain;

import java.util.Date;

public class PcUacUser {
    /**
     * pc_uac_user.id;ID
     */
    private Long id;

    /**
     * pc_uac_user.version;版本号
     */
    private Integer version;

    /**
     * pc_uac_user.login_name;登录名
     */
    private String loginName;

    /**
     * pc_uac_user.login_pwd;登录密码
     */
    private String loginPwd;

    /**
     * pc_uac_user.salt;盐,用于shiro加密, 字段停用
     */
    private String salt;

    /**
     * pc_uac_user.user_code;工号
     */
    private String userCode;

    /**
     * pc_uac_user.user_name;姓名
     */
    private String userName;

    /**
     * pc_uac_user.mobile_no;手机号
     */
    private String mobileNo;

    /**
     * pc_uac_user.email;邮件地址
     */
    private String email;

    /**
     * pc_uac_user.status;状态
     */
    private String status;

    /**
     * pc_uac_user.user_source;用户来源
     */
    private String userSource;

    /**
     * pc_uac_user.type;操作员类型（2000伙伴，3000客户，1000运营）
     */
    private String type;

    /**
     * pc_uac_user.last_login_ip;最后登录IP地址
     */
    private String lastLoginIp;

    /**
     * pc_uac_user.last_login_location;最后登录位置
     */
    private String lastLoginLocation;

    /**
     * pc_uac_user.remark;描述
     */
    private String remark;

    /**
     * pc_uac_user.last_login_time;最后登录时间
     */
    private Date lastLoginTime;

    /**
     * pc_uac_user.is_changed_pwd;是否更改过密码
     */
    private Short isChangedPwd;

    /**
     * pc_uac_user.pwd_error_count;连续输错密码次数（连续5次输错就冻结帐号）
     */
    private Short pwdErrorCount;

    /**
     * pc_uac_user.pwd_error_time;最后输错密码时间
     */
    private Date pwdErrorTime;

    /**
     * pc_uac_user.creator;创建人
     */
    private String creator;

    /**
     * pc_uac_user.creator_id;创建人ID
     */
    private Long creatorId;

    /**
     * pc_uac_user.created_time;创建时间
     */
    private Date createdTime;

    /**
     * pc_uac_user.last_operator;最近操作人
     */
    private String lastOperator;

    /**
     * pc_uac_user.last_operator_id;最后操作人ID
     */
    private Long lastOperatorId;

    /**
     * pc_uac_user.update_time;更新时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUserSource() {
        return userSource;
    }

    public void setUserSource(String userSource) {
        this.userSource = userSource == null ? null : userSource.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public String getLastLoginLocation() {
        return lastLoginLocation;
    }

    public void setLastLoginLocation(String lastLoginLocation) {
        this.lastLoginLocation = lastLoginLocation == null ? null : lastLoginLocation.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Short getIsChangedPwd() {
        return isChangedPwd;
    }

    public void setIsChangedPwd(Short isChangedPwd) {
        this.isChangedPwd = isChangedPwd;
    }

    public Short getPwdErrorCount() {
        return pwdErrorCount;
    }

    public void setPwdErrorCount(Short pwdErrorCount) {
        this.pwdErrorCount = pwdErrorCount;
    }

    public Date getPwdErrorTime() {
        return pwdErrorTime;
    }

    public void setPwdErrorTime(Date pwdErrorTime) {
        this.pwdErrorTime = pwdErrorTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator == null ? null : lastOperator.trim();
    }

    public Long getLastOperatorId() {
        return lastOperatorId;
    }

    public void setLastOperatorId(Long lastOperatorId) {
        this.lastOperatorId = lastOperatorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}