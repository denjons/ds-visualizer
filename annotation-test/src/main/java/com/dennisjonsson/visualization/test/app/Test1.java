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

/**
 *
 * @author dennis
 */

@VisualClass
public class Test1 {

    /**
     * @param args the command line arguments
     */
    
   
    @Visualize(abstractType = AbstractType.ARRAY)
    public static int [] a = new int[10];
    
    @Visualize(abstractType = AbstractType.ARRAY)
    public static int [] b = new int[]{1,2,3,4,5,6,7,8,9};
    
    public static int c [] = new int [10];
   
    @Visualize(abstractType = AbstractType.ADJACENCY_MATRIX)
    public static int [][] e = new int [10][10];
    
    public static void main(String[] args) {
        // TODO code application logic here
      
        
        
        Test1 test = new Test1();
        test.simpleTest();
        test.scope1(c);
        test.scope2(c);
        test.bubblesortFailedCaseTest(a);
        test.testDependencies();
        test.recursive(2);
        
        
        
       
       // Node d = c[1];
    
    }
    
    public void testDependencies(){
        QuickSort.sort(new int [] {8,1,5,8,9,6});
        MergeSort.sort(new Integer [] {8,1,5,8,9,6});
        HeapSort h = new HeapSort();
        h.sort(new int [] {8,1,5,8,9,6});
        BubbleSort.sort(new int [] {8,1,5,8,9,6});
    }
    
    public void simpleTest(){
        int d = a[1];
        a = b; 
        a[1] = b[1];
        d = a[1];
        a[1] = 1;
        a[2] = d;
        
        d = c[1];
        c[1] = d;
        c[1] = c[0];
        d = d;
        
        a[0] = b[0];
        a[0] = 1;
        a[0] = b[0] + a[1];
        a[0] = b[0] + a[1] + b[1];
        
        e[a[0]][b[0]] = e[c[1]][b[2]];
    }
    
    public void bubblesortFailedCaseTest(@Visualize(abstractType = "array")int [] intArray){
        int j = 2;
        int temp;
        if(intArray[j-1] <= intArray[j]){
            temp = intArray[j-1];
            intArray[j-1] = intArray[j];
            intArray[j] = temp;
        }
        
    }
    
    public void scope1(@Visualize(abstractType="array")int [] b){
           b[0] = 1;
           this.b[0] = 1;
           b[0] = 1;
           b[0] = this.b[0];
           this.b[0] = b[0];
    }
    
     public void scope2(int [] b){
           b[0] = 1;
           this.b[0] = 1;
           b[0] = 1;
           b[0] = this.b[0];
           this.b[0] = b[0];
    }
     
     public int recursive(int i){
        if(i==0){
            return 0;
        }
        
        if(e[0][i] > 0){
        
        }
        
        e[0][i] = e[0][i];
        e[0][i] = recursive(i -1) + a[i]; 

        return e[0][i];
    }
   
    
    
    
}
