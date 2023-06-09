package com.jack.financialweb.model;

import com.jack.financialweb.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    public enum PayType {A, C};
    public enum AutoBilling {Y, N};
    public enum Status {S,F};
    public enum APType { PayOut, CaptureOut, RegularOut, RegularEnd };
    /**
     * 轉換成字串(永豐API格式)
     * @return
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> parameters = new ArrayList<>();

        // Get all fields using reflection
        Field[] fields = ReflectionUtil.GetAllFields(this.getClass());

        // Iterate over the fields
        for (Field field : fields) {
            try {
                field.setAccessible(true); //設定該元素可訪問
                Object value = field.get(this);

                if (value != null && isPrimitiveOrWrapper(value)) {
                    String parameter = field.getName() + "=" + value.toString();
                    parameters.add(parameter);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Sort the parameters alphabetically
        Collections.sort(parameters);

        // Build the order string
        for (String parameter : parameters) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("&");
            }
            stringBuilder.append(parameter);
        }

        return stringBuilder.toString();
    }

    /**
     * 是否為單節點類型
     *
     * @param obj
     * @return
     */
    private boolean isPrimitiveOrWrapper(Object obj) {
        Class clazz = obj.getClass();
        if(clazz.isPrimitive() ||
                clazz.equals(Boolean.class) ||
                clazz.equals(Character.class) ||
                clazz.equals(Byte.class) ||
                clazz.equals(Short.class) ||
                clazz.equals(Integer.class) ||
                clazz.equals(Long.class) ||
                clazz.equals(Float.class) ||
                clazz.equals(Double.class) ||
                clazz.isEnum()){
            return true;
        }

        if(clazz.equals(String.class) && !(((String) obj).isEmpty())){
            return true;
        }

        return false;
    }
}
