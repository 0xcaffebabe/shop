package wang.ismy.leyou.search.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.common.vo.PageResult;
import wang.ismy.leyou.search.pojo.Goods;
import wang.ismy.leyou.search.pojo.SearchRequest;
import wang.ismy.leyou.search.service.SearchService;

/**
 * @author MY
 * @date 2019/9/23 22:09
 */
@RestController
@AllArgsConstructor
public class SearchController {

    private SearchService searchService;

    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok(searchService.search(searchRequest));
    }
}
