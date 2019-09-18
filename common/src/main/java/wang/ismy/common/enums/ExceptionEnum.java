package wang.ismy.common.enums;

import lombok.Getter;

/**
 * @author MY
 * @date 2019/9/17 19:37
 */
@Getter
public enum  ExceptionEnum {

    /*
    * 服务器内部错误
    * */
    SERVER_ERROR(500,"服务器错误"),
    NOT_FOUND(404,"查询不到相关内容")
    ;

     ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;
}
