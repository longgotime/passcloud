package com.paascloud.provider.model.dto;

import com.paascloud.provider.model.vo.CartProductVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The type Cart list query.
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018 /7/15 下午8:58
 */
@Data
public class CartListQuery implements Serializable {
    private static final long serialVersionUID = -5968284112162772265L;
    private List<CartProductVo> cartProductVoList;
}
