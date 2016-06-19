/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;


import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.markup.AbstractType;
import com.dennisjonsson.annotation.Visualize;
import static com.dennisjonsson.visualization.test.app.BFSMain.ls;
import java.util.ArrayList;


@VisualClass
public class BFS {
    
    @Visualize(abstractType = "array")
    int [] path = new int[]{1,8,6,4,7,9,5,2,3};
    // this is a comment yo
    public void bfs(@Visualize(abstractType="adjacencymatrix")int[][] adjList,  int start) {
        
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
            int k = path[5];
            i++;
        }
    }
    
    public static void main(String [] args){
        int size = 7;
        int [][] ls = new int[size][size];
        for(int i = 0; i < size; i ++){
            for(int j = 0; j < size/2; j++){
                ls[i][(int)Math.random()*size] = 1;
            }
        }
        BFS b = new BFS();
        b.bfs(ls, 0);
    }

}
