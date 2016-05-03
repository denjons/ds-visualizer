/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.app;

import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.visualization.test.QuickSort;

/**
 *
 * @author dennis
 */

public class NewMainVisual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("NewMainVisual",new String [] { "/*"," * To change this license header, choose License Headers in Project Properties."," * To change this template file, choose Tools | Templates"," * and open the template in the editor."," */","package com.dennisjonsson.visualization.test.app;","","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.visualization.test.QuickSort;","","/**"," *"," * @author dennis"," */","@VisualClass","public class NewMain {","","    /**","     * @param args the command line arguments","     */","    public static void main(String[] args) {","        QuickSort.sort(new int[] { 0, 4, 5, 8, 9, 7, 5, 6, 8, 5, 12, 41, 0, 8, 9, 2 });","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] { },new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:/Users/dennis/Documents/NetBeansProjects/annotation-test"));

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        com.dennisjonsson.visualization.test.QuickSortVisual.sort(new int[] { 0, 4, 5, 8, 9, 7, 5, 6, 8, 5, 12, 41, 0, 8, 9, 2 });
    }
public static int read(String name,int dimension, int index ){ 
logger.read("NewMainVisual", name ,index ,dimension);
return index; 
}
}
