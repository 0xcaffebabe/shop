package wang.ismy.pojo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author MY
 * @date 2019/9/20 15:10
 */
@Data
@Table(name = "tb_spec_group")
public class SpecGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cid;

    private String name;

    @Transient
    private List<SpecParam> params;
}
