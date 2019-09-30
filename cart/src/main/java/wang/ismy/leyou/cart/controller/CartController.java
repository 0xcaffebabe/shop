package wang.ismy.leyou.cart.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.ismy.leyou.cart.pojo.Cart;
import wang.ismy.leyou.cart.service.CartService;

import java.util.List;

/**
 * @author MY
 * @date 2019/9/30 13:09
 */
@RestController
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("list")
    public ResponseEntity<List<Cart>> queryList(){
        return ResponseEntity.ok(cartService.findAll());
    }

    @PutMapping
    public ResponseEntity<Void> modifyNum(@RequestParam Long id,@RequestParam Integer num){

        cartService.modifyNum(id,num);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("{skuId}")
    public ResponseEntity<Void> delete(@PathVariable String skuId){
        cartService.delete(skuId);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}
