package com.paascloud.provider.model.dto;

import com.paascloud.base.dto.LoginAuthDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * The type Omc cancel order dto.
 * @author liuzhaoming
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OmcCancelOrderDto implements Serializable {
    private String orderNo;

    private LoginAuthDto loginAuthDto;

}
