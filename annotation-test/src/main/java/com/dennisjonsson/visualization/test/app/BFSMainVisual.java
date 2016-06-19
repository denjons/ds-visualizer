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

public class BFSMainVisual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("BFSMainVisual",new String [] { "/*"," * To change this license header, choose License Headers in Project Properties."," * To change this template file, choose Tools | Templates"," * and open the template in the editor."," */","package com.dennisjonsson.visualization.test.app;","","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.visualization.test.BFS;","","/**"," *"," * @author dennis"," */","@VisualClass","public class BFSMain {","","    /**","     * @param args the command line arguments","     */","    static final int size = 10;","","    public static int[][] ls = new int[size][size];","","    public static void main(String[] args) {","        for (int i = 0; i < size; i++) {","            for (int j = 0; j < size / 2; j++) {","                ls[i][(int) Math.random() * size] = 1;","            }","        }","        BFS bfs = new BFS();","        bfs.bfs(ls, 0);","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] { },new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:.Users.dennis.Documents.NetBeansProjects.annotation-test"));

    /**
     * @param args the command line arguments
     */
    static final int size = 10;

    public static int[][] ls = new int[size][size];

    public static void main(String[] args) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size / 2; j++) {
                ls[i][(int) Math.random() * size] = 1;
            }
        }
        com.dennisjonsson.visualization.test.BFSVisual bfs = new com.dennisjonsson.visualization.test.BFSVisual();
        bfs.bfs(ls, 0);
    }
public static int read(String name,int dimension, int index ){ 
logger.read("BFSMainVisual", name ,index ,dimension);
return index; 
}
}
