package wang.ismy.item.controller;

import com.mysql.fabric.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.ismy.item.service.BrandService;
import wang.ismy.item.service.CategoryService;
import wang.ismy.pojo.entity.Category;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/18 13:09
 */
@RestController
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    private BrandService brandService;

    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryListByPid(@RequestParam("pid") Long pid) {
        return ResponseEntity.ok(categoryService.queryListByPid(pid));
    }

    @GetMapping("/bid/{bid}")
    public ResponseEntity<List<Category>> getCategoryByBrand(@PathVariable Long bid){
        return ResponseEntity.ok(categoryService.getCategoryByBrand(bid));
    }

    @GetMapping("list/ids")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(categoryService.queryCategoryByIds(ids));
    }

    @GetMapping("/list/name/ids")
    public ResponseEntity<List<String>> queryCategoryNameByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(categoryService.queryCategoryNameByIds(ids));
    }

    @GetMapping("all/level/{cid3}")
    public ResponseEntity<List<Category>> queryCategoryList(@PathVariable("cid3") Long cid3){
        return ResponseEntity.ok(categoryService.queryCategoryList(cid3));
    }
}
