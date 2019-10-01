package wang.ismy.leyou.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author MY
 * @date 2019/10/1 14:16
 */
@ConfigurationProperties(prefix = "shop.worker")
@Data
public class IdWorkerProperties {
    private long workerId;// 当前机器id

    private long datacenterId;// 序列号
}
