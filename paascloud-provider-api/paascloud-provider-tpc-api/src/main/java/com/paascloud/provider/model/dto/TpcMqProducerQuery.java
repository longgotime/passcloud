package com.paascloud.provider.model.dto;

import com.paascloud.base.dto.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * The type Tpc mq producer query.
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018 -07-08上午8:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TpcMqProducerQuery extends BaseQuery implements Serializable {
    /**
     * 微服务名称
     */
    private String applicationName;

    /**
     * PID 生产者组编码
     */
    private String producerCode;

    /**
     * PID 生产者组名称
     */
    private String producerName;

    private String queryMessageUrl;

    /**
     * 状态, 0生效,10,失效
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
