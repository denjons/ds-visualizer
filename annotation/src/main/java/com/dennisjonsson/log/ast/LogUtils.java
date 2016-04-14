/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log.ast;

import java.lang.reflect.Array;

/**
 *
 * @author dennis
 */
public class LogUtils<T> {
    
    public static final String CLASS_NAME = "com.dennisjonsson.log.ast.LogUtils";
    public static final String COPY = "deepCopy";
    
    public static <T> T[] deepCopy(T[] array) {

    if (0 >= array.length) return array;

    return (T[]) deepCopy(
            array, 
            Array.newInstance(array[0].getClass(), array.length), 
            0);
}

private static Object deepCopy(Object array, Object copiedArray, int index) {

    if (index >= Array.getLength(array)) return copiedArray;

    Object element = Array.get(array, index);

    if (element.getClass().isArray()) {

        Array.set(copiedArray, index, deepCopy(
                element,
                Array.newInstance(
                        element.getClass().getComponentType(),
                        Array.getLength(element)),
                0));

    } else {

        Array.set(copiedArray, index, element);
    }

    return deepCopy(array, copiedArray, ++index);
}
}
