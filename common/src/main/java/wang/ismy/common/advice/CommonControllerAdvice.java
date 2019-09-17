package wang.ismy.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import wang.ismy.common.exception.BusinessException;
import wang.ismy.common.vo.ExceptionResult;

/**
 * @author MY
 * @date 2019/9/17 19:25
 */
@ControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResult> handleException(BusinessException e){
        return ResponseEntity.status(e.getExceptionEnum()
                .getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }
}
