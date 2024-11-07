package com.cg.test.am.utils;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 集合转换问题
 * @author hao.yan
 */
public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> list){
        return list == null || list.size() == 0;
    }

    /**
     * 字符串截取转list集合
     * @param str 字符串 如：1,2,3
     * @param regex 分隔符
     * @return 返回List集合
     */
    public static List<String> toList(String str,String regex){
        return toCollection(str, regex, ArrayList::new);
    }

    /**
     * 字符串截取转set集合
     * @param str 字符串 如：1,2,3
     * @param regex 分隔符
     * @return 返回set集合
     */
    public static Set<String> toSet(String str,String regex){
       return toCollection(str, regex, HashSet::new);
    }

    /**
     * 字符串截取转集合
     * @param str 字符串 如：1,2,3
     * @param regex 分隔符
     * @param supplier 转换类型
     * @return 返回集合
     */
    public static <C extends Collection<String>> C toCollection(String str, String regex, Supplier<C> supplier){
        if (StringUtils.isEmpty(str)){
            return supplier.get();
        }
        return toCollection(str.split(regex), supplier);
    }

    /**
     * 数值转集合
     * @param array 数据
     * @return 返回List集合
     */
    public static <T> List<T> toList(T[] array){
        return Arrays.stream(array).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 数值转集合
     * @param array 数据
     * @return 返回set集合
     */
    public static <T> Set<T> toSet(T[] array){
        return Arrays.stream(array).collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * 数值转集合
     * @param array 数据
     * @param supplier 转换类型
     * @return
     */
    public static <T, C extends Collection<T>> C toCollection(T[] array, Supplier<C> supplier){
        return Arrays.stream(array).collect(Collectors.toCollection(supplier));
    }

}
