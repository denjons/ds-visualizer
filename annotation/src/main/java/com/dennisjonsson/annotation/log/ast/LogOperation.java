/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.log.ast;

/**
 *
 * @author dennis
 */
public class LogOperation {
    
    public final String operation, identifier;

    public LogOperation(String operation, String identifier) {
        this.operation = operation;
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return operation;
    }

    


    
}
