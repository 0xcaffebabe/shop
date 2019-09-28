package wang.ismy.leyou.sms;

import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import wang.ismy.leyou.sms.config.SMSConfig;
import wang.ismy.leyou.sms.util.SMSUtils;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@EnableConfigurationProperties(SMSConfig.class)
@Slf4j
public class SMSListener {

    private SMSUtils smsUtils;

    private SMSConfig config;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sms.verify.code.queue", durable = "true"),
            exchange = @Exchange(name = "shop.sms.exchange", type = ExchangeTypes.TOPIC),
            key = {"sms.verify.code"}
    ))
    public void listenVerifyCode(Map<String, String> msg) {
        if (CollectionUtils.isEmpty(msg)) {
            return;
        }
        msg = new HashMap<>(msg);
        String phone = msg.remove("phone");
        if (StringUtils.isEmpty(phone)) {
            return;
        }

        smsUtils.sendSms(phone, config.getSigName(), config.getTemplate(), new Gson().toJson(msg));


    }
}
