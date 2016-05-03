/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.app;

import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.visualization.test.HeapSort;

/**
 *
 * @author dennis
 */

public class HeapMainVisual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("HeapMainVisual",new String [] { "/*"," * To change this license header, choose License Headers in Project Properties."," * To change this template file, choose Tools | Templates"," * and open the template in the editor."," */","package com.dennisjonsson.visualization.test.app;","","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.visualization.test.HeapSort;","","/**"," *"," * @author dennis"," */","@VisualClass","public class HeapMain {","","    /**","     * @param args the command line arguments","     */","    public static void main(String[] args) {","        HeapSort hs = new HeapSort();","        hs.sort(new int[] { 5, 4, 1, 2, 3, 69, 8, 7, 4, 2, 5, 4, 10 });","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] { },new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:/Users/dennis/Documents/NetBeansProjects/annotation-test"));

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        com.dennisjonsson.visualization.test.HeapSortVisual hs = new com.dennisjonsson.visualization.test.HeapSortVisual();
        hs.sort(new int[] { 5, 4, 1, 2, 3, 69, 8, 7, 4, 2, 5, 4, 10 });
    }
public static int read(String name,int dimension, int index ){ 
logger.read("HeapMainVisual", name ,index ,dimension);
return index; 
}
}
