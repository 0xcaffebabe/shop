package wang.ismy.leyou.search.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wang.ismy.api.CategoryApi;
import wang.ismy.pojo.entity.Category;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/23 18:36
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi { }
