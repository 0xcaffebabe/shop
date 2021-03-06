package wang.ismy.leyou.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import wang.ismy.common.vo.PageResult;
import wang.ismy.pojo.entity.Brand;
import wang.ismy.pojo.entity.Category;

import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2019/9/24 17:17
 */
@Data
@AllArgsConstructor
public class SearchResult extends PageResult<Goods> {

    private List<Category> categories;

    private List<Brand> brands;

    private List<Map<String,Object>> specs;

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Category> categories, List<Brand> brands,List<Map<String,Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
