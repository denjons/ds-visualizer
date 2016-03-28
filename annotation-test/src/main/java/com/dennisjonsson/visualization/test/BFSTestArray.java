/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

import com.dennisjonsson.annotation.TestVisualize;
import com.dennisjonsson.markup.AbstractType;
import com.dennisjonsson.annotation.VisualClassPath;
import com.dennisjonsson.annotation.Visualize;
import java.util.ArrayList;

@VisualClassPath(path = "C:/Users/dennis/Documents/NetBeansProjects/" + "annotation-test/src/main/" + "java/com/dennisjonsson/visualization/test/")
public class BFSTestArray {

    final int size = 20;

    @Visualize(type = AbstractType.ADJECENCY_MATRIX)
    int[][] adjList = new int[size][size];

    // this is a comment yo
    public void bfs(int start) {
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

    public static void main(String[] args) {
        BFSTestArray bfs = new BFSTestArray();
        bfs.bfs(0);
    /*end visualize*/
    }
}
