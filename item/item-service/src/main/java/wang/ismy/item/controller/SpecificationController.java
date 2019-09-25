package wang.ismy.item.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.ismy.item.service.SpecificationService;
import wang.ismy.pojo.entity.SpecGroup;
import wang.ismy.pojo.entity.SpecParam;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/20 15:12
 */
@RestController
@RequestMapping("spec")
@AllArgsConstructor
public class SpecificationController {

    private SpecificationService specificationService;

    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> selectByCid(@PathVariable Long cid) {

        return ResponseEntity.ok(specificationService.selectByCid(cid));
    }

    @PostMapping("/group")
    public ResponseEntity<Void> save(@RequestBody SpecGroup specGroup) {

        specificationService.save(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/group")
    public ResponseEntity<Void> update(@RequestBody SpecGroup specGroup) {

        specificationService.update(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/group/{gid}")
    public ResponseEntity<Void> delete(@PathVariable Long gid) {

        specificationService.delete(gid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @param cid       目录ID
     * @param gid       组ID
     * @param searching 搜索
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> selectParam(@RequestParam(value = "gid", required = false) Long gid,
                                                       @RequestParam(value = "cid", required = false) Long cid,
                                                       @RequestParam(value = "searching", required = false) Boolean searching) {

        return ResponseEntity.ok(specificationService.selectParam(gid,cid,searching));


    }

    @PostMapping("/param")
    public ResponseEntity<Void> save(@RequestBody SpecParam specGroup) {

        specificationService.save(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/param")
    public ResponseEntity<Void> update(@RequestBody SpecParam specGroup) {

        specificationService.update(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/param/{pid}")
    public ResponseEntity<Void> deleteParam(@PathVariable("pid") Long pid) {

        specificationService.deleteParam(pid);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("group/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(specificationService.querySpecsByCid(cid));
    }
}
