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

public class BubbleSortVisual{
public static com.dennisjonsson.log.Logger logger = 
new com.dennisjonsson.log.Logger(
new String [] {"ARRAY","int[]","intArray"});
       
       static int[] intArray = new int[]{5,90,35,45,150,3};
 
        public static void main(String[] args) {
               
                //create an int array we want to sort using bubble sort algorithm
                
               
                //print array before sorting using bubble sort algorithm
                System.out.println("Array Before Bubble Sort");
                for(int i=0; i < intArray.length; i++){
                        System.out.print(eval("bb5f54d7-df0e-4ac3-ad99-b28d64913fde", intArray[read("intArray",
"bb5f54d7-df0e-4ac3-ad99-b28d64913fde",
0,i)],0) + " ");
                }
               
                //sort an array using bubble sort algorithm
                bubbleSort(intArray);
               
                System.out.println("");
               
                //print array after sorting using bubble sort algorithm
                System.out.println("Array After Bubble Sort");
                for(int i=0; i < intArray.length; i++){
                        System.out.print(eval("b1d9f037-0bff-432d-a9a6-144f6bfe59ff", intArray[read("intArray",
"b1d9f037-0bff-432d-a9a6-144f6bfe59ff",
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
               
                for(int i=0; i < n; i++){
                        for(int j=1; j < (n-i); j++){
                               
                                if(eval("dbb418d0-2585-470a-bab8-a1c81d1caf60", intArray[read("intArray",
"dbb418d0-2585-470a-bab8-a1c81d1caf60",
0,j-1)],0) > eval("920db808-b49d-4e67-95a8-d11c21454125", intArray[read("intArray",
"920db808-b49d-4e67-95a8-d11c21454125",
0,j)],0)){
                                        //swap the elements!
                                        temp = eval("d74087fe-e32d-4817-b454-846e843c9ea9", intArray[read("intArray",
"d74087fe-e32d-4817-b454-846e843c9ea9",
0,j-1)],logger.endStatement());
                                        eval("72b9e387-6263-4700-b776-4213ee7148fa", intArray[read("intArray",
"72b9e387-6263-4700-b776-4213ee7148fa",
0,j-1)] = 
write("intArray", "72b9e387-6263-4700-b776-4213ee7148fa",eval("72b9e387-6263-4700-b776-4213ee7148fa", intArray[read("intArray",
"72b9e387-6263-4700-b776-4213ee7148fa",
0,j)],0)),logger.endStatement());
                                        eval("cc3dfdbd-eef6-46ab-bfa8-92ed8ab8cd32", intArray[read("intArray",
"cc3dfdbd-eef6-46ab-bfa8-92ed8ab8cd32",
0,j)] = 
write("intArray", "cc3dfdbd-eef6-46ab-bfa8-92ed8ab8cd32",temp),logger.endStatement());
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
