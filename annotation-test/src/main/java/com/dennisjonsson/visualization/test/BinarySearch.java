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


@SourcePath(path = "C:/Users/dennis/Documents/NetBeansProjects/" 
        + "annotation-test/src/main/" 
        + "java/com/dennisjonsson/visualization/test/")
class BinarySearch 
{
    
 
    @VisualizeArg(args={"array"})
    public static int binarySearch(int [] array, int search){
        
        int  first, last, middle;
        
        first  = 0;
        last   = array.length - 1;
        middle = (first + last)/2;

        while( first <= last )
        {
          if ( array[middle] < search )
            first = middle + 1;    
          else if ( array[middle] == search ) 
          {
            return middle +1;
          }
          else
             last = middle - 1;

          middle = (first + last)/2;
        }
        if(first > last)
            return -1;
        else
            return first;
        
    }

}