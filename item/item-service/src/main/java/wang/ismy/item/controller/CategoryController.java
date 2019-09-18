package wang.ismy.item.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.item.service.CategoryService;
import wang.ismy.pojo.Category;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/18 13:09
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryListByPid(@RequestParam("pid") Long pid) {
        return ResponseEntity.ok(categoryService.queryListByPid(pid));
    }
}
