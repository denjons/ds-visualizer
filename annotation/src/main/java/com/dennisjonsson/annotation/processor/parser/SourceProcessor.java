/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.processor.VisualizeProcessor;
import com.dennisjonsson.annotation.markup.Argument;
import com.dennisjonsson.annotation.markup.DataStructure;
import com.dennisjonsson.annotation.util.SourceFinder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.element.Element;
import javax.tools.StandardLocation;

/**
 *
 * @author dennis
 */
public abstract class SourceProcessor {
    
    protected String source, className;
    public final String originalClassName;
    protected ArrayList<DataStructure> dataStructures;
    private boolean written = false;
    protected Element print;
    protected Path fullPath;
    protected String rootDirectory;
    
    public static final String SUFFIX = "Visual";
    
    public SourceProcessor(String className) {
        this.className = className;
        this.originalClassName = className;
        this.dataStructures = new ArrayList<>();
    }

    public String getPrintingPath() {
        if(print == null){
            return "\"\"";
        }
        return "\""+print.getAnnotation(Print.class).path()+"\"";
    }
    
    public Element getPrintingMethod(){
        return print;
    }

    public void setPrint(Element print) {
        this.print = print;
    }
    
    public String getClassName(){
            return this.className;
    }
    
    public Path getPath(){
        return fullPath;
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
        source = readFile(this.fullPath);
    }
    
    public void writeSource(){
        String path = fullPath.toString();
        path = path.replace(originalClassName+".java", className +".java");
        createFile(path, source);
    }
    
    public abstract void processSource(Object arg);

    
    
    protected InputStream getInputStream(Path fullPath){
        InputStream stream = null;
        try {

            stream = 
                    Files.newInputStream(
                            fullPath,  
                            StandardOpenOption.READ);

        } catch (IOException ex) {
            Logger.getLogger(VisualizeProcessor.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage());
        }

        return stream;
    }

    public String readFile(Path fullPath){
        
        if(fullPath == null){
            throw new RuntimeException("path is null");
        }

        InputStream reader = getInputStream(fullPath);

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
    
    protected void createFile(String file, String source){
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file, "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VisualizeProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(VisualizeProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        writer.write(source);
        writer.close();


    } 
    
}
