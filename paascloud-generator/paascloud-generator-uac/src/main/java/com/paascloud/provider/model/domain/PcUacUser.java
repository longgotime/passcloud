package com.paascloud.provider.model.domain;

import java.util.Date;

/**
 * The type Pc uac user.
 *
 * @author paascloud.net @gmail.com
 */
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Gets login name.
     *
     * @return the login name
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Sets login name.
     *
     * @param loginName the login name
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * Gets login pwd.
     *
     * @return the login pwd
     */
    public String getLoginPwd() {
        return loginPwd;
    }

    /**
     * Sets login pwd.
     *
     * @param loginPwd the login pwd
     */
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    /**
     * Gets salt.
     *
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Sets salt.
     *
     * @param salt the salt
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * Gets user code.
     *
     * @return the user code
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * Sets user code.
     *
     * @param userCode the user code
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * Gets mobile no.
     *
     * @return the mobile no
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets mobile no.
     *
     * @param mobileNo the mobile no
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * Gets user source.
     *
     * @return the user source
     */
    public String getUserSource() {
        return userSource;
    }

    /**
     * Sets user source.
     *
     * @param userSource the user source
     */
    public void setUserSource(String userSource) {
        this.userSource = userSource == null ? null : userSource.trim();
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * Gets last login ip.
     *
     * @return the last login ip
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * Sets last login ip.
     *
     * @param lastLoginIp the last login ip
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    /**
     * Gets last login location.
     *
     * @return the last login location
     */
    public String getLastLoginLocation() {
        return lastLoginLocation;
    }

    /**
     * Sets last login location.
     *
     * @param lastLoginLocation the last login location
     */
    public void setLastLoginLocation(String lastLoginLocation) {
        this.lastLoginLocation = lastLoginLocation == null ? null : lastLoginLocation.trim();
    }

    /**
     * Gets remark.
     *
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets remark.
     *
     * @param remark the remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * Gets last login time.
     *
     * @return the last login time
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * Sets last login time.
     *
     * @param lastLoginTime the last login time
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * Gets is changed pwd.
     *
     * @return the is changed pwd
     */
    public Short getIsChangedPwd() {
        return isChangedPwd;
    }

    /**
     * Sets is changed pwd.
     *
     * @param isChangedPwd the is changed pwd
     */
    public void setIsChangedPwd(Short isChangedPwd) {
        this.isChangedPwd = isChangedPwd;
    }

    /**
     * Gets pwd error count.
     *
     * @return the pwd error count
     */
    public Short getPwdErrorCount() {
        return pwdErrorCount;
    }

    /**
     * Sets pwd error count.
     *
     * @param pwdErrorCount the pwd error count
     */
    public void setPwdErrorCount(Short pwdErrorCount) {
        this.pwdErrorCount = pwdErrorCount;
    }

    /**
     * Gets pwd error time.
     *
     * @return the pwd error time
     */
    public Date getPwdErrorTime() {
        return pwdErrorTime;
    }

    /**
     * Sets pwd error time.
     *
     * @param pwdErrorTime the pwd error time
     */
    public void setPwdErrorTime(Date pwdErrorTime) {
        this.pwdErrorTime = pwdErrorTime;
    }

    /**
     * Gets creator.
     *
     * @return the creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets creator.
     *
     * @param creator the creator
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * Gets creator id.
     *
     * @return the creator id
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * Sets creator id.
     *
     * @param creatorId the creator id
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * Gets created time.
     *
     * @return the created time
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * Sets created time.
     *
     * @param createdTime the created time
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * Gets last operator.
     *
     * @return the last operator
     */
    public String getLastOperator() {
        return lastOperator;
    }

    /**
     * Sets last operator.
     *
     * @param lastOperator the last operator
     */
    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator == null ? null : lastOperator.trim();
    }

    /**
     * Gets last operator id.
     *
     * @return the last operator id
     */
    public Long getLastOperatorId() {
        return lastOperatorId;
    }

    /**
     * Sets last operator id.
     *
     * @param lastOperatorId the last operator id
     */
    public void setLastOperatorId(Long lastOperatorId) {
        this.lastOperatorId = lastOperatorId;
    }

    /**
     * Gets update time.
     *
     * @return the update time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets update time.
     *
     * @param updateTime the update time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}