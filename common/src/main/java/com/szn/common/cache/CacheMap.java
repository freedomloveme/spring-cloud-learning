package com.szn.common.cache;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class CacheMap {

    // 缓存结果集
    private static ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<>();

    // 缓存时间结果集
    private static ConcurrentHashMap<String, Object> dataExpireMap = new ConcurrentHashMap<>();

    // 默认缓存过期时间30分钟
    private static final int defaultExpireMinute = 30;

    private static Integer expireMinute;

    /**
     * 设置缓存过期时间
     *
     * @param minute 分钟数
     */
    public static void setExpireMinute(int minute) {
        expireMinute = minute;
    }

    /**
     * 缓存存放数据
     *
     * @param key   键
     * @param value 值
     * @param flag  是否需要启用缓存过期时间
     */
    public static void put(String key, Object value, boolean flag) {
        dataMap.putIfAbsent(key, value);
        if (flag) {
            if (null == expireMinute) {
                dataExpireMap.putIfAbsent(key, DateUtils.addMinutes(new Date(), defaultExpireMinute));
            } else {
                dataExpireMap.putIfAbsent(key, DateUtils.addMinutes(new Date(), expireMinute));
            }
        }
    }

    /**
     *  从缓存中获取数据
     * @param key 键
     * @return  结果
     */
    public static Object get(String key) {
        Object obj = null;
        Date expireDate = (Date) dataExpireMap.get(key);
        if (expireDate != null && expireDate.compareTo(new Date()) > 0) {
            obj = dataMap.get(key);
        }
        return obj;
    }

}
