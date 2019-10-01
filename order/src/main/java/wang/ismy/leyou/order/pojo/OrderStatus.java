package wang.ismy.leyou.order.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2019/9/30 19:57
 */
@Table(name = "tb_order_status")
@Data
public class OrderStatus {
    @Id
    private Long orderId;

    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 付款时间
     */
    private LocalDateTime paymentTime;

    /**
     *  发货时间
     */
    private LocalDateTime consignTime;

    /**
     * 交易结束时间
     */
    private LocalDateTime endTime;

    /**
     * 交易关闭时间
     */
    private LocalDateTime closeTime;

    /**
     * 评价时间
     */
    private LocalDateTime commentTime;
}
