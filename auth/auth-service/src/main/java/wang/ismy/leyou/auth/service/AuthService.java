package wang.ismy.leyou.auth.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wang.ismy.common.utils.CookieUtils;
import wang.ismy.leyou.auth.client.UserClient;
import wang.ismy.leyou.auth.config.JwtProperties;
import wang.ismy.leyou.auth.entity.UserInfo;
import wang.ismy.leyou.auth.utils.JwtUtils;
import wang.ismy.leyou.user.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MY
 * @date 2019/9/29 16:50
 */
@Service
@AllArgsConstructor
public class AuthService {

    private UserClient userClient;

    private JwtProperties properties;

    public String authentication(String username, String password) {
        try {
            // 调用微服务，执行查询
            User user = userClient.queryUser(username, password);

            // 如果查询结果为null，则直接返回null
            if (user == null) {
                return null;
            }

            // 如果有查询结果，则生成token
            return JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()),
                    properties.getPrivateKey(), properties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
