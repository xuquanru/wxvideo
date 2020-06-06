package com.rootbant.wxapp.controller;

import com.rootbant.wxapp.utils.RedisOperator;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 🍁 Program: wxapp
 * <p>
 * 🍁 Description
 * <p>
 * 🍁 Author: Stephen
 * <p>
 * 🍁 Create: 2020-03-04 18:30
 **/
@RestController
public class BasicController {
    @Resource
    public RedisOperator redis;
    public static final String USER_REDIS_SESSION="user-redis-session";


}
