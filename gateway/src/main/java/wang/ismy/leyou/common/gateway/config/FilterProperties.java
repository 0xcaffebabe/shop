package wang.ismy.leyou.common.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/29 19:28
 */
@ConfigurationProperties("shop.filter")
@Data
public class FilterProperties {

    private List<String> allowPaths;
}
