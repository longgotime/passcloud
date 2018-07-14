package com.paascloud.provider.model.dto;

import com.paascloud.base.dto.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * The type Tpc mq consumer query.
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018 -07-08 上午8:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TpcMqConsumerQuery extends BaseQuery implements Serializable {

    /**
     * 任务ID
     */
    private Long messageId;

    /**
     * 消息唯一标识
     */
    private String messageKey;

    /**
     * 消费者组编码
     */
    private String consumerCode;

    /**
     * 消费的数次
     */
    private Integer consumeCount;

    /**
     * 状态, 10 - 未确认 ; 20 - 已确认; 30 已消费
     */
    private Integer status;
}
