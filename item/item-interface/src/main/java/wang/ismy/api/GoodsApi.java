package wang.ismy.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.ismy.common.vo.PageResult;
import wang.ismy.pojo.entity.Sku;
import wang.ismy.pojo.entity.Spu;
import wang.ismy.pojo.entity.SpuDetail;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/23 18:49
 */
public interface GoodsApi {

    @GetMapping("/spu/page")
    PageResult<Spu> query(@RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                          @RequestParam(value = "salable", required = false) Boolean saleable,
                          @RequestParam(value = "key", required = false) String key);

    @GetMapping("/spu/detail/{id}")
    SpuDetail getSpuDetail(@PathVariable("id") Long id);

    @GetMapping("/sku/list")
    List<Sku> getSkuList(@RequestParam("id") Long spuId);

    /**
     * 添加商品数据
     * @param spu 商品实体
     * */
    @PostMapping("/goods")
    void saveGoods(@RequestBody Spu spu);

    @PutMapping("/goods")
    void updateGoogds(@RequestBody Spu spu);

    /**
     * 根据spu的id查询spu
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    Spu querySpuById(@PathVariable("id") Long id);
}
