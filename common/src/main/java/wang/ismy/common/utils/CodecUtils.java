package wang.ismy.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * @author MY
 * @date 2019/9/29 15:55
 */
public class CodecUtils {


    public static String generateSalt() {
        return NumberUtils.generateCode(8);
    }


    public static String md5Hex(String password, String salt) {

        return DigestUtils.md5DigestAsHex((DigestUtils.md5DigestAsHex(password.getBytes())+salt).getBytes());
    }
}
