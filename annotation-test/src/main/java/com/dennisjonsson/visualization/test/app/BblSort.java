
package com.dennisjonsson.visualization.test.app;


import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.visualization.test.BubbleSort;

@VisualClass
public class BblSort {
    
    public static void main(String [] args){
        BubbleSort.sort(new int [] {14,51,21,61,21,14,12,56,58,47,14,12,25,25,26,23,21,24,27,45,48,46,29,58,39,38,37,1,5,6});
    }
    

}
