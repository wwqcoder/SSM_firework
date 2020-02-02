package cn.wwq.rabbit.api;

/**
 * 消息者监听消息
 */
public interface MessageListener {

    void onMessage(Message message);
}
