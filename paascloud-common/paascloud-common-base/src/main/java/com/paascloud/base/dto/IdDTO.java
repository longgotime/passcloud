package com.paascloud.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 主键和操作信息
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018-07-14 上午9:56
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IdDTO implements Serializable {
    private static final long serialVersionUID = 7770980592462280849L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 操作人信息
     */
    private LoginAuthDto loginAuthDto;
}
