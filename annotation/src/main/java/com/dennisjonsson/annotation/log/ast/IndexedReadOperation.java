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
public class IndexedReadOperation extends LogOperation {


    public static final String OPERATION = "IndexedRead";
    final int index, dimension;

    public IndexedReadOperation(String identifier, int index, int dimension) {
        super(OPERATION, identifier);
        this.index = index;
        this.dimension = dimension;
    }
    

    public static boolean initial(DataStructure dataStructure, 
            IndexedReadOperation op){
        return (op.dimension + 1) == ((ArrayDataStructure)dataStructure).getDimensions();
    }
   
    
}
