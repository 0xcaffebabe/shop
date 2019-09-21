package wang.ismy.item.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.ismy.common.vo.PageResult;
import wang.ismy.item.service.GoodsService;
import wang.ismy.pojo.entity.Sku;
import wang.ismy.pojo.entity.Spu;
import wang.ismy.pojo.vo.SpuVO;

/**
 * @author MY
 * @date 2019/9/21 13:46
 */
@RestController
@AllArgsConstructor
public class GoodsController {

    private GoodsService goodsService;

    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SpuVO>> query(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                   @RequestParam(value = "salable", required = false) Boolean saleable,
                                                   @RequestParam(value = "key", required = false) String key){
        return ResponseEntity.ok(goodsService.query(page,rows,saleable,key));
    }

    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu){
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
