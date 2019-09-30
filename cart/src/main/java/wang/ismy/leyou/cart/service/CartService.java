package wang.ismy.leyou.cart.service;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.Assertion;
import wang.ismy.common.exception.BusinessException;
import wang.ismy.common.utils.JsonUtils;
import wang.ismy.leyou.auth.entity.UserInfo;
import wang.ismy.leyou.cart.interceptor.UserInterceptor;
import wang.ismy.leyou.cart.pojo.Cart;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/9/30 13:11
 */
@Service
@AllArgsConstructor
public class CartService {

    private StringRedisTemplate redisTemplate;

    private static final String PREFIX = "cart:user:id:";

    public void addCart(Cart cart) {
        // 获取登录用户
        UserInfo userInfo = UserInterceptor.getUserInfo();
        String key = PREFIX + userInfo.getId();

        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(key);

        // 判断当前用户购物车是否有内容
        if (operations.hasKey(cart.getSkuId().toString())) {
            Cart cacheCart = JsonUtils.parse(operations.get(cart.getSkuId().toString()).toString(), cart.getClass());
            cacheCart.setNum(cacheCart.getNum() + cart.getNum());
            operations.put(cart.getSkuId().toString(), JsonUtils.serialize(cacheCart));
        } else {
            operations.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
        }
    }

    public List<Cart> findAll() {
        UserInfo userInfo = UserInterceptor.getUserInfo();
        String key = PREFIX + userInfo.getId();

        var operations = redisTemplate.boundHashOps(key);

        List<Object> values = operations.values();
        Assertion.assertNotEmpty(values);

        return values.stream().map(s -> JsonUtils.parse(s.toString(), Cart.class)).collect(Collectors.toList());
    }

    public void modifyNum(Long id, Integer num) {
        UserInfo userInfo = UserInterceptor.getUserInfo();
        String key = PREFIX + userInfo.getId();

        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(key);

        if (!operations.hasKey(id.toString())) {
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }

        Cart cacheCart = JsonUtils.parse(operations.get(id.toString()).toString(), Cart.class);
        cacheCart.setNum(num);
        operations.put(id.toString(),JsonUtils.serialize(cacheCart));
    }

    public void delete(String skuId) {
        UserInfo userInfo = UserInterceptor.getUserInfo();
        String key = PREFIX + userInfo.getId();

        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(key);

        operations.delete(skuId);
    }
}
