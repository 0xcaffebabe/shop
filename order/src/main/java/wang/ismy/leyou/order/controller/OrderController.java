package wang.ismy.leyou.order.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.ismy.leyou.order.dto.OrderDTO;
import wang.ismy.leyou.order.pojo.Order;
import wang.ismy.leyou.order.service.OrderService;

import javax.validation.Valid;

/**
 * @author MY
 * @date 2019/9/30 20:13
 */
@RestController
@RequestMapping("")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody @Valid OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> queryOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.queryOrderById(id));
    }
}
