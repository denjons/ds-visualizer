/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.annotation.VisualizeArg;

 class BinarySearchVisual{
public static com.dennisjonsson.log.ast.ASTLogger logger = 
com.dennisjonsson.log.ast.ASTLogger.instance(
new com.dennisjonsson.log.ast.SourceHeader(
"BinarySearchVisual",
null,
new com.dennisjonsson.markup.DataStructure [] {  com.dennisjonsson.markup.DataStructureFactory.getDataStructure("array","int[]","array")},
com.dennisjonsson.log.DefaultInterpreter.instance()));

    
    public static int binarySearch(int[] array, int search) {
        int first, last, middle;
        first = 0;
        last = array.length - 1;
        middle = (first + last) / 2;
        while (first <= last) {
            if (eval(null, array[read("array", 0, middle)], 2) < search)
                first = middle + 1;
            else if (eval(null, array[read("array", 0, middle)], 2) == search) {
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

public static int eval( String targetId, int value, int expressionType){
logger.eval("BinarySearchVisual", targetId, value, expressionType);
return value;
}
public static int write(String name, int value, int sourceType, int targetType ){
logger.write("BinarySearchVisual", name, value, sourceType, targetType);
return value;
}
public static int[] eval( String targetId, int[] value, int expressionType){
logger.eval("BinarySearchVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType);
return value;
}
public static int[] write(String name, int[] value, int sourceType, int targetType ){
logger.write("BinarySearchVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static int[][] eval( String targetId, int[][] value, int expressionType){
logger.eval("BinarySearchVisual", targetId, new com.dennisjonsson.log.ast.LogUtils<int[][]>().deepCopy(value), expressionType);
return value;
}
public static int[][] write(String name, int[][] value, int sourceType, int targetType ){
logger.write("BinarySearchVisual", name, new com.dennisjonsson.log.ast.LogUtils<int[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index){ 
logger.read("BinarySearchVisual", name ,index ,dimension);
return index; 
}
}
