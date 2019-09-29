package wang.ismy.leyou.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wang.ismy.leyou.user.pojo.User;

/**
 * @author MY
 * @date 2019/9/29 17:06
 */
public interface UserApi {

    @GetMapping("query")
    User queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );
}
