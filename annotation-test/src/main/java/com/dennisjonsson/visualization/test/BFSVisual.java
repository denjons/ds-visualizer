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


public class BFSVisual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("BFSVisual",new String [] { "/*"," * To change this license header, choose License Headers in Project Properties."," * To change this template file, choose Tools | Templates"," * and open the template in the editor."," */","package com.dennisjonsson.visualization.test;","","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.annotation.markup.AbstractType;","import com.dennisjonsson.annotation.Visualize;","import java.util.ArrayList;","","@VisualClass","public class BFS {","","    // this is a comment yo","    public void bfs(@Visualize(abstractType = 'matrix') int[][] adjList, int start) {","        int size = adjList.length;","        boolean[] marked = new boolean[size];","        for (int k = 0; k < adjList.length; k++) {","            for (int i = size - 1 - k; i < size - (k / 2); i++) {","                adjList[k][i] = 1;","            }","            marked[k] = false;","        }","        ArrayList<Integer> left = new ArrayList<Integer>();","        left.add(start);","        int i = 0;","        while (i < left.size()) {","            marked[left.get(i)] = true;","            for (int j = 0; j < adjList[left.get(i)].length; j++) {","                if (!marked[j] && adjList[left.get(i)][j] == 1) {","                    left.add(j);","                    marked[j] = true;","                }","            }","            i++;","        }","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] {  com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("matrix","int[][]","com.dennisjonsson.visualization.test.BFS bfs adjList")},new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:/Users/dennis/Documents/NetBeansProjects/annotation-test"));

    // this is a comment yo
    public void bfs( int[][] adjList, int start) {
        int size = adjList.length;
        boolean[] marked = new boolean[size];
        for (int k = 0; k < adjList.length; k++) {
            for (int i = size - 1 - k; i < size - (k / 2); i++) {
                eval("com.dennisjonsson.visualization.test.BFS bfs adjList", adjList[read("com.dennisjonsson.visualization.test.BFS bfs adjList", 0, k)][read("com.dennisjonsson.visualization.test.BFS bfs adjList", 1, i)] = write(null, 1, 3, 0), 0, new int[] { 26, 26 });
            }
            marked[k] = false;
        }
        ArrayList<Integer> left = new ArrayList<Integer>();
        left.add(start);
        int i = 0;
        while (i < left.size()) {
            marked[left.get(i)] = true;
            for (int j = 0; j < eval(null, adjList[read("com.dennisjonsson.visualization.test.BFS bfs adjList", 0, left.get(i))], 2, new int[] { 35, 35 }).length; j++) {
                if (!marked[j] && eval(null, adjList[read("com.dennisjonsson.visualization.test.BFS bfs adjList", 0, left.get(i))], 2, new int[] { 36, 36 })[j] == 1) {
                    left.add(j);
                    marked[j] = true;
                }
            }
            i++;
        }
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
public static int[][][] eval( String targetId, int[][][] value, int expressionType, int [] line){
logger.eval("BFSVisual", targetId, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][][]>().deepCopy(value), expressionType, line);
return value;
}
public static int[][][] write(String name, int[][][] value, int sourceType, int targetType ){
logger.write("BFSVisual", name, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index ){ 
logger.read("BFSVisual", name ,index ,dimension);
return index; 
}
}
