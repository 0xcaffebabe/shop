package wang.ismy.leyou.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author MY
 * @date 2019/9/25 13:39
 */
@Controller
public class PageController {

    @GetMapping("item/{id}.html")
    public ModelAndView page(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();



        mv.setViewName("item");
        return mv;
    }
}
