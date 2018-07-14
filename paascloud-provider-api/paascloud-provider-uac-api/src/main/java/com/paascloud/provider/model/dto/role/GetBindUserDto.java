package com.paascloud.provider.model.dto.role;

import com.paascloud.base.dto.LoginAuthDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Get bind user dto.
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018 -07-14 上午2:34
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetBindUserDto implements Serializable {

    private Long roleId;

    private LoginAuthDto loginAuthDto;
}
