package com.cloud.controller;

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
    public String hello(){


        System.out.println("----------------------");

        return "ok";
    }


}
