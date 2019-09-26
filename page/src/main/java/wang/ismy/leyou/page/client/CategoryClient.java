package wang.ismy.leyou.page.client;

import org.springframework.cloud.openfeign.FeignClient;
import wang.ismy.api.CategoryApi;

/**
 * @author MY
 * @date 2019/9/23 18:36
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi { }
