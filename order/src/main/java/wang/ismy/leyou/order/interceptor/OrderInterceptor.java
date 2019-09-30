package wang.ismy.leyou.order.interceptor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.HandlerInterceptor;
import wang.ismy.common.utils.CookieUtils;
import wang.ismy.leyou.auth.entity.UserInfo;
import wang.ismy.leyou.auth.utils.JwtUtils;
import wang.ismy.leyou.order.config.JwtProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MY
 * @date 2019/9/30 19:25
 */
@AllArgsConstructor
@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
public class OrderInterceptor implements HandlerInterceptor {

    private JwtProperties prop;

    private static final ThreadLocal<UserInfo> TL = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());

        try {
            UserInfo info = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            TL.set(info);
            return true;
        }catch (Exception e){
            log.error("订单解析用户失败",e);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TL.remove();
    }

    public static UserInfo getUserInfo(){
        return TL.get();
    }
}

