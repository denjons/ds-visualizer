/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.exec;

import com.dennisjonsson.annotation.IVisualMain;
import com.dennisjonsson.annotation.processor.VisualizeProcessor;
import com.dennisjonsson.annotation.processor.parser.ASTProcessor;
import com.dennisjonsson.annotation.util.ADVicePrinter;
import com.dennisjonsson.annotation.util.PathFormatter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dennis
 */
public class ProgramExecutor {
    
    final ASTProcessor source;
    

    public ProgramExecutor(ASTProcessor source) {
        this.source = source;
    }
    
    private String getClassPath(){
        
        // maven project 
        String path = PathFormatter.toMavenClassPath(source.getPath().toString());
        int i = path.lastIndexOf(PathFormatter.classes);
        return path.substring(0, i + PathFormatter.classes.length());
        //path = path.substring(0,path.length() - 1);
        /*
        if(Files.exists(Paths.get(path))){
            return path;
        }*/
        
        // return anyway
        
        
        // not project
        /*
        path = source.getPath().toString();
        int i = Math.max(path.lastIndexOf("\\"), path.lastIndexOf("/")) + 1;
        return source.getPath().toString().substring(0, i);
        */
        
    }
    
    public void execute(String [] arguments){
        
  
        /*
        executeProgram(
        "java",
        source.getExecutableName(),
        getClassPath());*/
    
    }
    
    private void executeProgram(String [] arguments){
        
        
        try {
            Class<?> clazz = Class.forName(source.fullName);
            IVisualMain visualMain = (IVisualMain)clazz.newInstance();
            visualMain.run(arguments);
        } catch (InstantiationException ex) {
            Logger.getLogger(ProgramExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProgramExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProgramExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void executeProgram(){
        
        
        try {
            Class<?> clazz = Class.forName(source.fullName);
            IVisualMain visualMain = (IVisualMain)clazz.newInstance();
            visualMain.run();
        } catch (InstantiationException ex) {
            Logger.getLogger(ProgramExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProgramExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProgramExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ADVicePrinter.info("executing: "+source.fullName);
        try {
            Class exeClass = Class.forName(source.fullName);
            exeClass.getMethod("main", String[].class.getClass()).invoke(this);
            
            //Constructor c = exeClass.getConstructor();
            //c.newInstance();

        } catch (ClassNotFoundException ex) {
            ADVicePrinter.error(ClassNotFoundException.class.getSimpleName()+": "+ ex.getMessage());
        } catch (NoSuchMethodException ex) {
            ADVicePrinter.error(NoSuchMethodException.class.getSimpleName()+": "+ ex.getMessage());
        } catch (SecurityException ex) {
            ADVicePrinter.error(SecurityException.class.getSimpleName()+": "+ ex.getMessage());
        } catch (IllegalAccessException ex) {
            ADVicePrinter.error(IllegalAccessException.class.getSimpleName()+": "+ ex.getMessage());
        } catch (IllegalArgumentException ex) {
            ADVicePrinter.error(IllegalArgumentException.class.getSimpleName()+": "+ ex.getMessage());
        } catch (InvocationTargetException ex) {
            ADVicePrinter.error(InvocationTargetException.class.getSimpleName()+": "+ ex.getMessage());
        }/* catch (InstantiationException ex) {
            ADVicePrinter.error(ex.getMessage());
        } */
    }
    
    public void executeProgram(String exec, String command, String classPath){
        try {
            String path = "-cp \""+classPath+"\" ";
            System.out.println("class path: "+classPath);
            String execString =  exec + " " + path + " " + command;
            System.out.println("running: "+execString);
            Process pro = Runtime.getRuntime().exec(execString);
            printLines(command + " stdout:", pro.getInputStream());
            printLines(command + " stderr:", pro.getErrorStream());
            pro.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(VisualizeProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VisualizeProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
    public static  void printLines(String name, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
            new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(name + " " + line);
        }
    }
    
}
