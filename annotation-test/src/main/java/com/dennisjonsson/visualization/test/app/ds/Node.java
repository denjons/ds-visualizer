/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.app.ds;

import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.annotation.Visualize;

/**
 *
 * @author dennis
 */

@SourcePath(path = "C:/Users/dennis/Documents/NetBeansProjects/" 
        + "annotation-test/src/main/" 
        + "java/com/dennisjonsson/visualization/test/app/ds/")
public class Node {
    
    public int level;
    public int arity;
    public int value;
    
    @Visualize(abstractType = "array")
    public int [] children;

    public Node(int level, int arity, int value) {
        this.level = level;
        this.arity = arity;
        this.value = value;
        children = new int[arity];
    }
    
    
    
    public void init(){
        for(int i =0; i < children.length; i++){
            children[i] = (int)Math.floor(Math.random()*children.length);
        }
    }
}
