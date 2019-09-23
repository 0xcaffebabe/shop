package wang.ismy.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.ismy.pojo.entity.Brand;

/**
 * @author MY
 * @date 2019/9/23 18:53
 */
@RequestMapping("brand")
public interface BrandApi {
    @GetMapping("{id}")
    Brand queryByBrandId(@PathVariable("id") Long id);
}
