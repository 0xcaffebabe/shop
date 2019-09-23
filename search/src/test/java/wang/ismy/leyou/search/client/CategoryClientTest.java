package wang.ismy.leyou.search.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.pojo.entity.Category;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryClientTest {

    @Autowired
    CategoryClient client;

    @Test
    public void query(){
        List<Category> categories = client.queryCategoryByIds(List.of(1L, 2L, 3L));
        assertEquals(3,categories.size());
    }
}