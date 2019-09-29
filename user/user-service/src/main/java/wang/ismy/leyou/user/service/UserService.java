package wang.ismy.leyou.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.KeyBoundCursor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.BusinessException;
import wang.ismy.common.utils.CodecUtils;
import wang.ismy.common.utils.NumberUtils;
import wang.ismy.leyou.user.mapper.UserMapper;
import wang.ismy.leyou.user.pojo.User;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author MY
 * @date 2019/9/29 14:35
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserMapper userMapper;

    private AmqpTemplate amqpTemplate;

    private StringRedisTemplate redisTemplate;

    static final String PREFIX = "user:code:phone:";

    public Boolean checkData(String data, Integer type) {
        User user = new User();
        if (type == 1) {
            // 根据用户名查询
            user.setUsername(data);
        } else if (type == 2) {
            // 根据手机号查询
            user.setPhone(data);
        } else {
            throw new BusinessException(ExceptionEnum.INVALID_DATA);
        }
        return userMapper.selectCount(user) == 0;
    }

    public void sendCode(String phone) {
        String code = NumberUtils.generateCode(6);
        String key = PREFIX + phone;
        amqpTemplate.convertAndSend("shop.sms.exchange", "sms.verify.code", Map.of(
                "phone", phone,
                "code", code
        ));

        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
    }


    public Boolean register(User user, String code) {
        String key = PREFIX + user.getPhone();
        // 从redis取出验证码
        String codeCache = this.redisTemplate.opsForValue().get(key);
        // 检查验证码是否正确
        if (!code.equals(codeCache)) {
            // 不正确，返回
            return false;
        }
        user.setId(null);
        user.setCreated(LocalDate.now());
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        // 对密码进行加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        // 写入数据库
        boolean boo = this.userMapper.insertSelective(user) == 1;

        // 如果注册成功，删除redis中的code
        if (boo) {
            try {
                this.redisTemplate.delete(key);
            } catch (Exception e) {
                log.error("删除缓存验证码失败，code：{}", code, e);
            }
        }
        return boo;
    }

    public User queryUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        User ret = userMapper.selectOne(user);

        String enPassword = CodecUtils.md5Hex(password, ret.getSalt());
        if (!ret.getPassword().equals(enPassword)) {
            return null;
        }

        return ret;
    }
}
