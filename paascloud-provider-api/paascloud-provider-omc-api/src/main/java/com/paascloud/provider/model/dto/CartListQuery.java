package com.paascloud.provider.model.dto;

import com.paascloud.provider.model.vo.CartProductVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Title: CartListQuery<br>;
 * Description: <br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/6/25 18:02
 */
@Data
public class CartListQuery implements Serializable {
    private static final long serialVersionUID = -5968284112162772265L;
    private List<CartProductVo> cartProductVoList;
}
