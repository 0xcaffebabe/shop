package wang.ismy.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wang.ismy.pojo.entity.SpecGroup;
import wang.ismy.pojo.entity.SpecParam;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/23 18:54
 */
@RequestMapping("spec")
public interface SpecificationApi {
    @GetMapping("/params")
    List<SpecParam> selectParam(@RequestParam(value = "gid", required = false) Long gid,
                                @RequestParam(value = "cid", required = false) Long cid,
                                @RequestParam(value = "searching", required = false) Boolean searching);

    @GetMapping("{cid}")
    SpecParam selectByCid(@PathVariable Long cid);
}
