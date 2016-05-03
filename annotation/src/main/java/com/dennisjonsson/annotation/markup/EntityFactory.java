/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.markup;

/**
 *
 * @author dennis
 */
public class EntityFactory {
    
    public static Entity createEntity(AbstractType abstractType, String type, String identifier){
        switch(abstractType.toString()){
            case "ADJECENCY_MATRIX" :
                return createArrayEntity(identifier);
            case "ARRAY" :
                return createArrayEntity(identifier);
                
        }
        throw new RuntimeException("EntityFactory: unknown entity type: "+abstractType);
    }
    
    private static ArrayEntity createArrayEntity(String identifier){
       return new ArrayEntity(identifier);
    }
    
}
