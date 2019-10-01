package wang.ismy.item.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;
import wang.ismy.common.mapper.BaseMapper;
import wang.ismy.pojo.entity.Stock;

/**
 * @author MY
 * @date 2019/9/21 16:41
 * 这里需要注意的是InsertListMapper导的包要是insert下的，该接口支持的是自定义主键
 */
public interface StockMapper extends BaseMapper<Stock,Long> {

    /**
     * 减库存
     * @param id skuId
     * @param num 数量
     * @return 首受影响行数
     */
    @Update("UPDATE tb_stock SET stock = stock - #{num} WHERE sku_id = #{id} AND stock >= #{num}")
    int decrease(@Param("id")  Long id,@Param("num") Integer num);
}
