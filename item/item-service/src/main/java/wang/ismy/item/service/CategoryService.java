package wang.ismy.item.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.Assertion;
import wang.ismy.common.exception.BusinessException;
import wang.ismy.item.mapper.CategoryMapper;
import wang.ismy.pojo.entity.Category;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/18 13:08
 */
@Service
public class CategoryService {

    private CategoryMapper mapper;

    public CategoryService(CategoryMapper mapper) {
        this.mapper = mapper;
    }

    public List<Category> queryListByPid(Long pid) {
        // 通用mapper会把该对象的非空属性作为查询条件
        Category c = new Category();
        c.setParentId(pid);
        List<Category> list = mapper.select(c);

        if (CollectionUtils.isEmpty(list)){
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }
        return list;
    }

    public Category getCategoryById(Long bid) {
        Category category = mapper.selectByPrimaryKey(bid);

        if (category == null){
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }
        return category;
    }

    public List<Category> getCategoryByBrand(Long bid) {
        return mapper.getCategoryByBrand(bid);
    }

    public List<Category> selectByIds(List<Long> ids){
        List<Category> categories = mapper.selectByIdList(ids);
        Assertion.assertNotEmpty(categories);
        return categories;
    }

    public List<Category> queryCategoryByIds(List<Long> ids) {
        List<Category> categories = mapper.selectByIdList(ids);
        Assertion.assertNotEmpty(categories);
        return categories;
    }
}
