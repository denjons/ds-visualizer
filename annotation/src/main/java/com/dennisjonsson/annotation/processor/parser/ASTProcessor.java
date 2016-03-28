/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.markup.DataStructure;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dennis
 */
public class ASTProcessor extends SourceProcessor {
    
    private ASTParser adapter;
    private CompilationUnit unit;
    
    ASTProcessor(String path, String className) {
        super(path, className, new ArrayList<DataStructure>());
        
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

    @Override
    public void processSource(Object arg) {
        
        if(unit == null){
            throw new RuntimeException("ASTPRocessor: CompilationUnit is null");
        }
        String newClass = (String)arg;
        adapter = new ASTParser(dataStructures);
        adapter.visit(unit, null);
        
        // textual changes
        TextParser parser = new TextParser(unit.toString());
        parser.renameClass(className, newClass);
        className = newClass;
        parser.removeAnnotations();
        parser.insertInterceptorMethods(dataStructures);
        parser.insertField("public static com.dennisjonsson.log.ast.ASTLogger logger = \n"
                +   "new com.dennisjonsson.log.ast.ASTLogger(\n"
                +   parser.printDataStructures(dataStructures)
                +   ");", className);
        parser.replace(TextProcessor.INSERTION_COMMENT, "\nlogger.printLog();\n");
        source = parser.getSource();
     
        
    }
        
    
}
