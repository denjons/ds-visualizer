/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.app;

import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.annotation.markup.AbstractType;
import com.dennisjonsson.visualization.test.BubbleSort;
import com.dennisjonsson.visualization.test.HeapSort;
import com.dennisjonsson.visualization.test.MergeSort;
import com.dennisjonsson.visualization.test.QuickSort;
import com.github.javaparser.ast.Node;


public class Test1Visual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("Test1Visual",new String [] { "/*"," * To change this license header, choose License Headers in Project Properties."," * To change this template file, choose Tools | Templates"," * and open the template in the editor."," */","package com.dennisjonsson.visualization.test.app;","","import com.dennisjonsson.annotation.Print;","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.annotation.Visualize;","import com.dennisjonsson.annotation.markup.AbstractType;","import com.dennisjonsson.visualization.test.BubbleSort;","import com.dennisjonsson.visualization.test.HeapSort;","import com.dennisjonsson.visualization.test.MergeSort;","import com.dennisjonsson.visualization.test.QuickSort;","import com.github.javaparser.ast.Node;","","@VisualClass","public class Test1 {","","    /**","     * @param args the command line arguments","     */","    @Visualize(abstractType = AbstractType.ARRAY)","    public static int[] a = new int[10];","","    @Visualize(abstractType = AbstractType.ARRAY)","    public static int[] b = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };","","    public static int c[] = new int[10];","","    @Visualize(abstractType = AbstractType.ADJACENCY_MATRIX)","    public static int[][] e = new int[10][10];","","    public static void main(String[] args) {","        // TODO code application logic here","        Test1 test = new Test1();","        test.simpleTest();","        test.scope1(c);","        test.scope2(c);","        test.bubblesortFailedCaseTest(a);","        test.testDependencies();","        test.recursive(2);","    // Node d = c[1];","    }","","    public void testDependencies() {","        QuickSort.sort(new int[] { 8, 1, 5, 8, 9, 6 });","        MergeSort.sort(new Integer[] { 8, 1, 5, 8, 9, 6 });","        HeapSort h = new HeapSort();","        h.sort(new int[] { 8, 1, 5, 8, 9, 6 });","        BubbleSort.sort(new int[] { 8, 1, 5, 8, 9, 6 });","    }","","    public void simpleTest() {","        int d = a[1];","        a = b;","        a[1] = b[1];","        d = a[1];","        a[1] = 1;","        a[2] = d;","        d = c[1];","        c[1] = d;","        c[1] = c[0];","        d = d;","        a[0] = b[0];","        a[0] = 1;","        a[0] = b[0] + a[1];","        a[0] = b[0] + a[1] + b[1];","        e[a[0]][b[0]] = e[c[1]][b[2]];","    }","","    public void bubblesortFailedCaseTest(@Visualize(abstractType = 'array') int[] intArray) {","        int j = 2;","        int temp;","        if (intArray[j - 1] <= intArray[j]) {","            temp = intArray[j - 1];","            intArray[j - 1] = intArray[j];","            intArray[j] = temp;","        }","    }","","    public void scope1(@Visualize(abstractType = 'array') int[] b) {","        b[0] = 1;","        this.b[0] = 1;","        b[0] = 1;","        b[0] = this.b[0];","        this.b[0] = b[0];","    }","","    public void scope2(int[] b) {","        b[0] = 1;","        this.b[0] = 1;","        b[0] = 1;","        b[0] = this.b[0];","        this.b[0] = b[0];","    }","","    public int recursive(int i) {","        if (i == 0) {","            return 0;","        }","        if (e[0][i] > 0) {","        }","        e[0][i] = e[0][i];","        e[0][i] = recursive(i - 1) + a[i];","        return e[0][i];","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] {  com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","int[]","com.dennisjonsson.visualization.test.app.Test1 a"),com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","int[]","com.dennisjonsson.visualization.test.app.Test1 b"),com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("adjacencymatrix","int[][]","com.dennisjonsson.visualization.test.app.Test1 e"),com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","int[]","com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray"),com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","int[]","com.dennisjonsson.visualization.test.app.Test1 scope1 b")},new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:.Users.dennis.Documents.NetBeansProjects.annotation-test"));

    /**
     * @param args the command line arguments
     */
    
    public static int[] a = eval("com.dennisjonsson.visualization.test.app.Test1 a", write(null, new int[10], 3, 1), 0, new int[] { 32, 32 });

    
    public static int[] b = eval("com.dennisjonsson.visualization.test.app.Test1 b", write(null, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, 3, 1), 0, new int[] { 35, 35 });

    public static int c[] = new int[10];

    
    public static int[][] e = eval("com.dennisjonsson.visualization.test.app.Test1 e", write(null, new int[10][10], 3, 1), 0, new int[] { 40, 40 });

    public static void main(String[] args) {
        // TODO code application logic here
        Test1Visual test = new Test1Visual();
        test.simpleTest();
        test.scope1(eval("com.dennisjonsson.visualization.test.app.Test1 scope1 b", write(null, c, 3, 1), 3, new int[] { 49, 49 }));
        test.scope2(c);
        test.bubblesortFailedCaseTest(eval("com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray", write("com.dennisjonsson.visualization.test.app.Test1 a", a, 1, 1), 3, new int[] { 51, 51 }));
        test.testDependencies();
        test.recursive(2);
    // Node d = c[1];
    }

    public void testDependencies() {
        com.dennisjonsson.visualization.test.QuickSortVisual.sort(new int[] { 8, 1, 5, 8, 9, 6 });
        com.dennisjonsson.visualization.test.MergeSortVisual.sort(new Integer[] { 8, 1, 5, 8, 9, 6 });
        com.dennisjonsson.visualization.test.HeapSortVisual h = new com.dennisjonsson.visualization.test.HeapSortVisual();
        h.sort(new int[] { 8, 1, 5, 8, 9, 6 });
        com.dennisjonsson.visualization.test.BubbleSortVisual.sort(new int[] { 8, 1, 5, 8, 9, 6 });
    }

    public void simpleTest() {
        int d = eval("d", write("com.dennisjonsson.visualization.test.app.Test1 a", a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 1)], 0, 1), 0, new int[] { 71, 71 });
        eval("com.dennisjonsson.visualization.test.app.Test1 a", a = write("com.dennisjonsson.visualization.test.app.Test1 b", b, 1, 1), 0, new int[] { 72, 72 });
        eval("com.dennisjonsson.visualization.test.app.Test1 a", a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 1)] = write("com.dennisjonsson.visualization.test.app.Test1 b", b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 1)], 0, 0), 0, new int[] { 73, 73 });
        eval("d", d = write("com.dennisjonsson.visualization.test.app.Test1 a", a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 1)], 0, 1), 0, new int[] { 74, 74 });
        eval("com.dennisjonsson.visualization.test.app.Test1 a", a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 1)] = write(null, 1, 3, 0), 0, new int[] { 75, 75 });
        eval("com.dennisjonsson.visualization.test.app.Test1 a", a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 2)] = write("d", d, 1, 0), 0, new int[] { 76, 76 });
        d = c[1];
        c[1] = d;
        c[1] = c[0];
        d = d;
        eval("com.dennisjonsson.visualization.test.app.Test1 a", a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 0)] = write("com.dennisjonsson.visualization.test.app.Test1 b", b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 0)], 0, 0), 0, new int[] { 83, 83 });
        eval("com.dennisjonsson.visualization.test.app.Test1 a", a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 0)] = write(null, 1, 3, 0), 0, new int[] { 84, 84 });
        eval("com.dennisjonsson.visualization.test.app.Test1 a", a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 0)] = write(null, eval(null, b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 0)], 2, new int[] { 85, 85 }) + eval(null, a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 1)], 2, new int[] { 85, 85 }), 3, 0), 0, new int[] { 85, 85 });
        eval("com.dennisjonsson.visualization.test.app.Test1 a", a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 0)] = write(null, eval(null, b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 0)], 2, new int[] { 86, 86 }) + eval(null, a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 1)], 2, new int[] { 86, 86 }) + eval(null, b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 1)], 2, new int[] { 86, 86 }), 3, 0), 0, new int[] { 86, 86 });
        eval("com.dennisjonsson.visualization.test.app.Test1 e", e[read("com.dennisjonsson.visualization.test.app.Test1 e", 0, a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, 0)])][read("com.dennisjonsson.visualization.test.app.Test1 e", 1, b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 0)])] = write("com.dennisjonsson.visualization.test.app.Test1 e", e[read("com.dennisjonsson.visualization.test.app.Test1 e", 0, c[1])][read("com.dennisjonsson.visualization.test.app.Test1 e", 1, b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 2)])], 0, 0), 0, new int[] { 88, 88 });
    }

    public void bubblesortFailedCaseTest( int[] intArray) {
        int j = 2;
        int temp;
        if (eval(null, intArray[read("com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray", 0, j - 1)], 2, new int[] { 94, 94 }) <= eval(null, intArray[read("com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray", 0, j)], 2, new int[] { 94, 94 })) {
            eval("temp", temp = write("com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray", intArray[read("com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray", 0, j - 1)], 0, 1), 0, new int[] { 95, 95 });
            eval("com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray", intArray[read("com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray", 0, j - 1)] = write("com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray", intArray[read("com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray", 0, j)], 0, 0), 0, new int[] { 96, 96 });
            eval("com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray", intArray[read("com.dennisjonsson.visualization.test.app.Test1 bubblesortFailedCaseTest intArray", 0, j)] = write("temp", temp, 1, 0), 0, new int[] { 97, 97 });
        }
    }

    public void scope1( int[] b) {
        eval("com.dennisjonsson.visualization.test.app.Test1 scope1 b", b[read("com.dennisjonsson.visualization.test.app.Test1 scope1 b", 0, 0)] = write(null, 1, 3, 0), 0, new int[] { 103, 103 });
        eval("com.dennisjonsson.visualization.test.app.Test1 b", this.b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 0)] = write(null, 1, 3, 0), 0, new int[] { 104, 104 });
        eval("com.dennisjonsson.visualization.test.app.Test1 scope1 b", b[read("com.dennisjonsson.visualization.test.app.Test1 scope1 b", 0, 0)] = write(null, 1, 3, 0), 0, new int[] { 105, 105 });
        eval("com.dennisjonsson.visualization.test.app.Test1 scope1 b", b[read("com.dennisjonsson.visualization.test.app.Test1 scope1 b", 0, 0)] = write("com.dennisjonsson.visualization.test.app.Test1 b", this.b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 0)], 0, 0), 0, new int[] { 106, 106 });
        eval("com.dennisjonsson.visualization.test.app.Test1 b", this.b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 0)] = write("com.dennisjonsson.visualization.test.app.Test1 scope1 b", b[read("com.dennisjonsson.visualization.test.app.Test1 scope1 b", 0, 0)], 0, 0), 0, new int[] { 107, 107 });
    }

    public void scope2(int[] b) {
        b[0] = 1;
        eval("com.dennisjonsson.visualization.test.app.Test1 b", this.b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 0)] = write(null, 1, 3, 0), 0, new int[] { 112, 112 });
        b[0] = 1;
        eval("b[0]", b[0] = write("com.dennisjonsson.visualization.test.app.Test1 b", this.b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 0)], 0, 3), 0, new int[] { 114, 114 });
        eval("com.dennisjonsson.visualization.test.app.Test1 b", this.b[read("com.dennisjonsson.visualization.test.app.Test1 b", 0, 0)] = write(null, b[0], 3, 0), 0, new int[] { 115, 115 });
    }

    public int recursive(int i) {
        if (i == 0) {
            return 0;
        }
        if (eval(null, e[read("com.dennisjonsson.visualization.test.app.Test1 e", 0, 0)], 2, new int[] { 123, 123 })[i] > 0) {
        }
        eval("com.dennisjonsson.visualization.test.app.Test1 e", e[read("com.dennisjonsson.visualization.test.app.Test1 e", 0, 0)][read("com.dennisjonsson.visualization.test.app.Test1 e", 1, i)] = write("com.dennisjonsson.visualization.test.app.Test1 e", e[read("com.dennisjonsson.visualization.test.app.Test1 e", 0, 0)][read("com.dennisjonsson.visualization.test.app.Test1 e", 1, i)], 0, 0), 0, new int[] { 127, 127 });
        eval("com.dennisjonsson.visualization.test.app.Test1 e", e[read("com.dennisjonsson.visualization.test.app.Test1 e", 0, 0)][read("com.dennisjonsson.visualization.test.app.Test1 e", 1, i)] = write(null, recursive(i - 1) + eval(null, a[read("com.dennisjonsson.visualization.test.app.Test1 a", 0, i)], 2, new int[] { 128, 128 }), 3, 0), 0, new int[] { 128, 128 });
        return eval(null, e[read("com.dennisjonsson.visualization.test.app.Test1 e", 0, 0)][read("com.dennisjonsson.visualization.test.app.Test1 e", 1, i)], 2, new int[] { 130, 130 });
    }

public static int eval( String targetId, int value, int expressionType, int [] line){
logger.eval("Test1Visual", targetId, value, expressionType, line);
return value;
}
public static int write(String name, int value, int sourceType, int targetType ){
logger.write("Test1Visual", name, value, sourceType, targetType);
return value;
}
public static int[] eval( String targetId, int[] value, int expressionType, int [] line){
logger.eval("Test1Visual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType, line);
return value;
}
public static int[] write(String name, int[] value, int sourceType, int targetType ){
logger.write("Test1Visual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static int[][] eval( String targetId, int[][] value, int expressionType, int [] line){
logger.eval("Test1Visual", targetId, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), expressionType, line);
return value;
}
public static int[][] write(String name, int[][] value, int sourceType, int targetType ){
logger.write("Test1Visual", name, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index ){ 
logger.read("Test1Visual", name ,index ,dimension);
return index; 
}
}
