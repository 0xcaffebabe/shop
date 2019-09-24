package wang.ismy.leyou.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author MY
 * @date 2019/9/24 22:10
 */
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
}
