/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.markup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author dennis
 */
public class Header {
    
    public final int VERSION = 2;
    public final HashMap<String, DataStructure> annotatedVariables;
    private String visual;

    public Header() {
        this.annotatedVariables = new HashMap<String, DataStructure> ();

    }
    
    public void addDataStructure(DataStructure dataStructure){
        this.annotatedVariables.put( dataStructure.getIdentifier(),dataStructure);
    }

 
    public void setVisual(String visual) {
        this.visual = visual;
    }
 
    @Override
    public String toString() {
        return "{\n "
                + "\"version\": "+VERSION+", \n"
                + "\"AnnotatedVariables\": "+Arrays.toString(annotatedVariables.values().toArray())+" \n"
                + "}";
    }
    
    
    
}
