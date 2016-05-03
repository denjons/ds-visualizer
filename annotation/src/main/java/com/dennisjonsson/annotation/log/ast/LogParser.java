/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.log.ast;
import com.dennisjonsson.annotation.markup.DataStructure;
import com.dennisjonsson.annotation.markup.Markup;
import com.dennisjonsson.annotation.markup.Source;
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
    public final String className;
    
    int pointer =0;
    
    private final ArrayList<ParseOperation> parserStack;
    private final CallStack callStack;

    public LogParser( String className, ArrayList<LogOperation> operations,
            Markup markup) {
        this.className = className;
        this.dataStructures = markup.header.getDataStructures(className);
        this.operations = operations;
        this.composer = new MarkupComposer(className, markup);
        parserStack = new ArrayList<>();
        callStack = new CallStack(dataStructures, operations, parserStack);
    }
    
    public void addOperation(LogOperation op){
        operations.add(op);
        pointer ++;
        //System.out.println("size:    "+operations.size());
        //System.out.println("pointer: "+pointer);
    }
    
    public void resetPointer(int i){
        //System.out.println("reset:   "+i);
        for(int j = i; j < pointer; j++ ) {
            operations.remove(i);
        }
        pointer = i;
    }
    
    public LogOperation getFromTop(int i){
        return operations.get(pointer-1 - i);
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
    
    public int visit(EvalOperation op, int i){
        //System.out.println(Arrays.toString(operations.toArray())+ ", "+i);
        LogOperation nextOp = operations.get(i);
        switch(op.expressionType){
            case EvalOperation.ASSIGNMENT :
                if(!(nextOp.operation.equalsIgnoreCase(WriteOperation.OPERATION))){
                    throw new RuntimeException("ASSIGNMENT: Unexpected successor to eval: "+nextOp.operation);
                }
                return visit((WriteOperation)nextOp, i-1 );
            case EvalOperation.ARRAY_ACCESS :
                return visit((IndexedReadOperation)nextOp, i-1);
            case EvalOperation.METHOD_CALL :
                return visit((WriteOperation)nextOp, i-1 );
            //case EvalOperation.DECLARATION :
                
        }
        
        throw new RuntimeException("visit: unsupported eval expression type: "+op.expressionType);
    }
    
    public int visit(WriteOperation op, int i){
        
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
    
    public int visit(IndexedReadOperation op, int i){
        
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
        
        EvalOperation eval = (EvalOperation)operations.get(i+2);
        
        // jump over expressions
        while(operations.get(i).operation
                .equalsIgnoreCase(EvalOperation.OPERATION)){
            i = callStack.collect(i-1);
            callStack.next();
        }

        i = callStack.collect(i);
       // System.out.println("_: "+i);
        
        callStack.next(to);
        
        composer.composeWriteVariableToArray(op, to, eval);
        
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
        EvalOperation eval = (EvalOperation)operations.get(i+2);
        
        ArrayList<ParseOperation> from = new ArrayList<>();

        i = callStack.collect(i);
        callStack.next(from);
        
        ArrayList<ParseOperation> to = new ArrayList<>();
        //System.out.println("at "+i+": "+operations.get(i).operation);
        i = callStack.collect(i-1);
        callStack.next(to);
        
        composer.composeWriteArrayToArray(from, to, op, eval);
        // (Entity source, Entity target, String[] value)
        // Reads
        
        //System.out.println("at "+i+": "+operations.get(i).operation);
        
        return i-1;
        
    }
    
    
}
