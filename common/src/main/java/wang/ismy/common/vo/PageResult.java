package wang.ismy.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/18 19:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    private Long total;

    private Integer totalPage;

    private List<T> items;


}
