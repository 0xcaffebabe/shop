package wang.ismy.leyou.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import wang.ismy.api.GoodsApi;

/**
 * @author MY
 * @date 2019/10/1 14:43
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi { }
