package wang.ismy.leyou.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MY
 * @date 2019/9/30 20:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long skuId;

    private Integer num;
}
