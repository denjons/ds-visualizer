/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.annotation.markup.AbstractType;
import java.util.Arrays;


public class HeapSortVisual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("HeapSortVisual",new String [] { "/*"," * To change this license header, choose License Headers in Project Properties."," * To change this template file, choose Tools | Templates"," * and open the template in the editor."," */","package com.dennisjonsson.visualization.test;","","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.annotation.Visualize;","import com.dennisjonsson.annotation.markup.AbstractType;","import java.util.Arrays;","","@VisualClass","public class HeapSort {","","    @Visualize(abstractType = 'binarytree')","    private static int[] a;","","    private static int n;","","    private static int left;","","    private static int right;","","    private static int largest;","","    public void buildheap(int[] a) {","        n = a.length - 1;","        for (int i = n / 2; i >= 0; i--) {","            maxheap(a, i);","        }","    }","","    public void maxheap(int[] a, int i) {","        left = 2 * i;","        right = 2 * i + 1;","        if (left <= n && a[left] > a[i]) {","            largest = left;","        } else {","            largest = i;","        }","        if (right <= n && a[right] > a[largest]) {","            largest = right;","        }","        if (largest != i) {","            exchange(i, largest);","            maxheap(a, largest);","        }","    }","","    public void exchange(int i, int j) {","        int t = a[i];","        a[i] = a[j];","        a[j] = t;","    }","","    public void sort(int[] myarray) {","        a = myarray;","        buildheap(a);","        for (int i = n; i > 0; i--) {","            exchange(0, i);","            n = n - 1;","            maxheap(a, 0);","        }","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] {  com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("binarytree","int[]","com.dennisjonsson.visualization.test.HeapSort a")},new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:/Users/dennis/Documents/NetBeansProjects/annotation-test"));

    
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
        if (left <= n && a[left] > a[i]) {
            largest = left;
        } else {
            largest = i;
        }
        if (right <= n && a[right] > a[largest]) {
            largest = right;
        }
        if (largest != i) {
            exchange(i, largest);
            maxheap(a, largest);
        }
    }

    public void exchange(int i, int j) {
        int t = eval("t", write("com.dennisjonsson.visualization.test.HeapSort a", a[read("com.dennisjonsson.visualization.test.HeapSort a", 0, i)], 0, 1), 0, new int[] { 52, 52 });
        eval("com.dennisjonsson.visualization.test.HeapSort a", a[read("com.dennisjonsson.visualization.test.HeapSort a", 0, i)] = write("com.dennisjonsson.visualization.test.HeapSort a", a[read("com.dennisjonsson.visualization.test.HeapSort a", 0, j)], 0, 0), 0, new int[] { 53, 53 });
        eval("com.dennisjonsson.visualization.test.HeapSort a", a[read("com.dennisjonsson.visualization.test.HeapSort a", 0, j)] = write("t", t, 1, 0), 0, new int[] { 54, 54 });
    }

    public void sort(int[] myarray) {
        eval("com.dennisjonsson.visualization.test.HeapSort a", a = write("myarray", myarray, 1, 1), 0, new int[] { 58, 58 });
        buildheap(a);
        for (int i = n; i > 0; i--) {
            exchange(0, i);
            n = n - 1;
            maxheap(a, 0);
        }
    }

public static int eval( String targetId, int value, int expressionType, int [] line){
logger.eval("HeapSortVisual", targetId, value, expressionType, line);
return value;
}
public static int write(String name, int value, int sourceType, int targetType ){
logger.write("HeapSortVisual", name, value, sourceType, targetType);
return value;
}
public static int[] eval( String targetId, int[] value, int expressionType, int [] line){
logger.eval("HeapSortVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType, line);
return value;
}
public static int[] write(String name, int[] value, int sourceType, int targetType ){
logger.write("HeapSortVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static int[][] eval( String targetId, int[][] value, int expressionType, int [] line){
logger.eval("HeapSortVisual", targetId, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), expressionType, line);
return value;
}
public static int[][] write(String name, int[][] value, int sourceType, int targetType ){
logger.write("HeapSortVisual", name, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index ){ 
logger.read("HeapSortVisual", name ,index ,dimension);
return index; 
}
}
