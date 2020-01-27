package cn.wwq.producer.returnack;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Return返回消息
 *
 * 对于return消息，
 * 简而言之：“Return Listener 用于处理一些不可路由的消息”。
 * 复杂言之：我们的消息生产者，通过指定一个Exchange 和 Routingkey，把消息送达到某一个队列中去，
 * 然后我们的消费者监听队列，进行消费处理操作，ü但是在某些情况下，如果我们在发送消息的时候，
 * 当前的exchange不存在或者指定的路由key路由不到，这个时候如果我们需要监听这种不可达的消息，
 * 就要使用Return Listener !
 *
 * 在基础API中有一个关键的配置项：
 *
 * Mandatory：如果为true ，则监听器会接收到路由不可达的消息，然后进行后续处理，
 * 如果为false，那么broker端自动删除该消息！
 *
 */
public class Producer {

	public static void main(String[] args) throws Exception {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("172.16.13.131");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = "test_return_exchange";
		String routingKey = "return.save";
		String routingKeyError = "abc.save";
		
		String msg = "Hello RabbitMQ Return Message";
		
        // 这个监听器就是监听return消息的，用于处理不可路由的情况
		channel.addReturnListener(new ReturnListener() {
			@Override
			public void handleReturn(int replyCode, String replyText, String exchange,
									 String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
				// 这是broker把消息原路返回后提供的一些响应参数，供生产端去做后续的处理
				System.err.println("---------handle  return----------");
				System.err.println("replyCode: " + replyCode);
				System.err.println("replyText: " + replyText);
				System.err.println("exchange: " + exchange);
				System.err.println("routingKey: " + routingKey);
				System.err.println("properties: " + properties);
				System.err.println("body: " + new String(body));
			}
		});
		channel.basicPublish(exchange, routingKeyError, true, null, msg.getBytes());
		//channel.basicPublish(exchange, routingKeyError, true, null, msg.getBytes());
	}
}