package wang.ismy.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.Assertion;
import wang.ismy.common.exception.BusinessException;
import wang.ismy.common.vo.PageResult;
import wang.ismy.item.mapper.SkuMapper;
import wang.ismy.item.mapper.SpuDetailMapper;
import wang.ismy.item.mapper.SpuMapper;
import wang.ismy.item.mapper.StockMapper;
import wang.ismy.pojo.entity.*;
import wang.ismy.pojo.vo.SpuVO;

import javax.sound.midi.Soundbank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/9/21 13:45
 */
@Service
@AllArgsConstructor
public class GoodsService {

    private SpuMapper spuMapper;

    private SpuDetailMapper spuDetailMapper;

    private SkuMapper skuMapper;

    private StockMapper stockMapper;

    private CategoryService categoryService;

    private BrandService brandService;
    public PageResult<SpuVO> query(Integer page, Integer rows, Boolean saleable, String key) {
        PageHelper.startPage(page,rows);

        Example example = new Example(Spu.class);

        Example.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(key)){
            criteria
                    .andLike("title","%"+key+"%");
        }

        if (saleable != null){
            criteria.andEqualTo("saleable",saleable);
        }

        example.setOrderByClause("last_update_time DESC");
        List<Spu> spus = spuMapper.selectByExample(example);
        Assertion.assertNotEmpty(spus);

        PageInfo<Spu> pageInfo = new PageInfo<>(spus);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setItems(loadCategoryNameAndBrandName(spus));
        return pageResult;
    }

    private List<SpuVO> loadCategoryNameAndBrandName(List<Spu> list) {

        List<SpuVO> ret = new ArrayList<>();
        for (Spu spu : list) {
            // 分类

            Optional<String> name = categoryService.selectByIds(List.of(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream()
                    .map(Category::getName)
                    .reduce((s, s2) -> s + "/" + s2);

            // 品牌
            Brand brand = brandService.findById(spu.getBrandId());

            SpuVO spuVO = SpuVO.convert(spu);
            spuVO.setBrandName(brand.getName());
            spuVO.setCategoryName(name.orElse("其他"));

            ret.add(spuVO);
        }

        return ret;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveGoods(Spu spu) {
        // 新增spu
        spu.setCreateTime(LocalDateTime.now());
        spu.setLastUpdateTime(LocalDateTime.now());
        spu.setSaleable(true);
        spu.setValid(false);

        int count = spuMapper.insert(spu);
        if (count != 1){
            throw new BusinessException(ExceptionEnum.SERVER_ERROR);
        }
        // 新增spu_detail
        SpuDetail detail = spu.getSpuDetail();
        detail.setSpuId(spu.getId());
        count = spuDetailMapper.insert(detail);
        if (count != 1){
            throw new BusinessException(ExceptionEnum.SERVER_ERROR);
        }
        // 新增sku
        List<Stock> stockList = new ArrayList<>();
        for (Sku sku : spu.getSkus()) {
            sku.setCreateTime(LocalDateTime.now());
            sku.setLastUpdateTime(LocalDateTime.now());
            sku.setSpuId(spu.getId());
            count = skuMapper.insert(sku);
            if (count != 1){
                throw new BusinessException(ExceptionEnum.SERVER_ERROR);
            }

            Stock stock = new Stock();
            stock.setStock(sku.getStock());
            stock.setSkuId(sku.getId());
            stockList.add(stock);
        }
        // 批量新增
        stockMapper.insertList(stockList);
    }
}
