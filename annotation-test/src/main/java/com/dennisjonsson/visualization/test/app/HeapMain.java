/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.app;

import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.visualization.test.HeapSort;

/**
 *
 * @author dennis
 */
@VisualClass
public class HeapMain {

   
    public static void main(String[] args) {
        HeapSort hs = new HeapSort();
        
        hs.sort(new int [] {5,4,1,2,3,69,8,7,4,2,5,4,10});
    }
    
}
