package wang.ismy.leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import wang.ismy.common.utils.JsonUtils;
import wang.ismy.common.vo.PageResult;
import wang.ismy.leyou.search.client.BrandClient;
import wang.ismy.leyou.search.client.CategoryClient;
import wang.ismy.leyou.search.client.GoodsClient;
import wang.ismy.leyou.search.client.SpecificationClient;
import wang.ismy.leyou.search.pojo.Goods;
import wang.ismy.leyou.search.pojo.SearchRequest;
import wang.ismy.pojo.entity.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/9/23 19:14
 */
@Service
@AllArgsConstructor
public class SearchService {

    private CategoryClient categoryClient;

    private BrandClient brandClient;

    private GoodsClient goodsClient;

    private SpecificationClient specClient;

    public Goods buildGoods(Spu spu)  {

        Goods goods = new Goods();

        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setId(spu.getId());
        goods.setSubTitle(spu.getSubTitle());

        List<Sku> skuList = goodsClient.getSkuList(spu.getId());

        // 该目录下商品的规格信息

        goods.setAll(getGoodsAllName(spu));
        goods.setPrice(skuList.stream().map(Sku::getPrice).collect(Collectors.toList()));
        goods.setSkus(JsonUtils.toJSON(skuList));
        try {
            goods.setSpecs(processSpec(spu));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return goods;
    }

    private String getGoodsAllName(Spu spu) {
        String categoryName = categoryClient.queryCategoryByIds(List.of(spu.getCid1(),spu.getCid2(),spu.getCid3()))
                .stream()
                .map(Category::getName)
                .reduce((s1,s2)->s1+" "+s2).get();
        Brand brand = brandClient.queryByBrandId(spu.getBrandId());
        return categoryName+" "+brand.getName();

    }

    private Map<String,Object> processSpec(Spu spu) throws IOException {
        SpuDetail spuDetail = goodsClient.getSpuDetail(spu.getId());
        List<SpecParam> params = specClient.selectParam(null, spu.getCid3(), true);
        // 处理规格参数
        Map<String, Object> genericSpecs = new ObjectMapper().readValue(spuDetail.getGenericSpec(), new TypeReference<Map<String, Object>>() {
        });
        Map<String, Object> specialSpecs = new ObjectMapper().readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<String, Object>>() {
        });
        // 获取可搜索的规格参数
        Map<String, Object> searchSpec = new HashMap<>();

        // 过滤规格模板，把所有可搜索的信息保存到Map中
        Map<String, Object> specMap = new HashMap<>();
        params.forEach(p -> {
            if (p.getSearching()) {
                if (p.getGeneric()) {
                    String value = genericSpecs.get(p.getId().toString()).toString();
                    if(p.getNumeric()){
                        value = chooseSegment(value,p);
                    }
                    specMap.put(p.getName(), StringUtils.isBlank(value) ? "其它" : value);
                } else {
                    specMap.put(p.getName(), specialSpecs.get(p.getId().toString()));
                }
            }
        });
        return searchSpec;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    public PageResult<Goods> search(SearchRequest request) {
        Integer page = request.getPage();
        Integer size = request.getSize();


    }
}
