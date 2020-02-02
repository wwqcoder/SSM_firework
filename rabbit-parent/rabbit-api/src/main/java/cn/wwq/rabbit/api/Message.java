package cn.wwq.rabbit.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = 1656796000924988535L;

    /* 消息的唯一ID */
    private String messageId;
    /* 消息的主题 */
    private String topic;
    /* 消息的路由规则 */
    private String routingKey;
    /* 消息的附加属性 */
    private Map<String,Object> attributes = new HashMap<>();
    /* 延迟消息的参数配置*/
    private int delayMills;
    /* 消息类型 默认确认消息 */
    private String messageType = MessageType.CONFIRM;

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
    }
}
