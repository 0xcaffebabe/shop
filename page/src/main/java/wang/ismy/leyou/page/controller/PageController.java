package wang.ismy.leyou.page.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import wang.ismy.leyou.page.service.PageService;

/**
 * @author MY
 * @date 2019/9/25 13:39
 */
@Controller
@AllArgsConstructor
public class PageController {

    private PageService pageService;

    @GetMapping("item/{id}.html")
    public ModelAndView page(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();

        mv.addAllObjects(pageService.loadModel(id));
        mv.setViewName("item");
        return mv;
    }
}
