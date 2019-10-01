package wang.ismy.leyou.order.client;

import org.springframework.stereotype.Component;
import wang.ismy.leyou.order.dto.AddressDTO;

/**
 * @author MY
 * @date 2019/10/1 14:32
 */
@Component
public class AddressClient {

    public AddressDTO find(Long id){
        AddressDTO dto = new AddressDTO();
        dto.setId(id);
        dto.setAddress("翻斗大街102号");
        dto.setDistrict("浦东");
        dto.setName("蔡徐坤");
        dto.setPhone("17359563770");
        dto.setCity("上海");
        dto.setState("上海");
        dto.setZipCode("343213");
        dto.setIsDefault(true);
        return dto;
    }
}
