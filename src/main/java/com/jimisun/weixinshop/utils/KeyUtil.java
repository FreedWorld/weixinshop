package com.jimisun.weixinshop.utils;

import java.util.Random;
import java.util.UUID;

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
     *
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        return System.currentTimeMillis() + String.valueOf(random.nextInt(90000) + 10000);

    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static synchronized String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] retArray = new String[number];
        for (int i = 0; i < number; i++) {
            retArray[i] = getUUID();
        }
        return retArray;
    }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static synchronized String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
