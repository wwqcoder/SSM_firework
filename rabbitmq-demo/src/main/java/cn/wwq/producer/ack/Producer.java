package cn.wwq.producer.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 *
 * Confirm确认消息
 *
 * 消息的确认，是指生产者投递消息后，如果Broker收到消息，则会给我们生产者一个应答。
 * 生产者进行接收应答，用来确定这条消息是否正常的发送到Broker，
 * 这种方式也是消息的可靠性投递的核心保障
 *
 * 第一步：在channel上开启确认模式：channel.confirmSelect()
 * 第二步：在channel上添加监听：addConfirmListener，监听成功和失败的返回结果，
 * 根据具体的结果对消息进行重新发送、或记录日志等后续处理！
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        //1 创建一个ConnectionFactory, 并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("172.16.13.131");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过connection创建一个Channel
        Channel channel = connection.createChannel();
        //4 指定我们的消息投递模式: 消息的确认模式
        channel.confirmSelect();
        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.save";
        //5 发送一条消息
        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ Send confirm message!";
            channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());
        }
        //6 添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            public void handleAck(long delivery, boolean multiple) throws IOException {
                System.err.println("-------ack!-----------");
            }

            public void handleNack(long delivery, boolean multiple) throws IOException {
                System.err.println("-------no ack!-----------");
            }
        });
    }
}
