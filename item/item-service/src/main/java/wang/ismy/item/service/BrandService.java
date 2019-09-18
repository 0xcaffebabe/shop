package wang.ismy.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.BusinessException;
import wang.ismy.common.vo.ExceptionResult;
import wang.ismy.common.vo.PageResult;
import wang.ismy.item.mapper.BrandMapper;
import wang.ismy.pojo.Brand;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/18 19:35
 */
@Service
public class BrandService {

    private BrandMapper mapper;

    public BrandService(BrandMapper mapper) {
        this.mapper = mapper;
    }

    public PageResult<Brand> pageQuery(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        // 分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Brand.class);
        if (!StringUtils.isEmpty(key)) {
            example.createCriteria().orLike("name","%"+key+"%")
                    .orEqualTo("letter",key.toUpperCase());
        }
        // 排序
        if (!StringUtils.isEmpty(sortBy)){
            String orderByClause = sortBy + " "+ (desc?"DESC":"ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        Page<Brand> brands = (Page<Brand>) mapper.selectByExample(example);

        if (CollectionUtils.isEmpty(brands)){
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }

        PageResult<Brand> result  = new PageResult<>();
        result.setTotal(brands.getTotal());
        result.setItems(brands.getResult());
        result.setTotalPage((long)brands.getPages());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Brand brand, List<Long> cids) {
        brand.setId(null);
        if (mapper.insert(brand) != 1){
            throw new BusinessException(ExceptionEnum.SERVER_ERROR);
        }

        // 新增中间表映射
        for (Long cid : cids) {
            int count = mapper.insertCategoryBrand(cid, brand.getId());

            if (count != 1){
                throw new BusinessException(ExceptionEnum.SERVER_ERROR);
            }
        }

    }
}
