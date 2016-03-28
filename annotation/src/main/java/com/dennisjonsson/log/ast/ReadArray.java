/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log.ast;

import com.dennisjonsson.markup.ArrayDataStructure;
import com.dennisjonsson.markup.DataStructure;

/**
 *
 * @author dennis
 */
public class ReadArray extends ParseOperation {
    
    
    public final int dimensions;
    public final int [] index;
    public final String type;
    
    public  ReadArray(String identifier, DataStructure dataStructure) {
        super("ReadArray", identifier);
        this.dimensions = ((ArrayDataStructure)dataStructure).getDimensions();
        this.index = new int[dimensions];
        this.type = dataStructure.getType();
    }

    @Override
    public boolean update(LogOperation op) {
        IndexedReadOperation readArray = (IndexedReadOperation)op;
        index[readArray.dimension] = readArray.index;
        return readArray.dimension == 0;
    }

  
}
