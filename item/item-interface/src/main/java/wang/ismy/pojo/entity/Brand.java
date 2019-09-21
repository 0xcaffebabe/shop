package wang.ismy.pojo.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author MY
 * @date 2019/9/18 19:32
 */
@Table(name = "tb_brand")
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
    * 品牌名称
    */
    private String name;

    /**
     * 品牌图片
     * */
    private String image;

    private Character letter;

}
