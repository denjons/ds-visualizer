/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.app;

import com.dennisjonsson.annotation.Interpreter;
import com.dennisjonsson.annotation.log.AbstractInterpreter;
import com.dennisjonsson.annotation.markup.Entity;
import com.dennisjonsson.annotation.markup.Operation;
import com.dennisjonsson.annotation.markup.Read;
import com.dennisjonsson.annotation.markup.Write;
import io.LogStreamManager;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dennis
 */
@Interpreter
public class MyInterpreter extends AbstractInterpreter {

    @Override
    public void interpret(String className, Operation operation) {
        /*
        if(operation instanceof Write){
            Write write = (Write)operation;
            Entity entity = write.getTarget();
            // är i våra exempel alltid Integer
            Object value = write.getValue();
            String id = entity.getId();
        }else if(operation instanceof Read){
            Read read = (Read)operation;
            
        }*/
       // System.out.println(className+ " " +operation.operation);
    }

    @Override
    public void print(String json) {
        //openWeb();
        //openLogStream(json);
        
    }
    
    private void openLogStream(String json){
        System.out.println(json);
        LogStreamManager manager = new LogStreamManager("java annotations", false);
        manager.stream(json);
        manager.close();
    }

    private void openWeb(){
        try {
            Desktop.getDesktop().browse(
                    new URI("http://blog-dennisjonsson.rhcloud.com/visualizer/visualizer.html"));
            System.out.println("opening: "+rootDirectory);
            Desktop.getDesktop().open(new File(rootDirectory));
            System.out.println("printing!");

        } catch (IOException ex) {
            Logger.getLogger(MyInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MyInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
