/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.annotation.markup.DataStructure;
import com.dennisjonsson.annotation.markup.Method;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.lang.model.element.Element;

/**
 *
 * @author dennis
 */
public class Parser extends ModifierVisitorAdapter {
    
    final ArrayList<DataStructure> dataStructures;
    final ArrayList<DataStructure> uknowns;
    final HashMap<String, Method> methods;
    final String className;
    final String fullClassName;
    final Element printMethod;
    
    public static final String LOGGER = "logger";
    public static final String PRINT = "print";
    public static final String PRINT_METHOD = LOGGER+"."+PRINT;
    
    // over ten has rank
    public static final int SKIP = 15;

    public static final int IS_ASSIGNMENT = 6;
    public static final int IS_DECLARATION = 5;
    // under ten has no rank
    public static final int IS_UNARY = 4;
    public static final int IS_BINARY = 3;
    
    

    public Parser(
            String className, 
            String fullClassName, 
            ArrayList<DataStructure> dataStruct, 
            Element printMethod, 
            HashMap<String, Method> methods) {
        
        this.dataStructures = dataStruct;
        uknowns = new ArrayList<>();
        this.printMethod = printMethod;
        this.methods = methods;
        this.className = className;
        this.fullClassName = fullClassName;
        
    }
    
    public Parser(Parser parser){
        this.dataStructures = parser.dataStructures;
        uknowns = parser.uknowns;
        this.printMethod = parser.printMethod;
        this.methods = parser.methods;
        this.className = parser.className;
        this.fullClassName = parser.fullClassName;
    }
}
