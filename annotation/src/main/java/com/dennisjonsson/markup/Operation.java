/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.markup;

import java.util.HashMap;

/**
 *
 * @author dennis
 */
public abstract class Operation {
    
    public final String operation;
    public final HashMap<String, Object> operationBody;

    public Operation(String op, HashMap<String, Object> operationBody) {
        this.operation = op;
        this.operationBody = operationBody;
    }
     

    public String getOp() {
        return operation;
    }
    
    //public abstract void update(Operation operation);
    
    public static String printValues(String [] values){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(int i = 0; i < values.length; i++){
           builder.append("\""+values[i]+"\","); 
        }
        builder.delete(builder.length()-1, builder.length());
        builder.append("]");
        return builder.toString();
    }
    
    public static String printOperationBody(Operation op){
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for( String key : op.operationBody.keySet()){
                builder.append("\""+key+"\": "+ op.operationBody.get(key).toString() +",\n");
        }
        builder.delete(builder.length()-2, builder.length());
        builder.append("}");
        return builder.toString();
    }

    @Override
    public String toString() {
        return "\"op\": \""+operation+"\"";
    }

}
