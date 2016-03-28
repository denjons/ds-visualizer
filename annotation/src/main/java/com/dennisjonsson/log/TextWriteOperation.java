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
public class TextWriteOperation extends TextLogOperation{
    String name;
    String value;

    public TextWriteOperation(String operation, String statementId) {
        super(operation, statementId);
    }

    public TextWriteOperation(String name, String value, String statementId) {
        super("write", statementId);
        this.name = name;
        this.value = value;
    }
    
    
}
