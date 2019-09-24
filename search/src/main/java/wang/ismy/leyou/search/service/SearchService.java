package wang.ismy.leyou.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.action.search.SearchResponse;

import org.elasticsearch.index.query.*;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.BusinessException;
import wang.ismy.common.utils.JsonUtils;

import wang.ismy.common.vo.PageResult;
import wang.ismy.leyou.search.client.BrandClient;
import wang.ismy.leyou.search.client.CategoryClient;
import wang.ismy.leyou.search.client.GoodsClient;
import wang.ismy.leyou.search.client.SpecificationClient;
import wang.ismy.leyou.search.pojo.Goods;
import wang.ismy.leyou.search.pojo.SearchRequest;
import wang.ismy.leyou.search.pojo.SearchResult;
import wang.ismy.leyou.search.repoistory.GoodsRepository;
import wang.ismy.pojo.entity.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/9/23 19:14
 */
@Service
@AllArgsConstructor
@Slf4j
public class SearchService {

    private CategoryClient categoryClient;

    private BrandClient brandClient;

    private GoodsClient goodsClient;

    private SpecificationClient specClient;

    private GoodsRepository goodsRepository;

    private ElasticsearchTemplate elasticsearchTemplate;

    public Goods buildGoods(Spu spu) throws IOException {

        Goods goods = new Goods();

        // 查询商品分类名称
        List<String> names = categoryClient.queryCategoryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        // 查询sku
        List<Sku> skus = goodsClient.getSkuList(spu.getId());
        // 查询详情
        SpuDetail spuDetail = goodsClient.getSpuDetail(spu.getId());
        // 查询规格参数
        List<SpecParam> params = specClient.selectParam(null, spu.getCid3(), true);

        // 处理sku，仅封装id、价格、标题、图片，并获得价格集合
        List<Long> prices = new ArrayList<>();
        List<Map<String, Object>> skuList = new ArrayList<>();
        skus.forEach(sku -> {
            prices.add(sku.getPrice());
            Map<String, Object> skuMap = new HashMap<>();
            skuMap.put("id", sku.getId());
            skuMap.put("title", sku.getTitle());
            skuMap.put("price", sku.getPrice());
            skuMap.put("image", StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            skuList.add(skuMap);
        });

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
                    if (p.getNumeric()) {
                        value = chooseSegment(value, p);
                    }
                    specMap.put(p.getName(), StringUtils.isBlank(value) ? "其它" : value);
                } else {
                    specMap.put(p.getName(), specialSpecs.get(p.getId().toString()));
                }
            }
        });

        goods.setId(spu.getId());
        goods.setSubTitle(spu.getSubTitle());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setAll(spu.getTitle() + " " + StringUtils.join(names, " "));
        goods.setPrice(prices);
        goods.setSkus(new ObjectMapper().writeValueAsString(skuList));
        goods.setSpecs(specMap);
        return goods;
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
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + p.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + p.getUnit() + "以下";
                } else {
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    public PageResult<Goods> search(SearchRequest request) {

        if (StringUtils.isBlank(request.getKey())) {
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder basicQuery = buildBasicQueryWithFilter(request);
        searchWithPageAndSort(queryBuilder, request);

        // 聚合
        String categoryAggName = "category";
        String brandAggName = "brand";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        // 过滤
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", request.getKey()));

        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "subTitle", "skus"}, null));

        AggregatedPage<Goods> pageInfo = (AggregatedPage<Goods>) goodsRepository.search(queryBuilder.build());

        // 解析聚合结果

        Long total = pageInfo.getTotalElements();
        int totalPage = (total.intValue() + request.getSize() - 1) / request.getSize();
        // 3.2、商品分类的聚合结果
        List<Category> categories =
                getCategoryAggResult(pageInfo.getAggregation(categoryAggName));

        List<Map<String, Object>> specs = new ArrayList<>();
        if (categories.size() == 1) {
            // 只有当商品分类只有1个时，才去获取该商品分类下的规格参数
            specs = getSpec(categories.get(0).getId(), basicQuery);
        }

        // 3.3、品牌的聚合结果
        List<Brand> brands = getBrandAggResult(pageInfo.getAggregation(brandAggName));

        // 返回结果
        return new SearchResult(pageInfo.getTotalElements(), pageInfo.getTotalPages(), pageInfo.getContent(), categories, brands, specs);

    }

    private QueryBuilder buildBasicQueryWithFilter(SearchRequest request) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //基本查询条件
        queryBuilder.must(QueryBuilders.matchQuery("all",request.getKey()).operator(Operator.AND));
        //过滤条件构造器
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
        //整理过滤条件
        Map<String,String> filter = request.getFilter();
        for (Map.Entry<String,String> entry : filter.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String regex = "^(\\d+\\.?\\d*)-(\\d+\\.?\\d*)$";
            if (!"key".equals(key)) {
                if ("price".equals(key)){
                    if (!value.contains("元以上")) {
                        String[] nums = StringUtils.substringBefore(value, "元").split("-");
                        filterQueryBuilder.must(QueryBuilders.rangeQuery(key).gte(Double.valueOf(nums[0]) * 100).lt(Double.valueOf(nums[1]) * 100));
                    }else {
                        String num = StringUtils.substringBefore(value,"元以上");
                        filterQueryBuilder.must(QueryBuilders.rangeQuery(key).gte(Double.valueOf(num)*100));
                    }
                }else {
                    if (value.matches(regex)) {
                        Double[] nums = wang.ismy.common.utils.NumberUtils.searchNumber(value, regex);
                        //数值类型进行范围查询   lt:小于  gte:大于等于
                        filterQueryBuilder.must(QueryBuilders.rangeQuery("specs." + key).gte(nums[0]).lt(nums[1]));
                    } else {
                        //商品分类和品牌要特殊处理
                        if (key != "cid3" && key != "brandId") {
                            key = "specs." + key + ".keyword";
                        }
                        //字符串类型，进行term查询
                        filterQueryBuilder.must(QueryBuilders.termQuery(key, value));
                    }
                }
            } else {
                break;
            }
        }
        //添加过滤条件
        queryBuilder.filter(filterQueryBuilder);
        return queryBuilder;
    }

    private List<Brand> getBrandAggResult(Aggregation aggregation) {
        try {
            LongTerms brandAgg = (LongTerms) aggregation;
            List<Long> bids = new ArrayList<>();
            for (LongTerms.Bucket bucket : brandAgg.getBuckets()) {
                bids.add(bucket.getKeyAsNumber().longValue());
            }
            // 根据id查询品牌
            return this.brandClient.queryBrandByIds(bids);
        } catch (Exception e) {
            log.error("品牌聚合出现异常：", e);
            return null;
        }
    }

    private List<Category> getCategoryAggResult(Aggregation aggregation) {
        try {
            List<Category> categories = new ArrayList<>();
            LongTerms categoryAgg = (LongTerms) aggregation;
            List<Long> cids = new ArrayList<>();
            for (LongTerms.Bucket bucket : categoryAgg.getBuckets()) {
                cids.add(bucket.getKeyAsNumber().longValue());
            }
            // 根据id查询分类名称
            List<String> names = categoryClient.queryCategoryNameByIds(cids);

            for (int i = 0; i < names.size(); i++) {
                Category c = new Category();
                c.setId(cids.get(i));
                c.setName(names.get(i));
                categories.add(c);
            }
            return categories;
        } catch (Exception e) {
            log.error("分类聚合出现异常：", e);
            return null;
        }
    }

    private void searchWithPageAndSort(NativeSearchQueryBuilder queryBuilder, SearchRequest request) {
        // 准备分页参数
        int page = request.getPage();
        int size = request.getSize();

        // 1、分页
        queryBuilder.withPageable(PageRequest.of(page - 1, size));
        // 2、排序
        String sortBy = request.getSortBy();
        Boolean desc = request.getDescending();
        if (StringUtils.isNotBlank(sortBy)) {
            // 如果不为空，则进行排序
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }
    }

    private List<Map<String, Object>> getSpec(Long cid, QueryBuilder query) {
        try {
            // 不管是全局参数还是sku参数，只要是搜索参数，都根据分类id查询出来
            List<SpecParam> params = specClient.selectParam(null, cid, true);
            List<Map<String, Object>> specs = new ArrayList<>();

            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            queryBuilder.withQuery(query);

            // 聚合规格参数
            params.forEach(p -> {
                String key = p.getName();
                queryBuilder.addAggregation(AggregationBuilders.terms(key).field("specs." + key + ".keyword"));

            });

            // 查询
            Map<String, Aggregation> aggs = elasticsearchTemplate.query(queryBuilder.build(),
                    SearchResponse::getAggregations).asMap();

            // 解析聚合结果
            params.forEach(param -> {
                Map<String, Object> spec = new HashMap<>();
                String key = param.getName();
                spec.put("k", key);
                StringTerms terms = (StringTerms) aggs.get(key);
                spec.put("options", terms.getBuckets().stream().map(StringTerms.Bucket::getKeyAsString));
                specs.add(spec);
            });

            return specs;
        } catch (Exception e) {
            log.error("规格聚合出现异常：", e);
            return null;
        }

    }

}
