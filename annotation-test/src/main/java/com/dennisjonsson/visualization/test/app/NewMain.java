/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.app;

import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.visualization.test.QuickSort;

/**
 *
 * @author dennis
 */
@VisualClass
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        QuickSort.sort(new int [] {0,4,5,8,9,7,5,6,8,5,12,41,0,8,9,2});
    }
    
}
