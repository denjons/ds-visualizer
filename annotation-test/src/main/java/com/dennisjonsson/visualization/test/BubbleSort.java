/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.annotation.VisualizeArg;
import com.dennisjonsson.markup.AbstractType;

@SourcePath(path = "C:/Users/dennis/Documents/NetBeansProjects/" 
        + "annotation-test/src/main/" 
        + "java/com/dennisjonsson/visualization/test/")
public class BubbleSort {
 
        @VisualizeArg(args={AbstractType.ARRAY} )
        public static void sort(int intArray[]) {
 
                int n = intArray.length;
                int temp = 0;
               
                for(int i=0; i < n; i++){
                        for(int j=1; j < (n-i); j++){
                               
                                if(intArray[j-1] > intArray[j]){
                                        //swap the elements!
                                        temp = intArray[j-1];
                                        intArray[j-1] = intArray[j];
                                        intArray[j] = temp;
                                }
                               
                        }
                }
       
        }
        
        @Print(path="")
        public static void print(){}
}