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
public class TextLogOperation {
    String operation;
    String statementId;

    public TextLogOperation(String operation, String statementId) {
        this.operation = operation;
        this.statementId = statementId;
    }
    
}
