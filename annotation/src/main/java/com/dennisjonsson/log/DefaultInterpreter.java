/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log;

import com.dennisjonsson.markup.ArrayEntity;
import com.dennisjonsson.markup.Entity;
import com.dennisjonsson.markup.Operation;
import com.dennisjonsson.markup.Read;
import com.dennisjonsson.markup.Write;
/*
import manager.LogStreamManager;
import manager.operations.OP_Read;
import manager.operations.OP_ReadWrite;
import manager.operations.OP_Write;
import wrapper.Locator;
*/
/**
 *
 * @author dennis
 */
/*
        Interpreter classes should have the following properties:
            extend: com.dennisjonsson.log.AbstractInterpreter
            method: public static AbstractInterpreter instance();
*/
public class DefaultInterpreter extends AbstractInterpreter {
    
    public static AbstractInterpreter interperter;
    
    //LogStreamManager manager = new LogStreamManager(false);
    
    public static AbstractInterpreter instance(){
        if(interperter == null){
            interperter = new DefaultInterpreter();
        }
        return interperter;
    }

    private DefaultInterpreter() {
    }

    @Override
    public void interpret(String className, int position) {
        Operation op = this.classes.get(className).body.get(position);
        
        if(op.operation.equalsIgnoreCase(Write.OPERATION)){
            Write write = (Write)op;
            /*
            OP_Write r= new OP_Write();
            setLocators(r,write.getSource(),write.getTarget());
            manager.stream(r);*/
            // do your work here
            System.out.println(Write.OPERATION + " to "+write.getTarget().getId());
        }
        else if(op.operation.equalsIgnoreCase(Read.OPERATION)){
            Read read = (Read)op;
            /*
            OP_Read r= new OP_Read();
            setLocators(r,read.getSource(),read.getTarget());
            manager.stream(r);*/
            System.out.println(Read.OPERATION + " from "+read.getSource().getId());
            // do your work here
        }
        
    }
    
    /*
    private void setLocators(OP_ReadWrite op, Entity source, Entity target){
        
        if(source instanceof ArrayEntity){
             
            op.setSource((new Locator(source.getId(), ((ArrayEntity)source).getIndex())));
        }else{
            op.setSource((new Locator(source.getId(), new int[]{0})));
        }
        
        if(target instanceof ArrayEntity){
             
            op.setTarget((new Locator(target.getId(), ((ArrayEntity)target).getIndex())));
        }else{
            op.setTarget((new Locator(target.getId(), new int[]{0})));
        }
       
    }*/
    
}
