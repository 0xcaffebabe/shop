package wang.ismy.leyou.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.leyou.order.dto.OrderDTO;

import javax.validation.Valid;

/**
 * @author MY
 * @date 2019/9/30 20:13
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody @Valid OrderDTO orderDTO){
        // TODO
        return null;
    }
}
