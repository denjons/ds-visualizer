/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log;

import com.dennisjonsson.markup.DataStructure;
import com.dennisjonsson.markup.DataStructureFactory;
import com.dennisjonsson.markup.Header;
import com.dennisjonsson.markup.Markup;
import com.dennisjonsson.markup.Operation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author dennis
 */
public class ASTLogger {

    ArrayList<LogOperation> operations = 
            new ArrayList<LogOperation>();
    
    Markup markup;
    Stack<Operation> callStack;

    public ASTLogger(String [] data) {
        
        callStack = new Stack<>();
        Header header = new Header();
        
        for(int i = 0; i < data.length-2; i = i+3){
  
           DataStructure dataStructure = DataStructureFactory
                   .getDataStructure(data[i], data[i+1], data[i+2]);
           header.addDataStructure(dataStructure);
        }
        markup = new Markup(header, new ArrayList<Operation>());
        markup.header.setVisual("graph");
        
    }
    
            
    public void read(String id, String stId, int index, int dimension){
        // IndexRead(String identifier, int index, int dimension, String statementId)
        operations.add(new IndexRead(id, index, dimension, stId));
    }
    
    public void write(String name, String statementId, String value){
        // (String name, String value, String operation, String statementId)
        operations.add(new WriteOperation(name, value, statementId));
    }
    
    public void eval(String statementId, String value){
        //EvalOperation(String value, String statementId)
        operations.add(new EvalOperation(value, statementId));
    }
    
    private void parse(){
        for(LogOperation op : operations){
            
        }
    }
    
    public void printLog(){
        
        
        String json = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        json = gson.toJson(operations);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("testText.txt", "UTF-8");
            writer.print(json);
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getMessage());
           // java.util.logging.Logger.getLogger(BFStest.class.getName()).log(Level.SEVERE, null, ex);
        }
        // java.util.logging.Logger.getLogger(BFStest.class.getName()).log(Level.SEVERE, null, ex);
        finally{
            if(writer != null)
                writer.close();
        }
    }
    
}
