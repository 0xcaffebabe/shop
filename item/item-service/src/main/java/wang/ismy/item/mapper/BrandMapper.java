package wang.ismy.item.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import wang.ismy.common.mapper.BaseMapper;
import wang.ismy.pojo.entity.Brand;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/18 19:32
 */

public interface BrandMapper extends BaseMapper<Brand,Long> {

    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid,@Param("bid") Long bid);

    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
    int deleteByBrand(Long bid);

    @Select("SELECT b.id,b.name FROM tb_brand b INNER JOIN tb_category_brand cb ON b.id=cb.brand_id WHERE cb.category_id = #{cid} ")
    List<Brand> selectByCategory(Long cid);
}
