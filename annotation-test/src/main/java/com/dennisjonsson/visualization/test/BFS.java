/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;


import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.markup.AbstractType;
import com.dennisjonsson.annotation.Visualize;
import java.util.ArrayList;


@VisualClass
public class BFS {
    
    // this is a comment yo
    public void bfs(@Visualize(abstractType="matrix")int[][] adjList,  int start) {
        
        int size = adjList.length;
        
        boolean[] marked = new boolean[size];
        for (int k = 0; k < adjList.length; k++) {
            for (int i = size - 1 - k; i < size - (k / 2); i++) {
                adjList[k][i] = 1;
            }
            marked[k] = false;
        }
        ArrayList<Integer> left = new ArrayList<Integer>();
        left.add(start);
        int i = 0;
        while (i < left.size()) {
            marked[left.get(i)] = true;
            for (int j = 0; j < adjList[left.get(i)].length; j++) {
                if (!marked[j] && adjList[left.get(i)][j] == 1) {
                    left.add(j);
                    marked[j] = true;
                }
            }
            i++;
        }
    }

}
