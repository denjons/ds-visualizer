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
public class WriteOperation extends LogOperation{
    String name;
    String value;

    public WriteOperation(String operation, String statementId) {
        super(operation, statementId);
    }

    public WriteOperation(String name, String value, String operation, String statementId) {
        super(operation, statementId);
        this.name = name;
        this.value = value;
    }
    
    
}
