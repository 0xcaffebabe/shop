package wang.ismy.item.service;

import com.fasterxml.jackson.databind.node.LongNode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.BusinessException;
import wang.ismy.item.mapper.SpecGroupMapper;
import wang.ismy.item.mapper.SpecParamMapper;
import wang.ismy.pojo.entity.SpecGroup;
import wang.ismy.pojo.entity.SpecParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/9/20 15:12
 */
@Service
@AllArgsConstructor
public class SpecificationService {

    private SpecGroupMapper specGroupMapper;

    private SpecParamMapper specParamMapper;

    public List<SpecGroup> selectByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> list = specGroupMapper.select(specGroup);

        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }

        return list;
    }

    public List<SpecParam> selectParamByGid(Long gid) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        List<SpecParam> list = specParamMapper.select(param);

        if (CollectionUtils.isEmpty(list)){
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(SpecGroup specGroup) {
        if (specGroup.getCid() == null){
            throw new BusinessException(ExceptionEnum.INVALID_DATA);
        }

        if (StringUtils.isEmpty(specGroup.getName())){
            throw new BusinessException(ExceptionEnum.INVALID_DATA);
        }

        specGroupMapper.insert(specGroup);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(SpecParam specParam) {

        specParamMapper.insert(specParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SpecGroup specGroup) {

        if (specGroup.getId() == null){
            throw new BusinessException(ExceptionEnum.INVALID_DATA);
        }

        if (specGroupMapper.selectByPrimaryKey(specGroup.getId()) == null){
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }

        if (StringUtils.isEmpty(specGroup.getName())){
            throw new BusinessException(ExceptionEnum.INVALID_DATA);
        }

        specGroupMapper.updateByPrimaryKey(specGroup);

    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SpecParam specParam) {

        if (specParam.getGroupId() == null){
            throw new BusinessException(ExceptionEnum.INVALID_DATA);
        }

        if (specParam.getId() == null){
            throw new BusinessException(ExceptionEnum.INVALID_DATA);
        }

        specParamMapper.updateByPrimaryKey(specParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long gid) {

        if (specGroupMapper.selectByPrimaryKey(gid) == null){
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }

        specGroupMapper.deleteByPrimaryKey(gid);
    }

    public void deleteParam(Long pid) {

        specParamMapper.deleteByPrimaryKey(pid);
    }

    public List<SpecParam> selectParam(Long gid, Long cid, Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> list = specParamMapper.select(param);

        if (CollectionUtils.isEmpty(list)){
            throw new BusinessException(ExceptionEnum.NOT_FOUND);
        }
        return list;
    }

    public List<SpecGroup> querySpecsByCid(Long cid) {
        List<SpecGroup> specGroups = selectByCid(cid);

        // 查询所有规格参数
        List<SpecParam> specParams = selectParam(null, cid, null);

        Map<Long,List<SpecParam>> map = new HashMap<>();

        for (SpecParam specParam : specParams) {
            if (CollectionUtils.isEmpty(map.get(specParam.getGroupId()))){
                map.put(specParam.getGroupId(),new ArrayList<>());
            }
            map.get(specParam.getGroupId()).add(specParam);
        }

        for (SpecGroup specGroup : specGroups) {
            specGroup.setParams(map.get(specGroup.getId()));
        }
        return specGroups;
    }
}
