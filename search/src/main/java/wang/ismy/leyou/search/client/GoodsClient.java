package wang.ismy.leyou.search.client;

import org.springframework.cloud.openfeign.FeignClient;
import wang.ismy.api.GoodsApi;

/**
 * @author MY
 * @date 2019/9/23 18:42
 */

@FeignClient("item-service")
public interface GoodsClient extends GoodsApi { }
