/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.annotation.VisualizeArg;
import com.dennisjonsson.markup.AbstractType;
import java.util.Arrays;

/**
 *
 * @author dennis
 */

public class QuickSortVisual{
public static com.dennisjonsson.log.ast.ASTLogger logger = 
com.dennisjonsson.log.ast.ASTLogger.instance(
new com.dennisjonsson.log.ast.SourceHeader(
"QuickSortVisual",
null,
new com.dennisjonsson.markup.DataStructure [] {  com.dennisjonsson.markup.DataStructureFactory.getDataStructure("array","int[]","arr")},
com.dennisjonsson.log.DefaultInterpreter.instance()));

    public static void sort(int[] array) {
        quickSort(eval("arr", write(null, array, 3, 1), 3), 0, array.length - 1);
    }

    
    public static void quickSort(int[] arr, int low, int high) {
        if (arr == null || arr.length == 0)
            return;
        if (low >= high)
            return;
        // pick the pivot
        int middle = low + (high - low) / 2;
        int pivot = eval("pivot", write("arr", arr[read("arr", 0, middle)], 0, 1), 0);
        // make left < pivot and right > pivot
        int i = low, j = high;
        while (i <= j) {
            while (eval(null, arr[read("arr", 0, i)], 2) < pivot) {
                i++;
            }
            while (eval(null, arr[read("arr", 0, j)], 2) > pivot) {
                j--;
            }
            if (i <= j) {
                int temp = eval("temp", write("arr", arr[read("arr", 0, i)], 0, 1), 0);
                eval("arr[i]", arr[read("arr", 0, i)] = write("arr", arr[read("arr", 0, j)], 0, 0), 0);
                eval("arr[j]", arr[read("arr", 0, j)] = write("temp", temp, 1, 0), 0);
                i++;
                j--;
            }
        }
        // recursively sort two sub parts
        if (low < j)
            quickSort(eval("arr", write("arr", arr, 1, 1), 3), low, j);
        if (high > i)
            quickSort(eval("arr", write("arr", arr, 1, 1), 3), i, high);
    }

public static int eval( String targetId, int value, int expressionType){
logger.eval("QuickSortVisual", targetId, value, expressionType);
return value;
}
public static int write(String name, int value, int sourceType, int targetType ){
logger.write("QuickSortVisual", name, value, sourceType, targetType);
return value;
}
public static int[] eval( String targetId, int[] value, int expressionType){
logger.eval("QuickSortVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType);
return value;
}
public static int[] write(String name, int[] value, int sourceType, int targetType ){
logger.write("QuickSortVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static int[][] eval( String targetId, int[][] value, int expressionType){
logger.eval("QuickSortVisual", targetId, new com.dennisjonsson.log.ast.LogUtils<int[][]>().deepCopy(value), expressionType);
return value;
}
public static int[][] write(String name, int[][] value, int sourceType, int targetType ){
logger.write("QuickSortVisual", name, new com.dennisjonsson.log.ast.LogUtils<int[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index){ 
logger.read("QuickSortVisual", name ,index ,dimension);
return index; 
}
}
