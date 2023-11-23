package com.careyq.alive.core.util;

import cn.hutool.core.collection.CollUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合工具类扩展
 *
 * @author CareyQ
 */
public class CollUtils extends CollUtil {

    /**
     * 获取列表中的某个字段并转为 SET
     *
     * @param coll 列表
     * @param func 获取字段
     * @return List
     */
    public static <T, U> Set<U> convertSet(Collection<T> coll, Function<T, U> func) {
        if (isEmpty(coll)) {
            return new HashSet<>();
        }
        return coll.stream().map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * 转换为指定内容的 List
     *
     * @param coll 列表
     * @param func 转换逻辑
     * @return List
     */
    public static <T, U> List<U> convertList(Collection<T> coll, Function<T, U> func) {
        if (isEmpty(coll)) {
            return new ArrayList<>();
        }
        return coll.stream().map(func).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
