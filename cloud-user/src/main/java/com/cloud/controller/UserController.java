package com.cloud.controller;

import com.ligong.common.domain.R;
import com.ligong.common.utils.UserLocal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description:
 * @since: 2023/10/9 11:12
 * @author: sdw
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/hello")
    public R hello(){

        Long user = UserLocal.getUser();
        System.out.println(user);

        System.out.println("----------------------");

        return R.ok();
    }


}
