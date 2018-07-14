package com.paascloud.provider.model.dto.user;

import com.paascloud.base.dto.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>Title: QueryUserDto . </p>
 * <p>用户分页查询  </p>
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018-07-14 上午11:38
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class QueryUserDTO extends BaseQuery {

    private static final long serialVersionUID = -786423407521655164L;
    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 工号
     */
    private String userCode;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 状态
     */
    private String status;
}
