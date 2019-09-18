package wang.ismy.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/18 19:51
 */
@Data
public class PageResult<T> {

    private Long total;

    private Long totalPage;

    private List<T> items;


}
