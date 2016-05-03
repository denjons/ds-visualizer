/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.markup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author dennis
 */
public class Source {
    
    public final String [] sourceLines;
    
    //public final HashMap<String, DataStructure> annotatedVariables;

    public Source(String [] lines) {
        this.sourceLines = lines;
      //  this.annotatedVariables = new HashMap<>();
    }
    /*
    public void addDataStructure(DataStructure dataStructure){
        this.annotatedVariables.put(dataStructure.getIdentifier(), 
                dataStructure);
    }*/

}
