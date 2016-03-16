/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.annotation.processor.VisualizeProcessor;
import com.dennisjonsson.markup.DataStructure;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dennis
 */
public abstract class SourceProcessor {
    
    protected final String path;
    protected String source, className;
    protected ArrayList<DataStructure> dataStructures;
    private boolean written = false;
    
    public String getClassName(){
            return this.className;
    }
    
    public String getPath() {
        return path;
    }

    public ArrayList<DataStructure> getDataStructures() {
        return dataStructures;
    }
    
    public void addDataStructure(DataStructure dataStructure){
            dataStructures.add(dataStructure);
    }

    public void written(){
        this.written = true;
    }

    public boolean isWritten() {
        return written;
    }
    
    public void loadSource(){
        source = readFile(path, className);
    }
    
    public void writeSource(){
        createFile(path, className, source);
    }
    
    public abstract void processSource(Object arg);

    public SourceProcessor(String path, String className, ArrayList<DataStructure> dataStructures) {
        this.path = path;
        this.className = className;
        this.source = source;
        this.dataStructures = dataStructures;
    }
    
    protected InputStream getInputStream(String path, String className){
        InputStream stream = null;
        try {

            stream = 
                    Files.newInputStream(
                            Paths.get(path+className+".java"),  
                            StandardOpenOption.READ);

        } catch (IOException ex) {
            Logger.getLogger(VisualizeProcessor.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage());
        }

        return stream;
    }

    protected String readFile(String path, String className){

        InputStream reader = getInputStream(path, className);

        StringBuilder builder = new StringBuilder();
        if(reader != null){
            Scanner scanner = new Scanner(reader);

            while(scanner.hasNext()){
                builder.append(scanner.nextLine());
                builder.append("\n");
            }
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(VisualizeProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return builder.toString();
    }
    
    protected void createFile(String path, String name, String source){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path+name+".java", "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VisualizeProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(VisualizeProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        writer.write(source);
        writer.close();


    } 
    
}
