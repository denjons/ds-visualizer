/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.Visualize;

 class BinarySearchVisual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("BinarySearchVisual",new String [] { "/*"," * To change this license header, choose License Headers in Project Properties."," * To change this template file, choose Tools | Templates"," * and open the template in the editor."," */","package com.dennisjonsson.visualization.test;","","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.annotation.Visualize;","","@VisualClass","class BinarySearch {","","    public static int binarySearch(@Visualize(abstractType = 'array') int[] array, int search) {","        int first, last, middle;","        first = 0;","        last = array.length - 1;","        middle = (first + last) / 2;","        while (first <= last) {","            if (array[middle] < search)","                first = middle + 1;","            else if (array[middle] == search) {","                return middle + 1;","            } else","                last = middle - 1;","            middle = (first + last) / 2;","        }","        if (first > last)","            return -1;","        else","            return first;","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] {  com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","int[]","com.dennisjonsson.visualization.test.BinarySearch binarySearch array")},new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:.Users.dennis.Documents.NetBeansProjects.annotation-test"));

    public static int binarySearch( int[] array, int search) {
        int first, last, middle;
        first = 0;
        last = array.length - 1;
        middle = (first + last) / 2;
        while (first <= last) {
            if (eval(null, array[read("com.dennisjonsson.visualization.test.BinarySearch binarySearch array", 0, middle)], 2, new int[] { 26, 26 }) < search)
                first = middle + 1;
            else if (eval(null, array[read("com.dennisjonsson.visualization.test.BinarySearch binarySearch array", 0, middle)], 2, new int[] { 28, 28 }) == search) {
                return middle + 1;
            } else
                last = middle - 1;
            middle = (first + last) / 2;
        }
        if (first > last)
            return -1;
        else
            return first;
    }

public static int eval( String targetId, int value, int expressionType, int [] line){
logger.eval("BinarySearchVisual", targetId, value, expressionType, line);
return value;
}
public static int write(String name, int value, int sourceType, int targetType ){
logger.write("BinarySearchVisual", name, value, sourceType, targetType);
return value;
}
public static int[] eval( String targetId, int[] value, int expressionType, int [] line){
logger.eval("BinarySearchVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType, line);
return value;
}
public static int[] write(String name, int[] value, int sourceType, int targetType ){
logger.write("BinarySearchVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static int[][] eval( String targetId, int[][] value, int expressionType, int [] line){
logger.eval("BinarySearchVisual", targetId, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), expressionType, line);
return value;
}
public static int[][] write(String name, int[][] value, int sourceType, int targetType ){
logger.write("BinarySearchVisual", name, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index ){ 
logger.read("BinarySearchVisual", name ,index ,dimension);
return index; 
}
}
