package wang.ismy.item.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.ismy.common.vo.PageResult;
import wang.ismy.item.service.BrandService;
import wang.ismy.pojo.Brand;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/18 19:31
 */
@RestController
@RequestMapping("brand")
public class BranController {

    private BrandService brandService;

    public BranController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> pageQuery(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                       @RequestParam(value = "sortBy", required = false) String sortBy,
                                                       @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
                                                       @RequestParam(value = "key", required = false) String key) {

        return ResponseEntity.ok(brandService.pageQuery(page,rows,sortBy,desc,key));
    }



    @PostMapping
    public ResponseEntity<Void> save(Brand brand,@RequestParam("cids") List<Long> cids){

        brandService.save(brand,cids);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(Brand brand,@RequestParam("cids") List<Long> cids){
        brandService.update(brand,cids);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{bid}")
    public ResponseEntity<Void> delete(@PathVariable Long bid){
        brandService.delete(bid);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
