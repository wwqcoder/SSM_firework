package cn.wwq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Topic Exchange：所有发送到Topic Exchange的消息被转发到所有关心RouteKey中指定Topic的Queue上，
 * Exchange 将RouteKey 和某Topic 进行模糊匹配,	此时队列需要绑定一个Topic。注意点：可以使用通配符进行模糊匹配。
 * 符号 “#” 匹配一个或多个词，例如：“log.#” 能够匹配到 “log.info.oa”；符号 “” 匹配不多不少一个词，
 * 例如：“log.*” 只会匹配到 “log.error”；
 */
public class Producer4TopicExchange {

	public static void main(String[] args) throws Exception {
		
		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("172.16.13.131");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		//2 创建Connection
		Connection connection = connectionFactory.newConnection();
		//3 创建Channel
		Channel channel = connection.createChannel();
		//4 声明
		String exchangeName = "test_topic_exchange";
		String routingKey1 = "user.save";
		String routingKey2 = "user.update";
		String routingKey3 = "user.delete.abc";
		//5 发送
		String msg = "Hello World RabbitMQ 4 Topic Exchange Message ...";
		channel.basicPublish(exchangeName, routingKey1 , null , msg.getBytes()); 
		channel.basicPublish(exchangeName, routingKey2 , null , msg.getBytes()); 	
		channel.basicPublish(exchangeName, routingKey3 , null , msg.getBytes()); 
		channel.close();  
        connection.close();  
	}
}