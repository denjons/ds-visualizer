/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.markup.AbstractType;
import java.util.Arrays;


public class HeapSortVisual{
public static com.dennisjonsson.log.ast.ASTLogger logger = 
com.dennisjonsson.log.ast.ASTLogger.instance(
new com.dennisjonsson.log.ast.SourceHeader(
"HeapSortVisual",
null,
new com.dennisjonsson.markup.DataStructure [] {  com.dennisjonsson.markup.DataStructureFactory.getDataStructure("binarytree","int[]","a")},
com.dennisjonsson.log.DefaultInterpreter.instance()));

    
    private static int[] a;

    private static int n;

    private static int left;

    private static int right;

    private static int largest;

    public void buildheap(int[] a) {
        n = a.length - 1;
        for (int i = n / 2; i >= 0; i--) {
            maxheap(a, i);
        }
    }

    public void maxheap(int[] a, int i) {
        left = 2 * i;
        right = 2 * i + 1;
        if (left <= n && eval(null, a[read("a", 0, left)], 2) > eval(null, a[read("a", 0, i)], 2)) {
            largest = left;
        } else {
            largest = i;
        }
        if (right <= n && eval(null, a[read("a", 0, right)], 2) > eval(null, a[read("a", 0, largest)], 2)) {
            largest = right;
        }
        if (largest != i) {
            exchange(i, largest);
            maxheap(a, largest);
        }
    }

    public void exchange(int i, int j) {
        int t = eval("t", write("a", a[read("a", 0, i)], 0, 1), 0);
        eval("a[i]", a[read("a", 0, i)] = write("a", a[read("a", 0, j)], 0, 0), 0);
        eval("a[j]", a[read("a", 0, j)] = write("t", t, 1, 0), 0);
    }

    public void sort(int[] myarray) {
        eval("a", a = write("myarray", myarray, 1, 1), 0);
        buildheap(a);
        for (int i = n; i > 0; i--) {
            exchange(0, i);
            n = n - 1;
            maxheap(a, 0);
        }
    }

public static int eval( String targetId, int value, int expressionType){
logger.eval("HeapSortVisual", targetId, value, expressionType);
return value;
}
public static int write(String name, int value, int sourceType, int targetType ){
logger.write("HeapSortVisual", name, value, sourceType, targetType);
return value;
}
public static int[] eval( String targetId, int[] value, int expressionType){
logger.eval("HeapSortVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType);
return value;
}
public static int[] write(String name, int[] value, int sourceType, int targetType ){
logger.write("HeapSortVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static int[][] eval( String targetId, int[][] value, int expressionType){
logger.eval("HeapSortVisual", targetId, new com.dennisjonsson.log.ast.LogUtils<int[][]>().deepCopy(value), expressionType);
return value;
}
public static int[][] write(String name, int[][] value, int sourceType, int targetType ){
logger.write("HeapSortVisual", name, new com.dennisjonsson.log.ast.LogUtils<int[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index){ 
logger.read("HeapSortVisual", name ,index ,dimension);
return index; 
}
}
