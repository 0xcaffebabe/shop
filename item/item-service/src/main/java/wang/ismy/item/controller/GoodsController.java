package wang.ismy.item.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.ismy.common.vo.PageResult;
import wang.ismy.item.service.GoodsService;
import wang.ismy.pojo.entity.Sku;
import wang.ismy.pojo.entity.Spu;
import wang.ismy.pojo.entity.SpuDetail;
import wang.ismy.pojo.vo.SpuVO;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/21 13:46
 */
@RestController
@AllArgsConstructor
public class GoodsController {

    private GoodsService goodsService;

    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> query(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                   @RequestParam(value = "salable", required = false) Boolean saleable,
                                                   @RequestParam(value = "key", required = false) String key){
        return ResponseEntity.ok(goodsService.query(page,rows,saleable,key));
    }

    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> getSpuDetail(@PathVariable Long id){
        return ResponseEntity.ok(goodsService.getSpuDetail(id));
    }

    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> getSkuList(@RequestParam("id") Long spuId){
        return ResponseEntity.ok(goodsService.getSkuList(spuId));
    }

    /**
     * 添加商品数据
     * @param spu 商品实体
     * */
    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu){
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/goods")
    public ResponseEntity<Void> updateGoogds(@RequestBody Spu spu){
        goodsService.update(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
