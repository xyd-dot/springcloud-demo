package com.cloud.controller;

import com.cloud.domain.dto.UserDto;
import com.ligong.common.domain.R;
import com.ligong.common.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @description:
 * @since: 2023/10/9 17:31
 * @author: sdw
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @PostMapping("/login")
    public R login(@RequestBody UserDto userDto){

        //1.查询数据库，判断该用户是否存在

        String userId = userDto.getUserId();
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        HashMap<String,String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("username",username);
        map.put("password",password);

        String token = JWTUtils.getToken(map);
        log.info(token);

        return R.ok(token);
    }

    @GetMapping("/hello")
    public R hello(){
        return R.ok("hello");
    }

}
