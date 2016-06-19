/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.Visualize;


public class MergeSortVisual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("MergeSortVisual",new String [] { "/*"," * To change this license header, choose License Headers in Project Properties."," * To change this template file, choose Tools | Templates"," * and open the template in the editor."," */","package com.dennisjonsson.visualization.test;","","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.annotation.Visualize;","","@VisualClass","public class MergeSort {","","    public static Comparable[] sort(@Visualize(abstractType = 'array') Comparable[] list) {","        //If list is empty; no need to do anything","        if (list.length <= 1) {","            return list;","        }","        //Split the array in half in two parts","        Comparable[] first = new Comparable[list.length / 2];","        Comparable[] second = new Comparable[list.length - first.length];","        System.arraycopy(list, 0, first, 0, first.length);","        System.arraycopy(list, first.length, second, 0, second.length);","        //Sort each half recursively","        sort(first);","        sort(second);","        //Merge both halves together, overwriting to original array","        merge(first, second, list);","        return list;","    }","","    private static void merge(@Visualize(abstractType = 'array') Comparable[] first, @Visualize(abstractType = 'array') Comparable[] second, @Visualize(abstractType = 'array') Comparable[] result) {","        //Index Position in first array - starting with first element","        int iFirst = 0;","        //Index Position in second array - starting with first element","        int iSecond = 0;","        //Index Position in merged array - starting with first position","        int iMerged = 0;","        //and move smaller element at iMerged","        while (iFirst < first.length && iSecond < second.length) {","            if (first[iFirst].compareTo(second[iSecond]) < 0) {","                result[iMerged] = first[iFirst];","                iFirst++;","            } else {","                result[iMerged] = second[iSecond];","                iSecond++;","            }","            iMerged++;","        }","        //copy remaining elements from both halves - each half will have already sorted elements","        System.arraycopy(first, iFirst, result, iMerged, first.length - iFirst);","        System.arraycopy(second, iSecond, result, iMerged, second.length - iSecond);","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] {  com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","java.lang.comparable[]","com.dennisjonsson.visualization.test.MergeSort sort list"),com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","java.lang.comparable[]","com.dennisjonsson.visualization.test.MergeSort merge first"),com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","java.lang.comparable[]","com.dennisjonsson.visualization.test.MergeSort merge second"),com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","java.lang.comparable[]","com.dennisjonsson.visualization.test.MergeSort merge result")},new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:.Users.dennis.Documents.NetBeansProjects.annotation-test"));

    public static Comparable[] sort( Comparable[] list) {
        //If list is empty; no need to do anything
        if (list.length <= 1) {
            return list;
        }
        //Split the array in half in two parts
        Comparable[] first = new Comparable[list.length / 2];
        Comparable[] second = new Comparable[list.length - first.length];
        System.arraycopy(list, 0, first, 0, first.length);
        System.arraycopy(list, first.length, second, 0, second.length);
        //Sort each half recursively
        sort(eval("com.dennisjonsson.visualization.test.MergeSort sort list", write(null, first, 3, 1), 3, new int[] { 31, 31 }));
        sort(eval("com.dennisjonsson.visualization.test.MergeSort sort list", write(null, second, 3, 1), 3, new int[] { 32, 32 }));
        //Merge both halves together, overwriting to original array
        merge(eval("com.dennisjonsson.visualization.test.MergeSort merge first", write(null, first, 3, 1), 3, new int[] { 35, 35 }), eval("com.dennisjonsson.visualization.test.MergeSort merge second", write(null, second, 3, 1), 3, new int[] { 35, 35 }), eval("com.dennisjonsson.visualization.test.MergeSort merge result", write("com.dennisjonsson.visualization.test.MergeSort sort list", list, 1, 1), 3, new int[] { 35, 35 }));
        return list;
    }

    private static void merge( Comparable[] first,  Comparable[] second,  Comparable[] result) {
        //Index Position in first array - starting with first element
        int iFirst = 0;
        //Index Position in second array - starting with first element
        int iSecond = 0;
        //Index Position in merged array - starting with first position
        int iMerged = 0;
        //and move smaller element at iMerged
        while (iFirst < first.length && iSecond < second.length) {
            if (eval(null, first[read("com.dennisjonsson.visualization.test.MergeSort merge first", 0, iFirst)], 2, new int[] { 56, 56 }).compareTo(eval(null, second[read("com.dennisjonsson.visualization.test.MergeSort merge second", 0, iSecond)], 2, new int[] { 56, 56 })) < 0) {
                eval("com.dennisjonsson.visualization.test.MergeSort merge result", result[read("com.dennisjonsson.visualization.test.MergeSort merge result", 0, iMerged)] = write("com.dennisjonsson.visualization.test.MergeSort merge first", first[read("com.dennisjonsson.visualization.test.MergeSort merge first", 0, iFirst)], 0, 0), 0, new int[] { 58, 58 });
                iFirst++;
            } else {
                eval("com.dennisjonsson.visualization.test.MergeSort merge result", result[read("com.dennisjonsson.visualization.test.MergeSort merge result", 0, iMerged)] = write("com.dennisjonsson.visualization.test.MergeSort merge second", second[read("com.dennisjonsson.visualization.test.MergeSort merge second", 0, iSecond)], 0, 0), 0, new int[] { 63, 63 });
                iSecond++;
            }
            iMerged++;
        }
        //copy remaining elements from both halves - each half will have already sorted elements
        System.arraycopy(first, iFirst, result, iMerged, first.length - iFirst);
        System.arraycopy(second, iSecond, result, iMerged, second.length - iSecond);
    }

public static java.lang.Comparable eval( String targetId, java.lang.Comparable value, int expressionType, int [] line){
logger.eval("MergeSortVisual", targetId, value, expressionType, line);
return value;
}
public static java.lang.Comparable write(String name, java.lang.Comparable value, int sourceType, int targetType ){
logger.write("MergeSortVisual", name, value, sourceType, targetType);
return value;
}
public static java.lang.Comparable[] eval( String targetId, java.lang.Comparable[] value, int expressionType, int [] line){
logger.eval("MergeSortVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType, line);
return value;
}
public static java.lang.Comparable[] write(String name, java.lang.Comparable[] value, int sourceType, int targetType ){
logger.write("MergeSortVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static java.lang.Comparable[][] eval( String targetId, java.lang.Comparable[][] value, int expressionType, int [] line){
logger.eval("MergeSortVisual", targetId, new com.dennisjonsson.annotation.log.ast.LogUtils<java.lang.Comparable[][]>().deepCopy(value), expressionType, line);
return value;
}
public static java.lang.Comparable[][] write(String name, java.lang.Comparable[][] value, int sourceType, int targetType ){
logger.write("MergeSortVisual", name, new com.dennisjonsson.annotation.log.ast.LogUtils<java.lang.Comparable[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index ){ 
logger.read("MergeSortVisual", name ,index ,dimension);
return index; 
}
}
