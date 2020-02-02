package cn.wwq.rabbit.producer.test;

import cn.wwq.rabbit.producer.component.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private RabbitSender rabbitSender;

    @Test
    public void testSend() throws InterruptedException {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("a",1);
        properties.put("b",2);
        rabbitSender.send("hello rabbitmq!!!",properties);

        Thread.sleep(10000);

    }


}
