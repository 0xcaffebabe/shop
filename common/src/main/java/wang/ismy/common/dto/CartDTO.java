package wang.ismy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MY
 * @date 2019/10/1 15:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long skuId;

    private Integer num;
}
