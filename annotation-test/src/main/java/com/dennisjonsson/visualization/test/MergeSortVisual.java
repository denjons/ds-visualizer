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
import java.util.*;


public class MergeSortVisual{
public static com.dennisjonsson.log.ast.ASTLogger logger = 
com.dennisjonsson.log.ast.ASTLogger.instance(
new com.dennisjonsson.log.ast.SourceHeader(
"MergeSortVisual",
null,
new com.dennisjonsson.markup.DataStructure [] {  com.dennisjonsson.markup.DataStructureFactory.getDataStructure("array","java.lang.comparable[]","null"),com.dennisjonsson.markup.DataStructureFactory.getDataStructure("array","java.lang.comparable[]","first"),com.dennisjonsson.markup.DataStructureFactory.getDataStructure("array","java.lang.comparable[]","second"),com.dennisjonsson.markup.DataStructureFactory.getDataStructure("array","java.lang.comparable[]","result")},
com.dennisjonsson.log.DefaultInterpreter.instance()));

    
    public static Comparable[] sort(Comparable[] list) {
        //If list is empty; no need to do anything
        if (list.length <= 1) {
            return list;
        }
        //Split the array in half in two parts
        Comparable[] first = eval("first", write(null, new Comparable[list.length / 2], 3, 1), 0);
        Comparable[] second = eval("second", write(null, new Comparable[list.length - first.length], 3, 1), 0);
        System.arraycopy(list, 0, first, 0, first.length);
        System.arraycopy(list, first.length, second, 0, second.length);
        //Sort each half recursively
        sort(eval("null", write("first", first, 1, 1), 3));
        sort(eval("null", write("second", second, 1, 1), 3));
        //Merge both halves together, overwriting to original array
        merge(eval("first", write("first", first, 1, 1), 3), eval("second", write("second", second, 1, 1), 3), eval("result", write(null, list, 3, 1), 3));
        return list;
    }

    
    private static void merge(Comparable[] first, Comparable[] second, Comparable[] result) {
        //Index Position in first array - starting with first element
        int iFirst = 0;
        //Index Position in second array - starting with first element
        int iSecond = 0;
        //Index Position in merged array - starting with first position
        int iMerged = 0;
        //and move smaller element at iMerged
        while (iFirst < first.length && iSecond < second.length) {
            if (eval(null, first[read("first", 0, iFirst)], 2).compareTo(eval(null, second[read("second", 0, iSecond)], 2)) < 0) {
                eval("result[iMerged]", result[read("result", 0, iMerged)] = write("first", first[read("first", 0, iFirst)], 0, 0), 0);
                iFirst++;
            } else {
                eval("result[iMerged]", result[read("result", 0, iMerged)] = write("second", second[read("second", 0, iSecond)], 0, 0), 0);
                iSecond++;
            }
            iMerged++;
        }
        //copy remaining elements from both halves - each half will have already sorted elements
        System.arraycopy(first, iFirst, result, iMerged, first.length - iFirst);
        System.arraycopy(second, iSecond, result, iMerged, second.length - iSecond);
    }

public static java.lang.Comparable eval( String targetId, java.lang.Comparable value, int expressionType){
logger.eval("MergeSortVisual", targetId, value, expressionType);
return value;
}
public static java.lang.Comparable write(String name, java.lang.Comparable value, int sourceType, int targetType ){
logger.write("MergeSortVisual", name, value, sourceType, targetType);
return value;
}
public static java.lang.Comparable[] eval( String targetId, java.lang.Comparable[] value, int expressionType){
logger.eval("MergeSortVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType);
return value;
}
public static java.lang.Comparable[] write(String name, java.lang.Comparable[] value, int sourceType, int targetType ){
logger.write("MergeSortVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static java.lang.Comparable[][] eval( String targetId, java.lang.Comparable[][] value, int expressionType){
logger.eval("MergeSortVisual", targetId, new com.dennisjonsson.log.ast.LogUtils<java.lang.Comparable[][]>().deepCopy(value), expressionType);
return value;
}
public static java.lang.Comparable[][] write(String name, java.lang.Comparable[][] value, int sourceType, int targetType ){
logger.write("MergeSortVisual", name, new com.dennisjonsson.log.ast.LogUtils<java.lang.Comparable[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index){ 
logger.read("MergeSortVisual", name ,index ,dimension);
return index; 
}
}
