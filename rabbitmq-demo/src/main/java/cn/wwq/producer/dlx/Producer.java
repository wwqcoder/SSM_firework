package cn.wwq.producer.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * TTL消息
 *
 * TTL是 Time To Live的缩写, 也就是生存时间。RabbitMQ支持消息的过期时间，
 * 在消息发送时可以进行指定；RabbitMQ
 * 支持队列的过期时间，从消息入队列开始计算，只要超过了队列的超时时间配置，那么消息会自动的清除！
 *
 * 死信队列
 * 死信队列：DLX, Dead-Letter-Exchange ，利用DLX,
 * 当消息在一个队列中变成死信（dead message）之后，它能被重新publish到另一个Exchange，
 * 这个Exchange就是DLX。
 *
 * 消息变成死信有一下几种情况：
 *
 * 消息被拒绝（basic.reject/ basic.nack）并且requeue=false
 * 消息TTL过期
 * 队列达到最大长度
 *
 * DLX也是一个正常的Exchange，和一般的Exchange没有区别，它能在任何的队列上被指定，
 * 实际上就是设置某个队列的属性。
 * 当这个队列中有死信时RabbitMQ就会自动将这个消息重新发布到设置的Exchange上去，
 * 进而被路由到另一个队列。
 * 可以监听这个队列中消息做相应的处理，这个特性可以弥补RabbitMQ 3.0以前支持的immediate参数的功能。
 * 这里给出死信队列的示例代码：
 */
public class Producer {

	public static void main(String[] args) throws Exception {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("172.16.13.131");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = "test_dlx_exchange";
		String routingKey = "dlx.save";
		
		String msg = "Hello RabbitMQ DLX Message";
		
		/**
		 * only when expired messages reach the head of a queue will they actually 				   be discarded (or dead-lettered).
		   只有当过期的消息到了队列的顶端（队首），才会被真正的丢弃或者进入死信队列.
		 * 
		 */
		for(int i = 1; i < 3; i++){
			Map<String, Object> headers = new HashMap<String, Object>();
			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					.expiration("" + 5000 * i)
					.build();
			channel.basicPublish(exchange, routingKey, true, properties,
                                 (msg + i).getBytes());
		}
		channel.close();
		connection.close();
	}
}