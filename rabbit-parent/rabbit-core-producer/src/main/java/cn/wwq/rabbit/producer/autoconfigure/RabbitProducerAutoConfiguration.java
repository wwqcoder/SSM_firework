package cn.wwq.rabbit.producer.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配
 */
@Configuration
@ComponentScan({"cn.wwq.rabbit.producer.*"})
public class RabbitProducerAutoConfiguration {
}
