package wang.ismy.item.service;

import org.springframework.stereotype.Service;
import wang.ismy.pojo.Item;

/**
 * @author MY
 * @date 2019/9/17 19:00
 */
@Service
public class ItemService {

    public Item save(Item item){
        return new Item();
    }
}
