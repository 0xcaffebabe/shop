package wang.ismy.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wang.ismy.pojo.entity.Category;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/23 18:52
 */
@RequestMapping("category")
public interface CategoryApi {

    @GetMapping("list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
