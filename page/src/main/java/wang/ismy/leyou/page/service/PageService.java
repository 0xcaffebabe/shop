package wang.ismy.leyou.page.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import wang.ismy.common.utils.JsonUtils;
import wang.ismy.leyou.page.client.BrandClient;
import wang.ismy.leyou.page.client.CategoryClient;
import wang.ismy.leyou.page.client.GoodsClient;
import wang.ismy.leyou.page.client.SpecificationClient;
import wang.ismy.pojo.entity.*;

import java.awt.image.renderable.ContextualRenderedImageFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class PageService {


    private GoodsClient goodsClient;

    private BrandClient brandClient;

    private CategoryClient categoryClient;

    private SpecificationClient specificationClient;

    private TemplateEngine templateEngine;

    public Map<String, Object> loadModel(Long id) {

        try {
            // 查询spu
            Spu spu = this.goodsClient.querySpuById(id);

            // 查询spu详情
            SpuDetail spuDetail = this.goodsClient.getSpuDetail(id);

            // 查询sku
            List<Sku> skus = this.goodsClient.getSkuList(id);

            // 查询品牌
            List<Brand> brands = this.brandClient.queryBrandByIds(Arrays.asList(spu.getBrandId()));

            // 查询分类
            List<Category> categories = categoryClient.queryCategoryByIds(List.of(spu.getCid1(),spu.getCid2(),spu.getCid3()));

            // 查询组内参数
            List<SpecGroup> specGroups = this.specificationClient.querySpecsByCid(spu.getCid3());

            // 查询所有特有规格参数
            List<SpecParam> specParams = this.specificationClient.selectParam(null, spu.getCid3(), false);
            // 处理规格参数
            Map<Long, String> paramMap = new HashMap<>();
            specParams.forEach(param -> {
                paramMap.put(param.getId(), param.getName());
            });

            Map<String, Object> map = new HashMap<>();
            map.put("spu", spu);
            map.put("detail", spuDetail);
            map.put("skus", skus);
            map.put("brand", brands.get(0));
            map.put("categories", categories);
            map.put("groups", specGroups);
            map.put("params", paramMap);
            Map<String, String> someMap = JsonUtils.parseMap(spuDetail.getSpecialSpec(), String.class, String.class);
            map.put("specialParamName",someMap.keySet());
            map.put("specialParamValue",someMap.values());
            return map;
        } catch (Exception e) {
            log.error("加载商品数据出错,spuId:{}", id);
        }
        return null;
    }

    public void createHtml(Long spuId){
        Context context = new Context();
        context.setVariables(loadModel(spuId));
        File dest = new File("./"+spuId+".html");
        try (PrintWriter writer = new PrintWriter(dest,"utf8")){
            writer.print(templateEngine.process("item",context));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            log.error("静态化失败",e);
        }
    }
}
