/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.markup;

/**
 *
 * @author dennis
 */
public class DataStructureFactory {
    
    public static DataStructure getDataStructure(
            String abstractType, String type, String identifier){
        
        switch(abstractType){
            case "ADJECENCY_MATRIX" :
                return createAdjecencyMatrix(type, identifier);
            case "ARRAY" :
                return createArray(type, identifier);  
        }
        throw new RuntimeException("unknown abstract type: "
                + abstractType.toString());
        
    }
    
    private static DataStructure createAdjecencyMatrix(String type, String identifier){
       
        ArrayDataStructure dataStructure = new ArrayDataStructure(
                AbstractType.ADJECENCY_MATRIX.toString(), type, identifier);
        return dataStructure;
    }
    
    private static DataStructure createArray(String type, String identifier){
       
        ArrayDataStructure dataStructure = new ArrayDataStructure(
                AbstractType.ARRAY.toString(), type, identifier);
        return dataStructure;
    }
    
    
}
