/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.markup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author dennis
 */
public abstract class Entity {
    
    public static final String UNDEFINED = "undefined";
    
    private final String identifier;

    public Entity(String identifier) {
        this.identifier = identifier;
    }
    
    public String getId() {
        return identifier;
    }
    
    public abstract void update(DataStructure dataStructure);
    
   
    
}
