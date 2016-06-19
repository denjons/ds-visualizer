/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.annotation.markup.AbstractType;


public class BubbleSortVisual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("BubbleSortVisual",new String [] { "/*"," * To change this license header, choose License Headers in Project Properties."," * To change this template file, choose Tools | Templates"," * and open the template in the editor."," */","package com.dennisjonsson.visualization.test;","","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.annotation.Visualize;","import com.dennisjonsson.annotation.markup.AbstractType;","","@VisualClass","public class BubbleSort {","","    public static void sort(@Visualize(abstractType = 'array') int intArray[]) {","        //intArray = intArray;","        int n = intArray.length;","        int temp = 0;","        for (int i = 0; i < n; i++) {","            for (int j = 1; j < (n - i); j++) {","                if (intArray[j - 1] > intArray[j]) {","                    //swap the elements!","                    temp = intArray[j - 1];","                    intArray[j - 1] = intArray[j];","                    intArray[j] = temp;","                }","            }","        }","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] {  com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","int[]","com.dennisjonsson.visualization.test.BubbleSort sort intArray")},new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:.Users.dennis.Documents.NetBeansProjects.annotation-test"));

    public static void sort( int intArray[]) {
        //intArray = intArray;
        int n = intArray.length;
        int temp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (eval(null, intArray[read("com.dennisjonsson.visualization.test.BubbleSort sort intArray", 0, j - 1)], 2, new int[] { 24, 24 }) > eval(null, intArray[read("com.dennisjonsson.visualization.test.BubbleSort sort intArray", 0, j)], 2, new int[] { 24, 24 })) {
                    //swap the elements!
                    eval("temp", temp = write("com.dennisjonsson.visualization.test.BubbleSort sort intArray", intArray[read("com.dennisjonsson.visualization.test.BubbleSort sort intArray", 0, j - 1)], 0, 1), 0, new int[] { 26, 26 });
                    eval("com.dennisjonsson.visualization.test.BubbleSort sort intArray", intArray[read("com.dennisjonsson.visualization.test.BubbleSort sort intArray", 0, j - 1)] = write("com.dennisjonsson.visualization.test.BubbleSort sort intArray", intArray[read("com.dennisjonsson.visualization.test.BubbleSort sort intArray", 0, j)], 0, 0), 0, new int[] { 27, 27 });
                    eval("com.dennisjonsson.visualization.test.BubbleSort sort intArray", intArray[read("com.dennisjonsson.visualization.test.BubbleSort sort intArray", 0, j)] = write("temp", temp, 1, 0), 0, new int[] { 28, 28 });
                }
            }
        }
    }

public static int eval( String targetId, int value, int expressionType, int [] line){
logger.eval("BubbleSortVisual", targetId, value, expressionType, line);
return value;
}
public static int write(String name, int value, int sourceType, int targetType ){
logger.write("BubbleSortVisual", name, value, sourceType, targetType);
return value;
}
public static int[] eval( String targetId, int[] value, int expressionType, int [] line){
logger.eval("BubbleSortVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType, line);
return value;
}
public static int[] write(String name, int[] value, int sourceType, int targetType ){
logger.write("BubbleSortVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static int[][] eval( String targetId, int[][] value, int expressionType, int [] line){
logger.eval("BubbleSortVisual", targetId, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), expressionType, line);
return value;
}
public static int[][] write(String name, int[][] value, int sourceType, int targetType ){
logger.write("BubbleSortVisual", name, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index ){ 
logger.read("BubbleSortVisual", name ,index ,dimension);
return index; 
}
}
