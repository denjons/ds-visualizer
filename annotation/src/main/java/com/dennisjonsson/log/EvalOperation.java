/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log;

import com.dennisjonsson.markup.Operation;

/**
 *
 * @author dennis
 */
public class EvalOperation extends LogOperation {
    
    String value;

    public EvalOperation(String value, String statementId) {
        super("eval", statementId);
        this.value = value;
    }
   
    

}
