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

@VisualClassPath(path="C:/Users/dennis/Documents/NetBeansProjects/" + "annotation-test/src/main/" + "java/com/dennisjonsson/visualization/test/")
public class TestParse {

    /**
     * @param args the command line arguments
     */
    
    
    @Visualize(type=AbstractType.ARRAY)
    public static int[] b = new int[10];
    @Visualize(type=AbstractType.ARRAY)
    public static int[][] c = new int[10][10];
    @Visualize(type=AbstractType.ARRAY)
    public static int[][][] d = new int[10][10][10];
    
    public static int [] un = new int[10];
    
    public static void main(String[] args) {
       
        if(b[0] == 1 /*&& a[0][1] < 2*/){
        
        }
        b[0] = b[1];
        
        if(b[0] == 1 /*&& a[0][1] < 2*/){
        
        }
        
        c[1][2] = b[0];
        int j = 0;
        int k = b[0];
        j = k;
        k = 1;
        c[1][2] = 1;
        b[0]++;
        c[1][0] = d[1][2][3];
        for(int i = 0; i < c[0].length; i++){}
        
        //b[1] = k;
        
       /*end visualize*/
    }
    
}
