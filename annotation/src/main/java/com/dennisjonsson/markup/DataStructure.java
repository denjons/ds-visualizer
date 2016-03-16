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
    
    private String abstractType;
    private String type;
    private String identifier;
    public final HashMap<String, Object> attributes;

    public DataStructure(String abstractType, String type, String name) {
        this.abstractType = abstractType;
        this.type = type;
        this.identifier = name;
       // size = new ArrayList<Integer>();
        attributes = new HashMap<String, Object>();
    }

    public DataStructure(String abstractType, String type, 
            String name, HashMap<String, Object> attributes) {
        this.abstractType = abstractType;
        this.type = type;
        this.identifier = name;
        this.attributes = attributes;
    }

    public String getAbstractType() {
        return abstractType;
    }

    public String getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }


       
}
