package wang.ismy.leyou.order.enums;

import lombok.Getter;

/**
 * @author MY
 * @date 2019/10/1 15:09
 */
@Getter
public enum OrderStatusEnum {

    UN_PAY(1,"未付款"),
    PAYED(2,"已付款"),
    UN_CONFIRM(3,"未确认"),
    SUCCESS(4,"交易成功"),
    DELIVERED(5,"已收货"),
    CLOSED(6,"已关闭"),
    ;
    private int code;

    private String msg;

    OrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
