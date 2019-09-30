package wang.ismy.leyou.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author MY
 * @date 2019/9/30 20:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @NotNull
    private Long addressId;

    @NotNull
    private Integer paymentType;

    @NotNull
    private List<CartDTO> carts;
}
