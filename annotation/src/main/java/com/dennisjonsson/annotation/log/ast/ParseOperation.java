
package com.dennisjonsson.annotation.log.ast;

import com.dennisjonsson.annotation.markup.DataStructure;


public abstract class ParseOperation {

    public final String operation;
    public final String identifier;
    

    public ParseOperation(String operation, String identifier) {
        this.operation = operation;
        this.identifier = identifier;
    }
    
    public abstract boolean update(LogOperation op); 

    @Override
    public String toString() {
        return operation; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
