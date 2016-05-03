/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;
import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.Visualize;


@VisualClass
class BinarySearch 
{
    
 
    public static int binarySearch(@Visualize(abstractType="array")int [] array, int search){
        
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