/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

import com.dennisjonsson.annotation.VisualClassPath;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.markup.AbstractType;

/**
 *
 * @author dennis
 */

public class BubbleSortVisualVisual{
public static com.dennisjonsson.log.Logger logger = 
new com.dennisjonsson.log.Logger(
new String [] {"ARRAY","int[]","intArray"});

    
    static int[] intArray = new int[] { 5, 90, 35, 45, 150, 3 };

    public static void main(String[] args) {
        //create an int array we want to sort using bubble sort algorithm
        //print array before sorting using bubble sort algorithm
        System.out.println("Array Before Bubble Sort");
        for (int i = 0; i < intArray.length; i++) {
            System.out.print(eval("5b56fbf7-fe57-4808-a41b-7aba3237afc3", intArray[read("intArray",
"5b56fbf7-fe57-4808-a41b-7aba3237afc3",
0,i)],0) + " ");
        }
        //sort an array using bubble sort algorithm
        bubbleSort(intArray);
        System.out.println("");
        //print array after sorting using bubble sort algorithm
        System.out.println("Array After Bubble Sort");
        for (int i = 0; i < intArray.length; i++) {
            System.out.print(eval("985006e6-4fd7-42e4-a240-cb23745e9666", intArray[read("intArray",
"985006e6-4fd7-42e4-a240-cb23745e9666",
0,i)],0) + " ");
        }
    }

    private static void bubbleSort(int[] intArray) {
        /*
                 * In bubble sort, we basically traverse the array from first
                 * to array_length - 1 position and compare the element with the next one.
                 * Element is swapped with the next element if the next element is greater.
                 *
                 * Bubble sort steps are as follows.
                 *
                 * 1. Compare array[0] & array[1]
                 * 2. If array[0] > array [1] swap it.
                 * 3. Compare array[1] & array[2]
                 * 4. If array[1] > array[2] swap it.
                 * ...
                 * 5. Compare array[n-1] & array[n]
                 * 6. if [n-1] > array[n] then swap it.
                 *
                 * After this step we will have largest element at the last index.
                 *
                 * Repeat the same steps for array[1] to array[n-1]
                 *  
                 */
        int n = intArray.length;
        int temp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (eval("b801f565-2554-407c-84f9-20222592c55e", intArray[read("intArray",
"b801f565-2554-407c-84f9-20222592c55e",
0,j - 1)],0) > eval("0e9e278b-2374-43fe-a23c-5f68b5c7d113", intArray[read("intArray",
"0e9e278b-2374-43fe-a23c-5f68b5c7d113",
0,j)],0)) {
                    //swap the elements!
                    temp = eval("284bedf5-01ba-4d03-a33a-510fc3931364", intArray[read("intArray",
"284bedf5-01ba-4d03-a33a-510fc3931364",
0,j - 1)],logger.endStatement());
                    eval("3c107cd7-0f79-4e5f-9a5e-c828b27780ac", intArray[read("intArray",
"3c107cd7-0f79-4e5f-9a5e-c828b27780ac",
0,j - 1)] = 
write("intArray", "3c107cd7-0f79-4e5f-9a5e-c828b27780ac",eval("3c107cd7-0f79-4e5f-9a5e-c828b27780ac", intArray[read("intArray",
"3c107cd7-0f79-4e5f-9a5e-c828b27780ac",
0,j)],0)),logger.endStatement());
                    eval("a72b56ae-4276-4fac-a495-e272168bc605", intArray[read("intArray",
"a72b56ae-4276-4fac-a495-e272168bc605",
0,j)] = 
write("intArray", "a72b56ae-4276-4fac-a495-e272168bc605",temp),logger.endStatement());
                }
            }
        }
    }
public static int read(String name,String statementId, int dimension, int index){ 
logger.loggRead(name, statementId ,index ,dimension);
return index; 
}
public static int write(String name, String statementId, int value){
logger.logWrite(name, statementId, value+"");
return value;
}
public static int eval(String statementId, int value, int statement){
logger.logEval(statementId, value+"");
return value;
}
public static String eval(String statementId, String value, int statement){
logger.logEval(statementId, value+"");
return value;
}
public static boolean eval(String statementId, boolean value, int statement){
logger.logEval(statementId, value+"");
return value;
}
public static char eval(String statementId, char value, int statement){
logger.logEval(statementId, value+"");
return value;
}
public static double eval(String statementId, double value, int statement){
logger.logEval(statementId, value+"");
return value;
}
public static float eval(String statementId, float value, int statement){
logger.logEval(statementId, value+"");
return value;
}

public static int[] eval(String statementId, int[] value, int statement){
logger.logEval(statementId, value+"");
return value;
}
public static int[][] eval(String statementId, int[][] value, int statement){
logger.logEval(statementId, value+"");
return value;
}
}
