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
    public static final int ARRAY_ECCESS = 2;
    
    public final String value;
    public final int expressionType;
    
    public EvalOperation(String targetId, String value, int expressionType) {
        super(OPERATION, targetId);
        this.value = value;
        this.expressionType = expressionType;
    }

  
}
