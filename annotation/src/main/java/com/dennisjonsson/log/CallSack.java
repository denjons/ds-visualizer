/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log;

import com.dennisjonsson.markup.Operation;
import com.dennisjonsson.markup.Read;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author dennis
 */
public class CallSack {
    
    int size;
    ArrayList<String> calls;
    HashMap<String, Stack<LogOperation>> callStacks;

    public CallSack() {
        size = 0;
        callStacks = new HashMap<>();
    }
    
    public void newAccess(String id, LogOperation operation){
        init(id);
        callStacks.get(id).push(operation);
        size ++;
    }
    
    public void init(String id){
        if(!callStacks.containsKey(id)){
            callStacks.put(id, new Stack<LogOperation>());
        }
    }
    
    public void updateLastAccess(IndexRead read){
        ((ReadOperation)callStacks.get(read.identifier).peek())
                .extendIndex(read.index);
    }
    
    
}
