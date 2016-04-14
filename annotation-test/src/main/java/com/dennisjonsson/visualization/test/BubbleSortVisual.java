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
import com.dennisjonsson.markup.AbstractType;


public class BubbleSortVisual{
public static com.dennisjonsson.log.ast.ASTLogger logger = 
com.dennisjonsson.log.ast.ASTLogger.instance(
new com.dennisjonsson.log.ast.SourceHeader(
"BubbleSortVisual",
"",
new com.dennisjonsson.markup.DataStructure [] {  com.dennisjonsson.markup.DataStructureFactory.getDataStructure("array","int[]","intArray")},
com.dennisjonsson.log.DefaultInterpreter.instance()));

    
    public static void sort(int intArray[]) {
        int n = intArray.length;
        int temp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (eval(null, intArray[read("intArray", 0, j - 1)], 2) > eval(null, intArray[read("intArray", 0, j)], 2)) {
                    //swap the elements!
                    eval("temp", temp = write("intArray", intArray[read("intArray", 0, j - 1)], 0, 1), 0);
                    eval("intArray[j - 1]", intArray[read("intArray", 0, j - 1)] = write("intArray", intArray[read("intArray", 0, j)], 0, 0), 0);
                    eval("intArray[j]", intArray[read("intArray", 0, j)] = write("temp", temp, 1, 0), 0);
                }
            }
        }
    }

    
    public static void print() {
        logger.print();
    }

public static int eval( String targetId, int value, int expressionType){
logger.eval("BubbleSortVisual", targetId, value, expressionType);
return value;
}
public static int write(String name, int value, int sourceType, int targetType ){
logger.write("BubbleSortVisual", name, value, sourceType, targetType);
return value;
}
public static int[] eval( String targetId, int[] value, int expressionType){
logger.eval("BubbleSortVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType);
return value;
}
public static int[] write(String name, int[] value, int sourceType, int targetType ){
logger.write("BubbleSortVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static int[][] eval( String targetId, int[][] value, int expressionType){
logger.eval("BubbleSortVisual", targetId, new com.dennisjonsson.log.ast.LogUtils<int[][]>().deepCopy(value), expressionType);
return value;
}
public static int[][] write(String name, int[][] value, int sourceType, int targetType ){
logger.write("BubbleSortVisual", name, new com.dennisjonsson.log.ast.LogUtils<int[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index){ 
logger.read("BubbleSortVisual", name ,index ,dimension);
return index; 
}
}
