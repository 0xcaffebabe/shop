package wang.ismy.leyou.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sms")
@Data
public class SMSConfig {
    private String accessKeyId;
    private String accessKeySecret;
    private String sigName;
    private String template;
}
