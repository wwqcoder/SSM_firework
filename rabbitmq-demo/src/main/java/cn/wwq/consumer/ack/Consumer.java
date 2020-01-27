package cn.wwq.consumer.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer {

	public static void main(String[] args) throws Exception {
		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("172.16.13.131");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		//2 获取C	onnection
		Connection connection = connectionFactory.newConnection();
		//3 通过Connection创建一个新的Channel
		Channel channel = connection.createChannel();
		String exchangeName = "test_confirm_exchange";
		String exchangeType = "topic";
		String routingKey = "confirm.#";
		String queueName = "test_confirm_queue";
		//4 声明交换机和队列 然后进行绑定设置, 最后制定路由Key
		channel.exchangeDeclare(exchangeName, exchangeType, true);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		//5 创建消费者 
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, queueingConsumer);
		while(true){
			QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.err.println("消费端: " + msg);
		}
	}
}