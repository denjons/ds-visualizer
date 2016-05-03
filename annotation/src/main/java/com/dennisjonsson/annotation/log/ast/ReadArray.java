/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.log.ast;

import com.dennisjonsson.annotation.markup.ArrayDataStructure;
import com.dennisjonsson.annotation.markup.DataStructure;

/**
 *
 * @author dennis
 */
public class ReadArray extends ParseOperation {
 
    public final int dimensions;
    public int [] index;
    public final String type;
    
    public  ReadArray(String identifier, DataStructure dataStructure) {
        super("ReadArray", identifier);
        this.dimensions = ((ArrayDataStructure)dataStructure).getDimensions();
        
        // changed
        //this.index = new int[dimensions];
        this.index = new int[0];
        
        this.type = dataStructure.getType();
    }

    @Override
    public boolean update(LogOperation op) {
        IndexedReadOperation readArray = (IndexedReadOperation)op;
        
        // added
        updateDimension(readArray.dimension);
        
        index[readArray.dimension] = readArray.index;
        return readArray.dimension == 0;
    }
    
    public void updateDimension(int dimension) {
        if(index.length <= dimension){
            int [] newIndex = new int[dimension+1];
            if(index.length > 0){
                System.arraycopy(index, 0,newIndex , 0, index.length);
            }   
            index = newIndex;
        }
    }

  
}
