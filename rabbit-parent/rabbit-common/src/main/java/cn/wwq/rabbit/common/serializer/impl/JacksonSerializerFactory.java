package cn.wwq.rabbit.common.serializer.impl;

import cn.wwq.rabbit.api.Message;
import cn.wwq.rabbit.common.serializer.Serializer;
import cn.wwq.rabbit.common.serializer.SerializerFactory;

public class JacksonSerializerFactory implements SerializerFactory {

    public static final JacksonSerializerFactory INSTANCE = new JacksonSerializerFactory();

    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
