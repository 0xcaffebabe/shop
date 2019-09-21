package wang.ismy.pojo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author MY
 * @date 2019/9/20 15:32
 */
@Data
@Table(name = "tb_spec_param")
public class SpecParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cid;

    private Long groupId;

    private String name;

    @Column(name = "`numeric`")
    private Boolean numeric;

    private String unit;

    private Boolean generic;

    private Boolean searching;

    private String segments;
}
