package com.intrack.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * References a generic type.
 *
 * @author intrack
 */
public abstract class TypeReference<T> {

    private final Type rawTpe;

    protected TypeReference() {
        rawTpe = getSupperclassTypeParameter(getClass());
    }

    Type getSupperclassTypeParameter(Class<?> clazz) {
        Type genericSupperclass = clazz.getGenericSuperclass();
        /**
         * 如果genericSupperClass是泛型类，则此处if条件为false.
         */
        if (genericSupperclass instanceof Class) {
            /**
             * 向上遍历直到genericSupperclass为泛型类.
             */
            if (TypeReference.class != genericSupperclass) {
                return getSupperclassTypeParameter(clazz.getSuperclass());
            }

            throw new TypeException("." + getClass() + "' extends TypeReference but misses the type parameter." +
                                    " Remove the extension or add a type parameter to it.");
        }

        Type rawType = ((ParameterizedType) genericSupperclass).getActualTypeArguments()[0];
        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType) rawType).getRawType();
        }

        return rawType;
    }

    public final Type getRawTpe() {
        return rawTpe;
    }

    @Override
    public String toString() {
        return rawTpe.toString();
    }

}
