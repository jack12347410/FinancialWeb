package com.jack.financialweb.util;

import java.lang.reflect.Field;

public class ReflectionUtil{
    /**
     * 取得包含所有父類別元素
     * @param clazz
     * @return
     */
    public static Field[] GetAllFields(Class<?> clazz) {
        if (clazz == null) {
            return new Field[0];
        }

        Field[] declaredFields = clazz.getDeclaredFields();
        Field[] parentFields = GetAllFields(clazz.getSuperclass());

        // 合并字段数组
        Field[] allFields = new Field[declaredFields.length + parentFields.length];
        System.arraycopy(declaredFields, 0, allFields, 0, declaredFields.length);
        System.arraycopy(parentFields, 0, allFields, declaredFields.length, parentFields.length);

        return allFields;
    }
}
