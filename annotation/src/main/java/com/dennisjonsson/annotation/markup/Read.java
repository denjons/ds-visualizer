/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.markup;

import java.util.HashMap;

/**
 *
 * @author dennis
 */
public class Read extends Operation{
    
    public static final String OPERATION = "read";
    private static final String KEY_TARGET = "target";
    private static final String KEY_SOURCE = "source";
    private static final String KEY_VALUE = "value";
    

    public Read(Entity source, Entity target, Object value, 
            String className, int beginLine, int endLine) {
        super(OPERATION, new HashMap<>(), className, beginLine, endLine);
        this.operationBody.put(KEY_SOURCE, source);
        this.operationBody.put(KEY_TARGET, target);
        this.operationBody.put(KEY_VALUE, value);
    }

  /**
        * Set the target variable for this Read operation.
        * The identifier of the variable should be previously declared in the header.
        * @param target The target variable for this Read operation.
        */
    public void setTarget(Entity target){
        this.operationBody.put(KEY_TARGET, target);
    }

       /**
        * Set the source variable for this Read operation.
        * The identifier of the variable should be previously declared in the header.
        * @param source The source variable for this Read operation.
        */
    public void setSource(Entity source){
        this.operationBody.put(KEY_SOURCE, source);
    }

       /**
        * Set the value(s) which were read from {@code source}. This should be the value
        * of {@code target} and the specified index after operation execution, if applicable.
        * @param value Set the value(s) which were read from {@code source}.
        */
    public void setValue(Object value){
        this.operationBody.put(KEY_VALUE, value);
    }

    public Entity getTarget(){
        return (Entity)this.operationBody.get(KEY_TARGET);
    }

    public Entity getSource(){
        return (Entity)this.operationBody.get(KEY_SOURCE);
    }

    public Object getValue(){
        return this.operationBody.get(KEY_VALUE);
    }

   
    
}
