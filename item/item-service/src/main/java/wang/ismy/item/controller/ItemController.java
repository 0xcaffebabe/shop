package wang.ismy.item.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.BusinessException;

/**
 * @author MY
 * @date 2019/9/17 19:12
 */
@RestController
public class ItemController {

    @RequestMapping("/item")
    public String item(){
        throw new BusinessException(ExceptionEnum.SERVER_ERROR);
    }
}
