/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.log.AbstractInterpreter;
import com.dennisjonsson.log.DefaultInterpreter;
import com.dennisjonsson.log.ast.ASTLogger;
import com.dennisjonsson.log.ast.SourceHeader;
import com.dennisjonsson.markup.Argument;
import com.dennisjonsson.markup.DataStructure;
import com.dennisjonsson.markup.Method;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dennis
 */
public class ASTProcessor extends SourceProcessor {
    
    private ASTParser adapter;
    private CompilationUnit unit;
    public HashMap<String, Method> methods;
    public final String fullName;
    public ArrayList<String> includes;
    private static final String SUFFIX = "Visual";
    
    ASTProcessor(String path, String className, String fullName) {
        super(path, className);
        this.fullName = fullName;
        methods = new HashMap<>();
        includes = new ArrayList<>();
    }
    
    public void setIncludes(ArrayList<String> includes){
        this.includes = includes;
    }
    
    public void addArgument(Argument arg){
        if(methods.get(arg.method.name) == null){
            methods.put(arg.method.name, arg.method);
        }
        methods.get(arg.method.name).addArgument(arg);
    }
    
    public void addMethods(HashMap<String, Method> methods){
        this.methods = methods;
    }
    
    public void addAllArguments(ArrayList<Argument> args){
        for(Argument arg : args){
            addArgument(arg);
        }
    }

    @Override
    public void loadSource() {
        unit = readFile();
    }

   CompilationUnit readFile(){
       
       InputStream stream = this.getInputStream(path, className);
       
       CompilationUnit unit = null;
       
        try {
            unit = JavaParser.parse(stream);
        } catch (ParseException ex) {
            Logger.getLogger(ASTProcessor.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ASTProcessor: Could not parse file"); 
        }finally{
           try {
               stream.close();
           } catch (IOException ex) {
               Logger.getLogger(ASTProcessor.class.getName()).log(Level.SEVERE, null, ex);
               
           }
        }
        
       return unit;
   }
   
    private void replaceIncludes(TextParser parser){
        for(String incl : includes){
            parser.renameType(incl, incl + SUFFIX);
            System.out.println("include: "+incl+" -> "+(incl + SUFFIX));
        }
    }

    @Override
    public void processSource(Object arg) {
        
        if(unit == null){
            throw new RuntimeException("ASTPRocessor: CompilationUnit is null");
        }
        /*
        if(print == null){
            throw new RuntimeException("ASTPRocessor: No printing method found");
        }*/
        String newClass = className + SUFFIX;
        
        
        PreParser pp = new PreParser(methods);
        pp.visit(unit, null);
        
        adapter = new ASTParser(className, dataStructures, getPrintingMethod(), methods);
        adapter.visit(unit, null);

        // textual changes
        TextParser parser = new TextParser(unit.toString());
        parser.renameClass(className, newClass);
        
        String oldClassName = className;
        className = newClass;
        
        parser.removeAnnotations();
        // replace type of included sources
        replaceIncludes(parser);
        parser.insertInterceptorMethods(className, dataStructures);
        parser.insertField("public static "+ASTLogger.CLASS_NAME+" logger = \n"
                +   ASTLogger.CLASS_NAME+".instance(\n"
                +   "new "+SourceHeader.CLASS_NAME+"(\n"
                +   "\""+newClass+"\""
                +   ",\n"
                +   getPrintingPath()
                +   ",\n"
                +   parser.printDataStructures(dataStructures) 
                +   ",\n"
                +   DefaultInterpreter.class.getName()+ ".instance()"
                + "));", className);
        source = parser.getSource();
     
        
    }
    
   
   
        
    
}
