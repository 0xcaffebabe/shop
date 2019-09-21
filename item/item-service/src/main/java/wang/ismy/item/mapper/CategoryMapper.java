package wang.ismy.item.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;
import wang.ismy.pojo.entity.Category;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/18 13:08
 */
public interface CategoryMapper extends Mapper<Category> , IdListMapper<Category,Long> {

    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid})")
    List<Category> getCategoryByBrand(Long bid);
}
