package wang.ismy.common.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;


/**
 * @author MY
 * @date 2019/9/21 16:57
 */
@RegisterMapper
public interface BaseMapper<T,K> extends Mapper<T>, IdListMapper<T,K>, InsertListMapper<T> { }
