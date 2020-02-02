package cn.wwq.rabbit.producer.component;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     *  这里就是确认消息的回调监听接口，用于确认消息是否被broker所收到
     */

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        /**
         *
         * @param correlationData 作为一个唯一的标识
         * @param ack  broker 是否落盘成功
         * @param s  失败的一些异常信息
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            System.out.println("ack 结果： "+ ack + "correlationData" + correlationData);
        }
    };

    /**
     * 对外发送消息的方法
     * @param message  具体的消息
     * @param properties  附加属性
     */
    public void send(Object message, Map<String,Object> properties){

        MessageHeaders mhs = new MessageHeaders(properties);

        Message<?> msg = MessageBuilder.createMessage(message, mhs);

        rabbitTemplate.setConfirmCallback(confirmCallback);

        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        MessagePostProcessor mpp = new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                System.out.println("post to do : "+ message);
                return message;
            }
        };
        rabbitTemplate.convertAndSend("order-exchange",
                "order.abc", msg,mpp,cd);

    }
}
