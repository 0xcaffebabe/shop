package wang.ismy.item.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MY
 * @date 2019/9/17 18:52
 */
@RestController
@RequestMapping("/api/")
public class TestController {

    @RequestMapping("test")
    public String test(){
        return "hello world";
    }
}
