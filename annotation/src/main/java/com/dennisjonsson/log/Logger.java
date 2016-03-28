/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log;

import com.dennisjonsson.markup.AbstractType;
import com.dennisjonsson.markup.ArrayEntity;
import com.dennisjonsson.markup.DataStructure;
import com.dennisjonsson.markup.DataStructureFactory;
import com.dennisjonsson.markup.Entity;
import com.dennisjonsson.markup.EntityFactory;
import com.dennisjonsson.markup.Header;
import com.dennisjonsson.markup.Markup;
import com.dennisjonsson.markup.Operation;
import com.dennisjonsson.markup.Read;
import com.dennisjonsson.markup.UndefinedEntity;
import com.dennisjonsson.markup.Write;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 *
 * @author dennis
 */
public class Logger {
    
    private int separator = 0;
    private HashMap<String, ArrayList<TextLogOperation>> Operations;
    private ArrayList<TextLogOperation> bodyList;
    Markup markup;
    private boolean endStatement = false;

    public Logger(String [] data) {
        // markup
        // header
        Header header = new Header();
        for(int i = 0; i < data.length-2; i = i+3){
           // abstarctType, type, name 
           //DataStructure dataStructure = new DataStructure(data[i], data[i+1], data[i+2]);
           DataStructure dataStructure = DataStructureFactory
                   .getDataStructure(data[i], data[i+1], data[i+2]);
           header.addDataStructure(dataStructure);
        }
        markup = new Markup(header, new ArrayList<Operation>());
        markup.header.setVisual("graph");
        // operations
        Operations = new HashMap<String, ArrayList<TextLogOperation>>();
        bodyList = new ArrayList<TextLogOperation>();
        
    }
    
    public int endStatement(){
        endStatement = true;
        return separator;
    }
    
    public void read(String name, String statementId ,int index ,int dimension){
        statementId = statementId + separator+"";
        //System.out.println("logged: "+op+", "+id +", "+uuid +", "+ index +", "+ dimension);
        
        if(markup.header.annotatedVariables.get(name) == null){
            DataStructure dataStructure = new DataStructure("unknown", "unknown", name);
            markup.header.annotatedVariables.put(name,dataStructure);
        }
        
        if(Operations.get(statementId) == null){
            Operations.put(statementId, new ArrayList<TextLogOperation>());
        }
        
        
        if(dimension == 0){
          // Operation operation = new Operation(op, id, new int[]{index}, new String[]{""} );
          // ReadOperation(String name, int[] index, String operation, String statementId)
          TextReadOperation op = new TextReadOperation(name, new int[]{index},"read",statementId);
           Operations.get(statementId).add(op);
           System.out.println("\nindex: "+Operations.get(statementId).size()+", "+statementId);
           bodyList.add(op);
        }else{
            ArrayList<TextLogOperation> list = Operations.get(statementId);
            System.out.println("\nindex: "+list.size()+", "+statementId);
            TextReadOperation oldOperation = (TextReadOperation)list.get(list.size() - 1);
            oldOperation.extendIndex(index);
            int [] indexes = oldOperation.index;
           // markup.header.variables.get(name).updateSize(indexes);
        }
    }
    
    public void write(String name, String statementId, String value){
        statementId = statementId + separator+"";
        //  WriteOperation(String name, String value, String statementId)
        TextWriteOperation op = new TextWriteOperation(name, value, statementId);
        //Operations.get(statementId).add(op);
        bodyList.add(op);
    }
    
    public void eval(String statementId, String value){
        statementId = statementId + separator+"";
        // 
        if(endStatement){
             separator ++;
             endStatement = false;
        }
        // EvalOperation(String value, String operation, String statementId)
        TextEvalOperation op = new TextEvalOperation(value, statementId);
        bodyList.add(op);
    }
    
    public void print(){
        String json = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Collections.reverse(markup.body);
        json = gson.toJson(markup);
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
    
    
    public void printLog(){
        System.out.println("body list: "+bodyList.size());
        parse(bodyList.size() - 1);
        print();
    }
    
    private void parse(int i){
        while(i >= 0){
        // eval
            TextLogOperation op = bodyList.get(i);
            if(!op.operation.equalsIgnoreCase("eval")){
                throw new RuntimeException("unexpected operation at "+i+": "+op.operation);
            }
            TextEvalOperation eval = (TextEvalOperation)op;

            // look at next operation
            i --;
            TextLogOperation next = bodyList.get(i);
            if(!next.statementId.equalsIgnoreCase(eval.statementId)){
                throw new RuntimeException(
                        "unexpected eval operation at "+i+","
                                + " followed by "+next.operation+","
                                + " id:  "+next.statementId);
            }

            // check wether it is read or write
            if(next.operation.equalsIgnoreCase("write")){
                i = writeState(eval.value, i, next.statementId);
            }else if(next.operation.equalsIgnoreCase("read")){
                i = readState(i, eval.value, next.statementId);
            }
        }
    }
    
    private int writeState( String value ,int i, String statementId){
        
        ArrayList<TextReadOperation> reads = new ArrayList<TextReadOperation>();
        
        while(i > 0 && bodyList.get(i).statementId.equalsIgnoreCase(statementId)){
            TextLogOperation op = bodyList.get(i);
            if(op.operation.equalsIgnoreCase("read")){
                reads.add((TextReadOperation)op);
            }
            i--;
        }
        
        Write op = null;
        if(reads.size() == 2){
            
            //Write(Entity source, Entity target, String[] value, String op) 
            TextReadOperation to = (TextReadOperation) reads.get(0);
            TextReadOperation from = (TextReadOperation) reads.get(1);
           /* 
            DataStructure targetDataStrucutre = 
                    this.markup.header.variables.get(to.name);*/
            
            Entity target = new ArrayEntity(to.index, to.name);
            
            
          /*  DataStructure sourceDataStrucutre = 
                    this.markup.header.variables.get(from.name);*/
            
            Entity source = new ArrayEntity(from.index, from.name);
            
            // look if source and target needs to be swapped
            op = new Write( source, target, new String [] {value});
        }
        /*added*/
        else if(reads.size() == 0){
          
          //throw new RuntimeException("No read operations in read state at: "+i);
          i--;
          return i;
        }
        else{
            //Write(String id, int[] index, String[] values)
            TextReadOperation to = (TextReadOperation) reads.get(0);
            Entity target = new ArrayEntity(to.index, to.name);
 
            op = new Write(
                    UndefinedEntity.UNDEFINED_ENTITY, 
                    target, 
                    new String [] {value} );
            
        }
        
        markup.appendOperation(op);
        return i;
    }
    
    private int readState(int i, String value, String statementId){
        
        
        TextReadOperation readOp = (TextReadOperation)bodyList.get(i);
 
        Entity source = new ArrayEntity(readOp.index, readOp.name);
        
        // Read(String id, int[] index, String[] values)
        Read read = new Read( 
                source, 
                UndefinedEntity.UNDEFINED_ENTITY, 
                new String [] {value} );
        
        markup.appendOperation(read);
        
        i--;
        
     
        
        return i;
    }
    
    
}
