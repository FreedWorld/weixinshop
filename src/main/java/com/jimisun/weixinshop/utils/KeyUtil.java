package com.jimisun.weixinshop.utils;

import java.util.Random;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 23:14 2018-05-13
 * @Modified By:
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 当前毫秒数+5位随机数
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        return System.currentTimeMillis()+String.valueOf(random.nextInt(90000)+10000);

    }
}
