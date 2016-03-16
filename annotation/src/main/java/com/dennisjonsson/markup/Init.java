/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.markup;

import java.util.HashMap;

/**
 *
 * @author dennis
 */
public class Init extends Operation {
    
    private static final String OPERATION = "init";
    private static final String KEY_TARGET = "target";
    private static final String KEY_SIZE = "size";
    private static final String KEY_VALUE = "value";

    
    public Init() {
        super(OPERATION, new HashMap<String, Object>());
        
    }

    /**
    * Set the target variable for this Init operation.
    * The identifier of the variable should be previously declared in the header.
    * @param var The target variable for this Init operation.
    */
    public void setTarget(Entity var){
        this.operationBody.put(KEY_TARGET, var);
    }

   /**
    * Set the declared maximum size of this variable, for each dimension.
    * @param size The declared maximum size of this variable.
    */
    public void setSize(int [] size){
        this.operationBody.put(KEY_SIZE, size);
    }

   /**
    * Set the value(s) with which to initialize this variable.
    * @param value The value(s) with which to initialize this variable.
    */
    public void setValue(String value){
        this.operationBody.put(KEY_VALUE, value);
    }

    public Entity getTarget(){
        return (Entity)this.operationBody.get(KEY_TARGET);
    }

    public int[] getSize(){
        return (int[])this.operationBody.get(KEY_SIZE);
    }

    public String getValue(){
        return (String)this.operationBody.get(KEY_VALUE);
    }

    
}
