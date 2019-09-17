package wang.ismy.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wang.ismy.common.enums.ExceptionEnum;

/**
 * @author MY
 * @date 2019/9/17 19:36
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BusinessException extends RuntimeException {

    private ExceptionEnum exceptionEnum;

}
