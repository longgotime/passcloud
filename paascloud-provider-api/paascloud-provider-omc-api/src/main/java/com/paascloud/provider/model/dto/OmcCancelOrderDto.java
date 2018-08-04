package com.paascloud.provider.model.dto;

import com.paascloud.base.dto.LoginAuthDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * The type Omc cancel order dto.
 * @author paascloud.net @gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OmcCancelOrderDto implements Serializable {
    private static final long serialVersionUID = 7872719757344531689L;
    private String orderNo;

    private LoginAuthDto loginAuthDto;

}
