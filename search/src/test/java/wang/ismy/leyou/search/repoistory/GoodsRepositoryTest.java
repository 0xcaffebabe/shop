package wang.ismy.leyou.search.repoistory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import wang.ismy.common.vo.PageResult;
import wang.ismy.leyou.search.client.GoodsClient;
import wang.ismy.leyou.search.pojo.Goods;
import wang.ismy.leyou.search.service.SearchService;
import wang.ismy.pojo.entity.Spu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepositoryTest {

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    ElasticsearchTemplate template;

    @Autowired
    GoodsClient goodsClient;

    @Autowired
    SearchService searchService;

    @Test
    public void testCreateIndex() {
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);
    }

    @Test
    public void loadData() throws IOException {
        int page = 1;
        int rows = 200;
        int size = 0;
        do {
            // 批量查询spu
            List<Spu> items = goodsClient.query(page, rows, true, null).getItems();
            if (CollectionUtils.isEmpty(items)) {
                break;
            }
            size = items.size();
            // 构建goods

            List<Goods> goodsList = new ArrayList<>();
            for (Spu item : items) {
                goodsList.add(searchService.buildGoods(item));
            }

            // 存储索引库
            goodsRepository.saveAll(goodsList);
            page++;
        } while (size == 100);
    }
}