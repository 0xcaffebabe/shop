package wang.ismy.leyou.order.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wang.ismy.common.utils.IdWorker;

/**
 * @author MY
 * @date 2019/10/1 14:17
 */
@EnableConfigurationProperties(IdWorkerProperties.class)
@Configuration
public class IdWorkerConfig {

    @Bean
    public IdWorker idWorker(IdWorkerProperties properties){
        return new IdWorker(properties.getWorkerId(),properties.getDatacenterId());
    }
}
