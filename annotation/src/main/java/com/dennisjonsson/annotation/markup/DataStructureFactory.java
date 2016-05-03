/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.markup;

import java.util.ArrayList;

/**
 *
 * @author dennis
 */
public class DataStructureFactory {
    
    public static final String CLASS_NAME = "com.dennisjonsson.markup.DataStructureFactory";
    public static final String METHOD =  "getDataStructure";
    
    public static DataStructure getDataStructure(
            String abstractType, 
            String type, 
            String identifier){
        type = type.toLowerCase();
        
        String absType = abstractType.toLowerCase().replaceAll(" ", "");
        
        switch(absType){
            case "variable" :
                return createPrimitve(type, identifier, absType);
            default :
                return createArray(type, identifier, absType);
 
        }
  
    }
    
    public static DataStructure getDataStructure(
            String abstractType, 
            String type, 
            String identifier, 
            ArrayList<String> scope){
        
        DataStructure ds =  getDataStructure(abstractType, type, identifier);
        ds.setScope(scope);
        return ds;
    }
    
    public static DataStructure createPrimitve(String type, String identifier, String abstractType){
        return new PrimitiveDataStructure(abstractType,getRawType(type),type,identifier);
    }
    
    private static DataStructure createArray(String type, String identifier, String abstractType){
       
        return new ArrayDataStructure(abstractType, getRawType(type), type,identifier);
    }
    
    public static String getRawType(String type){
        if(type != null && type.contains("[")){
            return "array";
        }
        return "independent variable";
    }
    
}
