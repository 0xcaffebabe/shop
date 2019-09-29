package wang.ismy.leyou.auth.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.BusinessException;
import wang.ismy.common.utils.CookieUtils;
import wang.ismy.leyou.auth.config.JwtProperties;
import wang.ismy.leyou.auth.entity.UserInfo;
import wang.ismy.leyou.auth.service.AuthService;
import wang.ismy.leyou.auth.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MY
 * @date 2019/9/29 16:49
 */
@RestController
@EnableConfigurationProperties(JwtProperties.class)
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    private JwtProperties prop;

    @PostMapping("accredit")
    public ResponseEntity<Void> authentication(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response) {
        // 登录校验
        String token = authService.authentication(username, password);
        if (StringUtils.isBlank(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
        CookieUtils.setCookie(request, response, prop.getCookieName(),
                token, prop.getCookieMaxAge(), true);
        return ResponseEntity.ok().build();
    }

    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("SHOP_TOKEN") String token){
        if (StringUtils.isEmpty(token)){
            throw new BusinessException(ExceptionEnum.UNAUTHORIZED);
        }

        // 解析token
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            throw new BusinessException(ExceptionEnum.UNAUTHORIZED);
        }
    }
}
