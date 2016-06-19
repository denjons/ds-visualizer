
package com.dennisjonsson.visualization.test;


import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.Visualize;


@VisualClass
public class MysterySort {
   
    @Visualize(abstractType = "array")
    public static int [] array = new int [] {9,5,1,4,6,8,5,2,1,4,10,1,2,8};

    
    public static void main(String[] args) {
         int [] res = mysterySort(array);
         
         Object o = new Object();
         array = res;
    }
    
    public static int []  mysterySort(@Visualize(abstractType="array") int[] array) {
       
        int k = 0;
        for (int i = 0; i < array.length/2; i++) {
           
            boolean scrappy = false;
            for (int j = i; j < array.length - i - 1; j++) {
                if (array[j] < array[j+1]) {
                    int b = array[j];
                    array[j] = array[j+1];
                    array[j+1] = b;
                    scrappy = true;
                }
            }
            k = i + 2;
            for (int j = array.length - 2 - i; j > i; j--) {
                if (array[j] > array[j-1]) {
                    int tmp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = tmp;
                    scrappy = true;
                }
            }
            k--;
            if(!scrappy) break;
        }
        return array;
    }  
}
