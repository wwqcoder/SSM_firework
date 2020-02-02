package cn.wwq.rabbit.common.convert;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * 装饰者模式 - -
 */
public class RabbitMessageConvert implements MessageConverter {

    private GenericMessageConverter delegate;

    // final String defaultExpire = String.valueOf(24*60*60*1000);
    public RabbitMessageConvert(GenericMessageConverter genericMessageConverter) {
        Preconditions.checkNotNull(genericMessageConverter);
        this.delegate = genericMessageConverter;
    }

    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        cn.wwq.rabbit.api.Message message = (cn.wwq.rabbit.api.Message) o;
        messageProperties.setDelay(message.getDelayMills());
        return this.delegate.toMessage(o, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return (cn.wwq.rabbit.api.Message)this.delegate.fromMessage(message);
    }
}
