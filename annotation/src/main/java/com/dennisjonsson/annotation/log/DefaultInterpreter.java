/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.log;

import com.dennisjonsson.annotation.markup.ArrayEntity;
import com.dennisjonsson.annotation.markup.Entity;
import com.dennisjonsson.annotation.markup.Operation;
import com.dennisjonsson.annotation.markup.Read;
import com.dennisjonsson.annotation.markup.Write;
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
    
  
    //LogStreamManager manager = new LogStreamManager(false);

    public DefaultInterpreter() {
    }

    @Override
    public void interpret(String className, Operation operation) {
        //Operation op = this.classes.get(className).body.get(position);
        /*
        if(op.operation.equalsIgnoreCase(Write.OPERATION)){
           // Write write = (Write)op;
         
            OP_Write r= new OP_Write();
            setLocators(r,write.getSource(),write.getTarget());
            manager.stream(r);
            // do your work here
           // System.out.println(Write.OPERATION + " to "+write.getTarget().getId());
        }
        else if(op.operation.equalsIgnoreCase(Read.OPERATION)){
            //Read read = (Read)op;
            
            OP_Read r= new OP_Read();
            setLocators(r,read.getSource(),read.getTarget());
            manager.stream(r);
            //System.out.println(Read.OPERATION + " from "+read.getSource().getId());
            // do your work here
        }
        */
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

    @Override
    public void print(String json) {
       
    }
    
}
