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
public class IndexRead extends LogOperation {

    final String identifier;
    final int index, dimension;

    public IndexRead(String identifier, int index, int dimension, String statementId) {
        super("IndexRead", statementId);
        this.identifier = identifier;
        this.index = index;
        this.dimension = dimension;
    }
    

   
    
}
