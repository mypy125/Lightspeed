package com.lightspeed.deepclone;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class CopyReflectionUtils {

    /**
     * используем рекурсию для обхода структуры объекта и рефлексию для доступа к его полям.
     * проверяем тип каждого поля и корректно копируем его.
     * @param object коппируемый обект
     * @return возврощается коппированый обект
     */
    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T object){
        return (T) deepCopyInternal(object, new IdentityHashMap<>());
    }

    /**
     * Реализована поддержка глубокого копирования массивов, списков, множеств и словарей.
     * @param object коппируемый обект
     * @param copiedObjects хронит скопированные объекты.
     * @return возврощается коппированый новый обект.
     */
    private static Object deepCopyInternal(Object object, IdentityHashMap<Object, Object> copiedObjects){
        if(object == null || object.getClass().isPrimitive() || object instanceof String ||
                object instanceof Number || object instanceof Boolean || object instanceof Character){
            return object;
        }

        if (copiedObjects.containsKey(object)){
            return copiedObjects.get(object);
        }

        if(object.getClass().isArray()){
            int length = Array.getLength(object);
            Object newArray = Array.newInstance(object.getClass().getComponentType(), length);
            copiedObjects.put(object, newArray);
            for (int i = 0; i < length; i++) {
                Array.set(newArray, i, deepCopyInternal(Array.get(object, i), copiedObjects));
            }
            return newArray;
        }

        if(object instanceof Collection){
            Collection<?> originCollection = (Collection<?>) object;
            Collection<Object> newCollection = originCollection instanceof List ? new ArrayList<>()
                    : originCollection instanceof Set ? new HashSet<>()
                    : new ArrayList<>();
            copiedObjects.put(object, newCollection);
            for (Object item : originCollection){
                newCollection.add(deepCopyInternal(item, copiedObjects));
            }
            return newCollection;
        }

        if(object instanceof Map){
            Map<?, ?> originMap = (Map<?,?>) object;
            Map<Object, Object> newMap  = new HashMap<>();
            copiedObjects.put(object, newMap);
            for (Map.Entry<?, ?> entry : originMap.entrySet()){
                Object newKey = deepCopyInternal(entry.getKey(), copiedObjects);
                Object newValue = deepCopyInternal(entry.getValue(), copiedObjects);
                newMap.put(newKey, newValue);
            }
            return newMap;
        }

        // здесь вызивается пустой конструктор обекта и создается новый копи обекта с пораметрами (DeclaredFields)
        try{
            Object newObject = object.getClass().getDeclaredConstructor().newInstance();
            copiedObjects.put(object, newObject);
            for(Field field : object.getClass().getDeclaredFields()){
                field.setAccessible(true); //доем доступ для полей чтоб подставил аргументы fieldValue
                Object fieldValue = field.get(object);
                Object copiedFieldValue = deepCopyInternal(fieldValue, copiedObjects);
                field.set(newObject, copiedFieldValue);
            }
            return newObject;

        }catch (Exception e){
            throw new RuntimeException("Failed to copy object"+e);
        }
    }
}
