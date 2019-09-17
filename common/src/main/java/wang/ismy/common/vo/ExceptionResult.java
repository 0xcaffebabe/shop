package wang.ismy.common.vo;

import lombok.Data;
import wang.ismy.common.enums.ExceptionEnum;

/**
 * @author MY
 * @date 2019/9/17 19:52
 */
@Data
public class ExceptionResult {

    private Integer status;

    private String message;

    private Long timestamp;

    public ExceptionResult(ExceptionEnum enm) {
        this.status = enm.getCode();
        this.message = enm.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

}
