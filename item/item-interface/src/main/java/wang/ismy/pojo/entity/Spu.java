package wang.ismy.pojo.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MY
 * @date 2019/9/21 13:43
 */
@Table(name = "tb_spu")
@Data
public class Spu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long brandId;
    private Long cid1;// 1级类目
    private Long cid2;// 2级类目
    private Long cid3;// 3级类目
    private String title;// 标题
    private String subTitle;// 子标题
    private Boolean saleable;// 是否上架
    private Boolean valid;// 是否有效，逻辑删除用
    private LocalDateTime createTime;// 创建时间
    private LocalDateTime lastUpdateTime;// 最后修改时间

    @Transient
    private List<Sku> skus;

    @Transient
    private SpuDetail spuDetail;
}
