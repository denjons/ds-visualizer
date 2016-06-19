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


public class BFSVisual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("BFSVisual",new String [] { "/*"," * To change this license header, choose License Headers in Project Properties."," * To change this template file, choose Tools | Templates"," * and open the template in the editor."," */","package com.dennisjonsson.visualization.test;","","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.annotation.markup.AbstractType;","import com.dennisjonsson.annotation.Visualize;","import static com.dennisjonsson.visualization.test.app.BFSMain.ls;","import java.util.ArrayList;","","@VisualClass","public class BFS {","","    @Visualize(abstractType = 'array')","    int[] path = new int[] { 1, 8, 6, 4, 7, 9, 5, 2, 3 };","","    // this is a comment yo","    public void bfs(@Visualize(abstractType = 'adjacencymatrix') int[][] adjList, int start) {","        int size = adjList.length;","        boolean[] marked = new boolean[size];","        for (int k = 0; k < adjList.length; k++) {","            for (int i = size - 1 - k; i < size - (k / 2); i++) {","                adjList[k][i] = 1;","            }","            marked[k] = false;","        }","        ArrayList<Integer> left = new ArrayList<Integer>();","        left.add(start);","        int i = 0;","        while (i < left.size()) {","            marked[left.get(i)] = true;","            for (int j = 0; j < adjList[left.get(i)].length; j++) {","                if (!marked[j] && adjList[left.get(i)][j] == 1) {","                    left.add(j);","                    marked[j] = true;","                }","            }","            int k = path[5];","            i++;","        }","    }","","    public static void main(String[] args) {","        int size = 7;","        int[][] ls = new int[size][size];","        for (int i = 0; i < size; i++) {","            for (int j = 0; j < size / 2; j++) {","                ls[i][(int) Math.random() * size] = 1;","            }","        }","        BFS b = new BFS();","        b.bfs(ls, 0);","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] {  com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","int[]","com.dennisjonsson.visualization.test.BFS path"),com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("adjacencymatrix","int[][]","com.dennisjonsson.visualization.test.BFS bfs adjList")},new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:.Users.dennis.Documents.NetBeansProjects.annotation-test"));

    
    int[] path = eval("com.dennisjonsson.visualization.test.BFS path", write(null, new int[] { 1, 8, 6, 4, 7, 9, 5, 2, 3 }, 3, 1), 0, new int[] { 20, 20 });

    // this is a comment yo
    public void bfs( int[][] adjList, int start) {
        int size = adjList.length;
        boolean[] marked = new boolean[size];
        for (int k = 0; k < adjList.length; k++) {
            for (int i = size - 1 - k; i < size - (k / 2); i++) {
                eval("com.dennisjonsson.visualization.test.BFS bfs adjList", adjList[read("com.dennisjonsson.visualization.test.BFS bfs adjList", 0, k)][read("com.dennisjonsson.visualization.test.BFS bfs adjList", 1, i)] = write(null, 1, 3, 0), 0, new int[] { 29, 29 });
            }
            marked[k] = false;
        }
        ArrayList<Integer> left = new ArrayList<Integer>();
        left.add(start);
        int i = 0;
        while (i < left.size()) {
            marked[left.get(i)] = true;
            for (int j = 0; j < eval(null, adjList[read("com.dennisjonsson.visualization.test.BFS bfs adjList", 0, left.get(i))], 2, new int[] { 38, 38 }).length; j++) {
                if (!marked[j] && eval(null, adjList[read("com.dennisjonsson.visualization.test.BFS bfs adjList", 0, left.get(i))], 2, new int[] { 39, 39 })[j] == 1) {
                    left.add(j);
                    marked[j] = true;
                }
            }
            int k = eval("k", write("com.dennisjonsson.visualization.test.BFS path", path[read("com.dennisjonsson.visualization.test.BFS path", 0, 5)], 0, 1), 0, new int[] { 44, 44 });
            i++;
        }
    }

    public static void main(String[] args) {
        int size = 7;
        int[][] ls = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size / 2; j++) {
                ls[i][(int) Math.random() * size] = 1;
            }
        }
        BFSVisual b = new BFSVisual();
        b.bfs(eval("com.dennisjonsson.visualization.test.BFS bfs adjList", write(null, ls, 3, 1), 3, new int[] { 58, 58 }), 0);
    }

public static int eval( String targetId, int value, int expressionType, int [] line){
logger.eval("BFSVisual", targetId, value, expressionType, line);
return value;
}
public static int write(String name, int value, int sourceType, int targetType ){
logger.write("BFSVisual", name, value, sourceType, targetType);
return value;
}
public static int[] eval( String targetId, int[] value, int expressionType, int [] line){
logger.eval("BFSVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType, line);
return value;
}
public static int[] write(String name, int[] value, int sourceType, int targetType ){
logger.write("BFSVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static int[][] eval( String targetId, int[][] value, int expressionType, int [] line){
logger.eval("BFSVisual", targetId, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), expressionType, line);
return value;
}
public static int[][] write(String name, int[][] value, int sourceType, int targetType ){
logger.write("BFSVisual", name, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index ){ 
logger.read("BFSVisual", name ,index ,dimension);
return index; 
}
}
