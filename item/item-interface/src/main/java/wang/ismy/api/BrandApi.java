package wang.ismy.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wang.ismy.pojo.entity.Brand;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/23 18:53
 */
@RequestMapping("brand")
public interface BrandApi {
    @GetMapping("{id}")
    Brand queryByBrandId(@PathVariable("id") Long id);

    /**
     * 根据ID批量查询品牌数据
     * @param ids ID列表
     * @return 品牌列表
     * */
    @GetMapping("list")
    List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);
}
