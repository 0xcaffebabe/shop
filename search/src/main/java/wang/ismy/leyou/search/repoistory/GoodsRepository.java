package wang.ismy.leyou.search.repoistory;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import wang.ismy.leyou.search.pojo.Goods;

/**
 * @author MY
 * @date 2019/9/23 19:10
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> { }
