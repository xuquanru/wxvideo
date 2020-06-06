package com.rootbant.wxapp.utils;

import java.util.UUID;

/**
 * 🍁 Program: wxapp
 * <p>
 * 🍁 Description
 * <p>
 * 🍁 Author: Stephen
 * <p>
 * 🍁 Create: 2020-03-05 23:29
 **/
public class UuidUtils {
    public  static String  getUuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
