/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.markup.AbstractType;
import com.dennisjonsson.annotation.Visualize;
import java.util.ArrayList;
import com.dennisjonsson.annotation.SourcePath;


public class BFSTestArrayVisual{
public static com.dennisjonsson.log.ast.ASTLogger logger = 
com.dennisjonsson.log.ast.ASTLogger.instance(
new com.dennisjonsson.log.ast.SourceHeader(
"BFSTestArrayVisual",
null,
new com.dennisjonsson.markup.DataStructure [] { },
com.dennisjonsson.log.DefaultInterpreter.instance()));

    // this is a comment yo
    public void bfs(int[][] adjList, int start) {
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
public static int read(String name,int dimension, int index){ 
logger.read("BFSTestArrayVisual", name ,index ,dimension);
return index; 
}
}
