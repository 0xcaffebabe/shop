package wang.ismy.leyou.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.ismy.leyou.user.pojo.User;
import wang.ismy.leyou.user.service.UserService;

import javax.validation.Valid;

/**
 * @author MY
 * @date 2019/9/29 14:36
 */
@RestController
@AllArgsConstructor

public class UserController {

    private UserService userService;

    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable String data, @PathVariable Integer type){
        return ResponseEntity.ok(userService.checkData(data,type));
    }

    @PostMapping("/code")
    public ResponseEntity<Void> sendCode(@RequestParam("phone") String phone){
        userService.sendCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code) {
        Boolean boo = userService.register(user, code);
        if (boo == null || !boo) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("query")
    public ResponseEntity<User> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        User user = userService.queryUser(username, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }
}
