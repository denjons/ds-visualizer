/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log.ast;

/**
 *
 * @author dennis
 */
public class EvalOperation extends LogOperation {
    public static final String OPERATION = "eval";
    
    public static final int ASSIGNMENT = 0;
    public static final int DECLARATION = 1;
    public static final int ARRAY_ACCESS = 2;
    public static final int METHOD_CALL = 3;
    
    public final Object value;
    public final int expressionType;
    
    public EvalOperation(String targetId, Object value, int expressionType) {
        super(OPERATION, targetId);
        this.value = value;
        this.expressionType = expressionType;
    }

  
}
