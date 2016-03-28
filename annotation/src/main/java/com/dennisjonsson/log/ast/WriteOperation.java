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
public class WriteOperation extends LogOperation {
    
    public static final String OPERATION = "write";
    public static final int ARRAY = 0;
    public static final int VARIABLE = 1;
    public static final int EXPRESSION = 2;
    public static final int UNDEFINED = 3;
    
 
    public final String value;
    public final int targetType;
    public final int sourceType;

    public WriteOperation(String identifier, String value, int sourceType, int targetType) {
        super(OPERATION, identifier);
        this.value = value;
        this.targetType = targetType;
        this.sourceType = sourceType;
    }
    
    
    
}
