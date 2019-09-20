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
    NOT_FOUND(404,"查询不到相关内容"),
    UPLOAD_FAIL(500,"上传文件失败"),
    INVALID_FILE_TYPE(400,"无效的文件类型"),
    INVALID_DATA(400,"无效的数据")
    ;

     ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;
}
