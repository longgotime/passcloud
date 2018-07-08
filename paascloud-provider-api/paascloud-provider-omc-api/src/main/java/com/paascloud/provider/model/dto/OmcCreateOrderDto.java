package com.paascloud.provider.model.dto;

import com.paascloud.base.dto.LoginAuthDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>Title: OmcCreateOrderDto . </p>
 * <p>创建订单实体  </p>
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018-07-08 下午7:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OmcCreateOrderDto implements Serializable {
    private Long shippingId;

    private LoginAuthDto loginAuthDto;

}
