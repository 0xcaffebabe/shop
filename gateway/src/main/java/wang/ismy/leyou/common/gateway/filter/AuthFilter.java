package wang.ismy.leyou.common.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import wang.ismy.common.utils.CookieUtils;
import wang.ismy.leyou.auth.utils.JwtUtils;
import wang.ismy.leyou.common.gateway.config.FilterProperties;
import wang.ismy.leyou.common.gateway.config.JwtProperties;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MY
 * @date 2019/9/29 19:02
 */
@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
@AllArgsConstructor
public class AuthFilter extends ZuulFilter {

    private JwtProperties jwtProperties;

    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER -1;
    }

    @Override
    public boolean shouldFilter() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String path = request.getRequestURI();

        return !isAllowPath(path);
    }

    private boolean isAllowPath(String path) {
        for (String allowPath : filterProperties.getAllowPaths()) {
            if (path.startsWith(allowPath)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();



        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        try {
            JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            // 校验权限
        } catch (Exception e) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(403);
        }

        return null;
    }
}
