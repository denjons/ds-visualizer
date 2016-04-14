/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.markup;

import java.util.HashMap;

/**
 *
 * @author dennis
 */
public class DataStructure {
    
    public static final String CLASS_NAME = "com.dennisjonsson.markup.DataStructure";
    
    protected String abstractType;
    protected String rawType;
    public String identifier;
    public final HashMap<String, Object> attributes;

    public DataStructure(String abstractType, String type, String name) {
        this.abstractType = abstractType;
        this.rawType = type;
        this.identifier = name;
       // size = new ArrayList<Integer>();
        attributes = new HashMap<>();
    }

    public DataStructure(String abstractType, String type, 
            String name, HashMap<String, Object> attributes) {
        this.abstractType = abstractType;
        this.rawType = type;
        this.identifier = name;
        this.attributes = attributes;
    }

    public String getAbstractType() {
        return abstractType;
    }

    public String getType() {
        return rawType;
    }

    public String getIdentifier() {
        return identifier;
    }


       
}
