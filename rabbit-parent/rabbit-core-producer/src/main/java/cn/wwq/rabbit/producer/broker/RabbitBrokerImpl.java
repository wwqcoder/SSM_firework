package cn.wwq.rabbit.producer.broker;

import cn.wwq.rabbit.api.Message;
import cn.wwq.rabbit.api.MessageType;
import cn.wwq.rabbit.producer.constant.BrokerMessageConst;
import cn.wwq.rabbit.producer.constant.BrokerMessageStatus;
import cn.wwq.rabbit.producer.entity.BrokerMessage;
import cn.wwq.rabbit.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {

    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;

    @Autowired
    private MessageStoreService messageStoreService;

    /**
     *  迅速发消息
     * @param message
     */
    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    /**
     * 发送消息的核心方法，使用异步线程池进行发送消息
     * @param message
     */
    private void sendKernel(Message message) {

        AsyncBaseQueue.submit((Runnable) () ->{
            CorrelationData correlationData = new CorrelationData(
                    String.format("%s#%s#%s",message.getMessageId(),
                            System.currentTimeMillis(),
                            message.getMessageType()));
            String routingKey = message.getRoutingKey();
            String topic = message.getTopic();

            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);

            rabbitTemplate.convertAndSend(topic,routingKey,message,correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq ,messageId : {} ",message.getMessageId());

        });
    }

    /**
     * 确认消息
     * @param message
     */
    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    /**
     * 可靠性投递消息
     * @param message
     */
    @Override
    public void reliantSend(Message message) {
        message.setMessageType(MessageType.RELIANT);
        BrokerMessage bm = messageStoreService.selectByMessageId(message.getMessageId());
        if (null == null) {
            //1 数据库的消息发送日志记录
            Date now = new Date();
            BrokerMessage brokerMessage = new BrokerMessage();
            brokerMessage.setMessageId(message.getMessageId());
            brokerMessage.setStatus(BrokerMessageStatus.SENDING.getCode());
            // tryCount 在最开始的时候不需要设置
            brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
            brokerMessage.setCreateTime(now);
            brokerMessage.setUpdateTime(now);
            brokerMessage.setMessage(message);
            messageStoreService.insert(brokerMessage);
        }
        //2 执行真正的发送消息
        sendKernel(message);
    }

    @Override
    public void sendMessages() {
        List<Message> messages = MessageHolder.clear();
        messages.forEach(message -> {
            MessageHolderAyncQueue.submit((Runnable) () ->{
                CorrelationData correlationData = new CorrelationData(
                        String.format("%s#%s#%s",message.getMessageId(),
                                System.currentTimeMillis(),
                                message.getMessageType()));
                String routingKey = message.getRoutingKey();
                String topic = message.getTopic();

                RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);

                rabbitTemplate.convertAndSend(topic,routingKey,message,correlationData);
                log.info("#RabbitBrokerImpl.sendMessages# send to rabbitmq ,messageId : {} ",message.getMessageId());

            });
        });
    }
}
