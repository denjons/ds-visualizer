/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log.ast;
import com.dennisjonsson.markup.DataStructure;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author dennis
 */
public class CallStack {
    
    private final HashMap<String, DataStructure> dataStructures;
    private final ArrayList<LogOperation> operations; 
    private final ArrayList<ParseOperation> callStack;
    
    int pointer;
    int start;
    boolean first;
  
    public CallStack(
            HashMap<String, DataStructure> dataStructures, 
            ArrayList<LogOperation> operations,
            ArrayList<ParseOperation> callStack) {
        
        this.dataStructures = dataStructures;
        this.operations = operations;
        this.callStack = callStack;
        this.pointer = - 1;
        this.start = 0;
    }
    
    public void next(ArrayList<ParseOperation> section){
        for(int i = start; i < callStack.size(); i++){
            section.add(callStack.get(i));
        }
        start = callStack.size();
        pointer = start - 1;
    }
    
    public int collect(int i){
        first = true;
        while(!branch(operations.get(i))){
            first = false;
            i--;
        }
        return i;
    }
    
    public boolean branch(LogOperation op){
        switch(op.operation){
            case IndexedReadOperation.OPERATION :
                return handle((IndexedReadOperation)op);
            case EvalOperation.OPERATION :
                return handle((EvalOperation)op);
            case WriteOperation.OPERATION :
                return handle((WriteOperation)op);
        }
        throw new RuntimeException("unhandled log operation: "+op.operation);
    }
    
    private boolean handle(IndexedReadOperation op){
        DataStructure dataStructure = dataStructures.get(op.identifier);
        if(IndexedReadOperation.initial(dataStructure, op) || first){
            return push(
                    new ReadArray(op.identifier, dataStructure), op);
        }
        return update(op);
    }
    
    boolean handle(EvalOperation op){
        return true;
        //throw new RuntimeException("EvalOperation not yet supported");
        //return true;
    }
    
    boolean handle(WriteOperation op){
        throw new RuntimeException("WriteOperation not yet supported");
       // return true;
    }
    
    private boolean push(ParseOperation op, LogOperation logOp){
        
        callStack.add(op);
        if(!op.update(logOp)){
            pointer ++;
        }
        //System.out.println("push: "+(pointer < start));
        return pointer < start;
        
    }
    
    private boolean update(LogOperation op){
        if(callStack.get(pointer).update(op)){
            pointer --;
        }
        //System.out.println("update: "+(pointer < start));
        //System.out.println("updated: "+op.operation+" at "+(pointer + 1));
        return pointer < start;
    }
    

   

   
    
    
}
