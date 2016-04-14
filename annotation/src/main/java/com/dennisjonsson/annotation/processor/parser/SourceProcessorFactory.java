/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

/**
 *
 * @author dennis
 */
public class SourceProcessorFactory {
    
    public enum Type{
        TEXT,
        AST
    }
    
    public static SourceProcessor getProcessor(Type type, 
            String path, String className, String fullName){
        switch(type.toString()){
            case "TEXT":
                return new TextProcessor(path, className);
            case "AST" :
                return new ASTProcessor(path, className, fullName);
            default:
                throw new RuntimeException(
                        "SourceProcessorFactory: "
                                + type + " processor does not exist.");
             
        }
    }
    
}
