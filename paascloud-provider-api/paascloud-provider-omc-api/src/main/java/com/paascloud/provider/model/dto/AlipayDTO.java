package com.paascloud.provider.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Alipay dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlipayDTO implements Serializable {

    private static final long serialVersionUID = 958825818044390919L;
    private String orderNo;
    private String tradeNo;
    private String tradeStatus;
    private Date paymentTime;
}
