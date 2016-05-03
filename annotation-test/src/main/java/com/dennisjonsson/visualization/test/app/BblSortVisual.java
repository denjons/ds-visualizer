package com.dennisjonsson.visualization.test.app;

import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.visualization.test.BubbleSort;


public class BblSortVisual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("BblSortVisual",new String [] { "package com.dennisjonsson.visualization.test.app;","","import com.dennisjonsson.annotation.Print;","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.visualization.test.BubbleSort;","","@VisualClass","public class BblSort {","","    public static void main(String[] args) {","        BubbleSort.sort(new int[] { 14, 51, 21, 61, 21, 14, 12, 56, 58, 47, 14, 12, 25, 25, 26, 23, 21, 24, 27, 45, 48, 46, 29, 58, 39, 38, 37, 1, 5, 6 });","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] { },new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:/Users/dennis/Documents/NetBeansProjects/annotation-test"));

    public static void main(String[] args) {
        com.dennisjonsson.visualization.test.BubbleSortVisual.sort(new int[] { 14, 51, 21, 61, 21, 14, 12, 56, 58, 47, 14, 12, 25, 25, 26, 23, 21, 24, 27, 45, 48, 46, 29, 58, 39, 38, 37, 1, 5, 6 });
    }
public static int read(String name,int dimension, int index ){ 
logger.read("BblSortVisual", name ,index ,dimension);
return index; 
}
}
