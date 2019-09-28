package wang.ismy.leyou.sms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SMSListenerTest {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Test
    public void test() throws InterruptedException {

        amqpTemplate.convertAndSend("shop.sms.exchange","sms.verify.code", Map.of(
                "phone","17359563770",
                "code","12345"
        ));

        Thread.sleep(10000);

    }
}