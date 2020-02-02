package cn.wwq.rabbit.producer.broker;

import cn.wwq.rabbit.api.Message;
import cn.wwq.rabbit.api.MessageType;
import cn.wwq.rabbit.api.exception.MessageRunTimeException;
import cn.wwq.rabbit.common.convert.GenericMessageConverter;
import cn.wwq.rabbit.common.convert.RabbitMessageConvert;
import cn.wwq.rabbit.common.serializer.Serializer;
import cn.wwq.rabbit.common.serializer.impl.JacksonSerializerFactory;
import cn.wwq.rabbit.producer.service.MessageStoreService;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * RabbitTemplateContainer 池化封装
 * 好处：
 *  每一个topic 对应一个RabbitTemplate，
 *  1 提高发送的效率
 *  2 可以根据不同的需求制定化不同的RabbitTemplate
 *    比如： 每一个topic 都有自己的routingKey规则
 */
@Slf4j
@Component
public class RabbitTemplateContainer implements  RabbitTemplate.ConfirmCallback{

    private Map<String /* topic */, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

    private Splitter splitter = Splitter.on("#");

    private Serializer serializer = JacksonSerializerFactory.INSTANCE.create();
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private MessageStoreService messageStoreService;

    public RabbitTemplate getTemplate(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message);
        String topic = message.getTopic();
        RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
        if (null != rabbitTemplate){
            return rabbitTemplate;
        }
        log.info("#RabbitTemplateContainer.getTemplate# topic : {} is not exist!!!,create one ",topic);

        RabbitTemplate newRabbitTemplate = new RabbitTemplate(connectionFactory);
        newRabbitTemplate.setExchange(topic);
        newRabbitTemplate.setRoutingKey(message.getRoutingKey());
        newRabbitTemplate.setRetryTemplate(new RetryTemplate());
        // 对于message的序列化
        GenericMessageConverter gmc = new GenericMessageConverter(serializer);
        RabbitMessageConvert rmc = new RabbitMessageConvert(gmc);
        newRabbitTemplate.setMessageConverter(rmc);
        String messageType = message.getMessageType();
        if (!MessageType.RAPID.equals(messageType)){
            newRabbitTemplate.setConfirmCallback(this);
        }
        rabbitMap.putIfAbsent(topic,newRabbitTemplate);
        return rabbitMap.get(topic);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //具体的消息应答
        List<String> strings = splitter.splitToList(correlationData.getId());

        String messageId = strings.get(0);
        Long sendTime = Long.valueOf(strings.get(1));
        String messageType = strings.get(2);
        if (ack){
            // 	如果当前消息类型为reliant 我们就去数据库查找并进行更新
            if(MessageType.RELIANT.endsWith(messageType)) {
                this.messageStoreService.success(messageId);
            }
            log.info("send message id OK, confirm messageId : {},sendTime:{} ",messageId,sendTime);
        }else {
            log.error("send message id FAIL, confirm messageId : {},sendTime:{} ",messageId,sendTime);
        }

    }
}
