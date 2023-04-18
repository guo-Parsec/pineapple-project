package org.pineapple.common.base.utils;

import cn.hutool.core.lang.Filter;
import com.google.common.collect.Lists;
import org.pineapple.common.base.lang.FieldsExtendsMapping;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>反射工具</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
public class ReflectTool {
    /**
     * <p>获取类中的字段按继承层级分类(不添加Object入层级分类中)</p>
     *
     * @param clazz       类对象
     * @param fieldFilter 字段过滤条件
     * @return java.util.Map
     * @author pinea
     * @date 2023/4/18 20:10
     */
    public static List<FieldsExtendsMapping> findExtendsFields(Class<?> clazz, Filter<Field> fieldFilter) {
        return findExtendsFields(clazz, fieldFilter, false);
    }

    /**
     * <p>获取类中的字段按继承层级分类</p>
     *
     * @param clazz       类对象
     * @param fieldFilter 字段过滤条件
     * @param isAddObject 是否添加Object入层级分类中
     * @return java.util.Map
     * @author pinea
     * @date 2023/4/18 20:10
     */
    public static List<FieldsExtendsMapping> findExtendsFields(Class<?> clazz, Filter<Field> fieldFilter, boolean isAddObject) {
        List<FieldsExtendsMapping> mappings = Lists.newArrayList();
        int index = 0;
        while (isAddObject ? clazz != null : clazz != Object.class) {
            List<Field> fields = Lists.newArrayList(clazz.getDeclaredFields())
                    .stream().filter(fieldFilter::accept).collect(Collectors.toList());
            mappings.add(new FieldsExtendsMapping(index--, clazz, fields));
            clazz = clazz.getSuperclass();
        }
        return mappings.stream().sorted(FieldsExtendsMapping::compareTo).collect(Collectors.toList());
    }
}
