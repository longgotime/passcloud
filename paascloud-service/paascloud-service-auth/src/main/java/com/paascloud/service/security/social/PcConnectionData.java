package com.paascloud.service.security.social;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class Pc connection data.
 *
 * @author Lenovo @gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PcConnectionData {
	private String providerId;

	private String providerUserId;

	private String displayName;

	private String profileUrl;

	private String imageUrl;

	private String accessToken;

	private String secret;

	private String refreshToken;

	private Long expireTime;
}
