package cn.wwq.rabbit.producer.broker;

import cn.wwq.rabbit.api.Message;

/**
 * 具体发送不同种类消息的接口，
 */
public interface RabbitBroker {

    void rapidSend(Message message);

    void confirmSend(Message message);

    void reliantSend(Message message);

    void sendMessages();

}
