/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log.ast;
import com.dennisjonsson.markup.DataStructure;
import com.dennisjonsson.markup.Markup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author dennis
 */
public class LogParser {
    
    public final HashMap<String, DataStructure> dataStructures;
    public final ArrayList<LogOperation> operations;
    public final MarkupComposer composer;
    
    private final ArrayList<ParseOperation> parserStack;
    private final CallStack callStack;

    public LogParser(
            HashMap<String, DataStructure> dataStructures, 
            ArrayList<LogOperation> operations,
            Markup markup) {
        
        this.dataStructures = dataStructures;
        this.operations = operations;
        this.composer = new MarkupComposer(markup);
        parserStack = new ArrayList<>();
        callStack = new CallStack(dataStructures, operations, parserStack);
    }

    public void parse(){
        System.out.println("operations: "+operations.size());
        //System.out.println(Arrays.toString(operations.toArray()));
        int i = operations.size() - 1;
        while(i >= 0){
           i =  branch(i);
        }
    }
    
    private int branch(int i){
        LogOperation op = operations.get(i);
       // System.out.println("branch: "+op.operation);
        switch(op.operation){
            case EvalOperation.OPERATION :
                return visit((EvalOperation)op, i - 1);
            case WriteOperation.OPERATION :
                return visit((WriteOperation)op, i - 1 );
            case IndexedReadOperation.OPERATION :
                return visit((IndexedReadOperation)op, i - 1);
        }
        
        throw new RuntimeException("branch: unsupported logOperation: "+op.operation);
    }
    
    private int visit(EvalOperation op, int i){
        //System.out.println(op.operation);
        LogOperation nextOp = operations.get(i);
        switch(op.expressionType){
            case EvalOperation.ASSIGNMENT :
                if(!(nextOp.operation.equalsIgnoreCase(WriteOperation.OPERATION))){
                    throw new RuntimeException("Unexpected successor to eval: "+nextOp.operation);
                }
                return visit((WriteOperation)nextOp, i-1 );
            case EvalOperation.ARRAY_ECCESS :
                return visit((IndexedReadOperation)nextOp, i-1);
            //case EvalOperation.DECLARATION :
                
        }
        
        throw new RuntimeException("visit: unsupported eval expression type: "+op.expressionType);
    }
    
    private int visit(WriteOperation op, int i){
        //System.out.println(op.operation);
        if(op.sourceType == WriteOperation.ARRAY &&
                op.targetType == op.sourceType){
            return assignArraytoArray(op, i);
        }else if(op.sourceType == WriteOperation.ARRAY){
            return writeArrayToVariable(op, i);
        }else if(op.targetType == WriteOperation.ARRAY){
            return writeVariableToArray(op, i);
        }else if(op.sourceType == WriteOperation.VARIABLE && 
                op.sourceType == op.targetType){
            return writeVariableToVariable(op, i);
        }else if(op.sourceType == WriteOperation.UNDEFINED && 
                op.targetType == WriteOperation.ARRAY){
            return writeVariableToArray(op, i);
        }else if(op.sourceType == WriteOperation.UNDEFINED && 
                op.targetType == WriteOperation.VARIABLE){
            return writeVariableToVariable(op, i);
        }
        throw new RuntimeException("unsupported write operation: "
                + "source: "+ op.sourceType
                + ", target: "+op.targetType);      
    }
    
    private int visit(IndexedReadOperation op, int i){
        
        //System.out.println(op.operation);
        EvalOperation eval = (EvalOperation)operations.get(i+2);
        return readArrayToUnknown(eval, i+1);
    }
    
    private int writeVariableToVariable(WriteOperation op, int i){
        EvalOperation eval = (EvalOperation)operations.get(i + 2);
        composer.composeWriteVariableToVariable(op, eval);
        return i;
    }
    
    private int writeArrayToVariable(WriteOperation op, int i){
        
        EvalOperation eval = (EvalOperation)operations.get(i+2);
        ArrayList<ParseOperation> from = new ArrayList<>();
        
        i = callStack.collect(i);
        callStack.next(from);
        
        composer.composeWriteArrayToVariable(from, eval);
        
        return i - 1;
        
    }
    
    private int writeVariableToArray(WriteOperation op, int i){
        ArrayList<ParseOperation> to = new ArrayList<>();
        
        i = callStack.collect(i);
        callStack.next(to);
        
        composer.composeWriteVariableToArray(op, to);
        
        return i - 1;
    }
    
    private int readArrayToUnknown(EvalOperation eval, int i){
       
        ArrayList<ParseOperation> section = new ArrayList<>();
        i = callStack.collect(i);
        callStack.next(section);
        composer.composeReadArrayToUknown(section, eval);
       // System.out.println("after: "+i);
        //composer.composeReadArrayToUknown(section);
        // if the collect is interuptd by an eval
        if(!(operations.get(i) instanceof EvalOperation)){
            i--;
        }
        // reads
        // new Read()
        return i;
    }
    
    private int assignArraytoArray(WriteOperation op, int i){
        
        //System.out.println("at "+i+": "+operations.get(i).operation);
        
        ArrayList<ParseOperation> from = new ArrayList<>();

        i = callStack.collect(i);
        callStack.next(from);
        
        ArrayList<ParseOperation> to = new ArrayList<>();
        //System.out.println("at "+i+": "+operations.get(i).operation);
        i = callStack.collect(i-1);
        callStack.next(to);
        
        composer.composeWriteArrayToArray(from, to, op);
        // (Entity source, Entity target, String[] value)
        // Reads
        
        //System.out.println("at "+i+": "+operations.get(i).operation);
        
        return i-1;
        
    }
    
    
}
