package cn.wwq.consumer.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {

	public static void main(String[] args) throws Exception {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("172.16.13.131");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();

		String exchangeName = "test_qos_exchange";
		String queueName = "test_qos_queue";
		String routingKey = "qos.#";
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		//1 限流方式  第一件事就是 autoAck设置为 false
		/**
		 * RabbitMQ提供了一种qos（服务质量保证）功能，即在非自动确认消息的前提下，
		 * 如果一定数目的消息（通过基于consume或者channel设置Qos的值）未被确认前，不进行消费新的消息。
		 *
		 * void BasicQos(uint prefetchSize, ushort prefetchCount, bool global);
		 * 参数解释：
		 *
		 * prefetchSize： 批量拉取消息数量
		 * prefetchCount：会告诉RabbitMQ不要同时给一个消费者推送多于N个消息，
		 *即一旦有N个消息还没有ack，则该consumer将block掉，直到有消息ack
		 * global：true或false，是否将上面设置应用于channel
		 * （ü简单点说，就是上面限制是channel级别的还是consumer级别）
		 */
		channel.basicQos(0, 4, false);
		channel.basicConsume(queueName, false, new MyConsumer(channel));
	}
}