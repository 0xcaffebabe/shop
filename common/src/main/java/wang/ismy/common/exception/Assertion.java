package wang.ismy.common.exception;

import org.springframework.util.CollectionUtils;
import wang.ismy.common.enums.ExceptionEnum;

import java.util.Collection;

/**
 * @author MY
 * @date 2019/9/21 14:26
 */
public class Assertion {

    public static void assertNotEmpty(Collection collection){
        if (CollectionUtils.isEmpty(collection)){
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }
    }

    public static void assertNotNull(Object obj){
        if (obj == null){
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }
    }
}
