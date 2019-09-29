package wang.ismy.leyou.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import wang.ismy.leyou.user.api.UserApi;

/**
 * @author MY
 * @date 2019/9/29 17:07
 */
@FeignClient("user-service")
public interface UserClient extends UserApi { }
