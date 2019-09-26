package wang.ismy.leyou.page.client;

import org.springframework.cloud.openfeign.FeignClient;
import wang.ismy.api.BrandApi;

/**
 * @author MY
 * @date 2019/9/23 18:56
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
