package it.aman.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("rawtypes")
public class FieldMapper {
    
    public static void copy(Object source, Object destination, boolean skipNulls) {
        Class fromClass = source.getClass();
        Class toClass = destination.getClass();
        List<Field> sourceFields = collectFields(fromClass);
        List<Field> destinationFields = collectFields(toClass);
        Field target;
        for (Field sourceField : sourceFields) {
            try {
                if ((target = find(sourceField, destinationFields)) != null) {
                    sourceField.setAccessible(true);
                    target.setAccessible(true);
                    Object value = sourceField.get(source);
                    if(skipNulls && value == null) continue; 
                    target.set(destination, sourceField.get(source));
                }
            } catch (Exception e) {
                // skip
            }
        }
    }

    private static List<Field> collectFields(Class c) {
        List<Field> list = new ArrayList<>();
        for (Field field : c.getDeclaredFields()) {
            list.add(field);
        }
        return list;
    }

    private static Field find(Field field, List<Field> fields) {
        Field actual;
        for (Iterator<Field> i = fields.iterator(); i.hasNext();) {
            actual = i.next();
            if (field.getName().equals(actual.getName()) && field.getType().equals(actual.getType())) {
                return actual;
            }
        }
        return null;
    }
    
    private FieldMapper() {
        throw new IllegalStateException("Utility class.");
    }
}