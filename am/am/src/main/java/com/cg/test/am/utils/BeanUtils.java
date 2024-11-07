package com.cg.test.am.utils;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 拷贝
 * @author hao.yan
 */
@Slf4j
public class BeanUtils {


    /**
     * bean 拷贝
     */
    public static <T, E> T toT(E source, Class<T> tClass){
        try {
            // 构建bean
            T t = tClass.newInstance();
            if (source == null){
                return t;
            }
            return toT(source, t);
        }catch (Exception e){
            log.error("bean 拷贝 ", e);
            return null;
        }
    }

    /**
     * bean 拷贝
     */
    public static <T, E> T toT(E source, T target){
        PropertyDescriptor[] properties = null;
        try {
            // 获取bean信息
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
            // 获取bean的所有属性列表
            properties = beanInfo.getPropertyDescriptors();
        }catch (Exception e){
            log.error("bean 拷贝 ", e);
            return null;
        }

        // 验证字段是否存在
        if (ArrayUtils.isEmpty(properties)) {
            return target;
        }

        // 遍历属性列表，查找指定的属性
        for (PropertyDescriptor propDesc : properties) {
            // 写入字段值
            Method writeMethod = propDesc.getWriteMethod();
            if (writeMethod == null){
                continue;
            }
            try {
                // 读取字段值
                PropertyDescriptor descriptor = new PropertyDescriptor(propDesc.getName(), source.getClass());
                Method readMethod = descriptor.getReadMethod();
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                Object value = readMethod.invoke(source);
                // 写入字段值
                writeMethod = propDesc.getWriteMethod();
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                writeMethod.invoke(target, value);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e){
                if (log.isDebugEnabled()) {
                    log.warn("没有找到方法 ({})", writeMethod.getName());
                }
            }
        }
        return target;
    }

    /**
     * 深度拷贝
     */
    public static <T, E> List<T> toList(List<E> source, Class<T> tClass){
        return toList(source, item->toT(item, tClass));
    }

    /**
     * 深度拷贝
     */
    public static <T, E> List<T> toList(List<E> source, Function<? super E, ? extends T> function){
        if (CollectionUtils.isEmpty(source)){
            return new ArrayList<>();
        }
        return source.stream().map(function).collect(Collectors.toList());
    }

}
