package wang.ismy.leyou.order.dto;

import lombok.Data;

/**
 * @author MY
 * @date 2019/10/1 14:32
 */
@Data
public class AddressDTO {

    private Long id;

    private String address;

    private String name;

    private String city;

    private String district;

    private String state;

    private String phone;

    private String zipCode;

    private Boolean isDefault;


}
