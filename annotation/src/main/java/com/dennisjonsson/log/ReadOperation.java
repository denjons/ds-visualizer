/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log;

/**
 *
 * @author dennis
 */
public class ReadOperation extends LogOperation {
    
    String name;
    int [] index;

    public ReadOperation(String operation, String statementId) {
        super(operation, statementId);
    }

    public ReadOperation(String name, int[] index, String operation, String statementId) {
        super(operation, statementId);
        this.name = name;
        this.index = index;
    }

    
    
  
    public void extendIndex(int in){
        int[] newIndex = new int[index.length + 1];
        for(int i = 0; i < index.length; i++){
            newIndex[i] = index[i];
        }
        newIndex[index.length] = in;
        index = newIndex;
    }
}
