/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.markup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author dennis
 */
public class Header {
    
    public transient static final String CONCAT = " ";
    
    public final int version = 2;
    public final HashMap<String, String []> sources;
    // old version
    public final HashMap<String, DataStructure> annotatedVariables;

    public Header(HashMap<String, String []> sources) {
        this.sources = sources;
        
        // old version
        annotatedVariables = new HashMap<>();
    }
    
    
    public void addDataStructure(String className, DataStructure dataStructure){
        //this.sources.get(className).addDataStructure(dataStructure);
        annotatedVariables.put(dataStructure.identifier, dataStructure);
    }
    
    public DataStructure getDataStructure(String className, String identifier){
        return annotatedVariables.get(identifier);
      //  return this.sources.get(className).annotatedVariables.get(identifier);
    }
    
    public HashMap<String, DataStructure> getDataStructures(String className){
        return annotatedVariables;
        //  return this.sources.get(className);
    }


    @Override
    public String toString() {
        return super.toString();
    }
    
    
    
}
