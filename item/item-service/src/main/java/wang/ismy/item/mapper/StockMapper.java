package wang.ismy.item.mapper;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;
import wang.ismy.common.mapper.BaseMapper;
import wang.ismy.pojo.entity.Stock;

/**
 * @author MY
 * @date 2019/9/21 16:41
 * 这里需要注意的是InsertListMapper导的包要是insert下的，该接口支持的是自定义主键
 */
public interface StockMapper extends BaseMapper<Stock,Long> { }
