package wang.ismy.pojo.vo;

import lombok.Data;

import org.springframework.beans.BeanUtils;
import wang.ismy.pojo.entity.Spu;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2019/9/21 13:55
 */
@Data
public class SpuVO {

    private Long id;
    private String brandName;
    private String categoryName;
    private String title;// 标题
    private String subTitle;// 子标题
    private Boolean saleable;// 是否上架
    private Boolean valid;// 是否有效，逻辑删除用
    private LocalDateTime createTime;// 创建时间

    public static SpuVO convert(Spu spu){
        SpuVO vo = new SpuVO();
        BeanUtils.copyProperties(spu,vo);
        return vo;
    }
}
