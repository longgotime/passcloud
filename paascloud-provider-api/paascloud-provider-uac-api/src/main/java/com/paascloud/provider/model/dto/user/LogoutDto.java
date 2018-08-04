package com.paascloud.provider.model.dto.user;

import com.paascloud.base.dto.LoginAuthDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Logout dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class LogoutDto implements Serializable {
    private static final long serialVersionUID = 8117196962960951870L;

    private String accessToken;

    private LoginAuthDto loginAuthDto;
}
