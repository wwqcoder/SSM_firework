package cn.wwq.rabbit.api;

import cn.wwq.rabbit.api.exception.MessageRunTimeException;

import java.util.List;

public interface MessageProducer {

    /**
     * 消息的发送，附带SendCallback回调执行响应的业务逻辑处理
     * @param message
     * @param callback
     * @throws MessageRunTimeException
     */
    void send(Message message,SendCallback callback) throws MessageRunTimeException;

    /**
     * 消息的发送
     * @param message
     * @throws MessageRunTimeException
     */
    void send(Message message) throws MessageRunTimeException;

    /**
     * 消息的批量发送
     * @param messages
     * @throws MessageRunTimeException
     */
    void send(List<Message> messages) throws MessageRunTimeException;
}
