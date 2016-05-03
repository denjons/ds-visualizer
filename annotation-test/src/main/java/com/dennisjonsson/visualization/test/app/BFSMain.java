/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.app;

import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.visualization.test.BFS;

/**
 *
 * @author dennis
 */
@VisualClass
public class BFSMain {

    /**
     * @param args the command line arguments
     */
    static final int size = 10;
    public static int[][] ls = new int[size][size];
    
    public static void main(String[] args) {
        
        for(int i = 0; i < size; i ++){
            for(int j = 0; j < size/2; j++){
                ls[i][(int)Math.random()*size] = 1;
            }
        }
        BFS bfs = new BFS();
        bfs.bfs(ls, 0);
    }
    
}
