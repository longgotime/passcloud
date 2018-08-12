package com.paascloud.security.core;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;

/**
 * The class Pc social user.
 *
 * @author Lenovo @gmail.com
 */
@NoArgsConstructor
public class PcSocialUser implements SocialUserDetails {

	private static final String ENABLE = "ENABLE";

	private Collection<GrantedAuthority> authorities;

	private Long userId;

	private String nickName;

	private String loginName;

	private String loginPwd;

	private String status;

	private Long groupId;

	private String groupName;

	public PcSocialUser(Long userId, String loginName, String loginPwd, String nickName, Long groupId, String groupName, String status, Collection<GrantedAuthority> grantedAuthorities) {
		this.setUserId(userId);
		this.setLoginName(loginName);
		this.setLoginPwd(loginPwd);
		this.setNickName(nickName);
		this.setGroupId(groupId);
		this.setGroupName(groupName);
		this.setStatus(status);
		this.authorities = grantedAuthorities;
	}

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 */
	@Override
	public String getUserId() {
		return String.valueOf(this.userId);
	}

	/**
	 * Gets authorities.
	 *
	 * @return the authorities
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	/**
	 * Gets password.
	 *
	 * @return the password
	 */
	@Override
	public String getPassword() {
		return this.getLoginPwd();
	}

	/**
	 * Gets username.
	 *
	 * @return the username
	 */
	@Override
	public String getUsername() {
		return this.getLoginName();
	}

	/**
	 * Is account non expired boolean.
	 *
	 * @return the boolean
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Is account non locked boolean.
	 *
	 * @return the boolean
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Is credentials non expired boolean.
	 *
	 * @return the boolean
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Is enabled boolean.
	 *
	 * @return the boolean
	 */
	@Override
	public boolean isEnabled() {
		return  StringUtils.equals(this.status, ENABLE);
	}

	public void setAuthorities(final Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(final String nickName) {
		this.nickName = nickName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(final String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(final String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(final Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(final String groupName) {
		this.groupName = groupName;
	}
}
