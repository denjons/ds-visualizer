
package com.dennisjonsson.log.ast;

import com.dennisjonsson.markup.DataStructure;


public abstract class ParseOperation {

    public final String operation;
    public final String identifier;
    

    public ParseOperation(String operation, String identifier) {
        this.operation = operation;
        this.identifier = identifier;
    }
    
    public abstract boolean update(LogOperation op); 
    
    
}
