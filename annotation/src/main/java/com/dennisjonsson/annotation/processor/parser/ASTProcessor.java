/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.annotation.log.AbstractInterpreter;
import com.dennisjonsson.annotation.log.DefaultInterpreter;
import com.dennisjonsson.annotation.log.ast.ASTLogger;
import com.dennisjonsson.annotation.log.ast.SourceHeader;
import com.dennisjonsson.annotation.markup.Argument;
import com.dennisjonsson.annotation.markup.DataStructure;
import com.dennisjonsson.annotation.markup.Header;
import com.dennisjonsson.annotation.markup.Method;
import com.dennisjonsson.annotation.util.ADVicePrinter;
import com.dennisjonsson.annotation.util.PathFormatter;
import com.dennisjonsson.annotation.util.SourceFinder;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dennis
 */
public class ASTProcessor extends SourceProcessor {
    
    private ClassLevelParser adapter;
    private CompilationUnit unit;
    public HashMap<String, Method> methods;
    public final String fullName;
    public ArrayList<String> includes;
    
    private String interpreterClass = DefaultInterpreter.class.getName();
    
    ASTProcessor(String className, String fullName) {
        super(className);
        this.fullName = fullName;
        methods = new HashMap<>();
        includes = new ArrayList<>();
    }
     
    public String getExecutableName(){
        return fullName + SUFFIX;
    }
    
    public void setInterpreter(String interpreterClass){
        this.interpreterClass = interpreterClass;
    }
    
    public void setIncludes(ArrayList<String> includes){
        this.includes = includes;
    }
    
    public void addArgument(Argument arg){
        if(methods.get(arg.method.name) == null){
            methods.put(arg.method.name, arg.method);
        }
        methods.get(arg.method.name).addArgument(arg);
    }
    
    public void addAllArguments(ArrayList<Argument> args){
        for(Argument arg : args){
            addArgument(arg);
        }
    }
    
    public void AddMethod(Method method){
        if(!methods.containsKey(method.name)){
            this.methods.put(method.name, method);
        }
    }

    public void addMethods(HashMap<String, Method> methods){
        
        this.methods = methods;
    }
    
    public void setPath(String path){
        
        if(path == null){     
            throw new RuntimeException("Source path is  null.\n"
                    + "Please provide a path to your project "
                    + "source using @SourcePath(path = your/path/to/source)");
        }
        
        this.rootDirectory = path;
        Path root = FileSystems.getDefault().getPath(path);
        SourceFinder sf = new SourceFinder(className+".java", fullName);
        
        try {
            Files.walkFileTree(root, sf); 
            this.fullPath = sf.result;
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        
    }

    @Override
    public void loadSource() {
        unit = readFile();
    }

   CompilationUnit readFile(){
       
       ADVicePrinter.info("loading source: "+this.fullPath.toString());
       InputStream stream = this.getInputStream(this.fullPath);
       
       CompilationUnit unit = null;
       
        try {
            unit = JavaParser.parse(stream);
        } catch (ParseException ex) {
            Logger.getLogger(ASTProcessor.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ASTProcessor: Could not parse file"); 
        }finally{
           try {
               stream.close();
           } catch (IOException ex) {
               Logger.getLogger(ASTProcessor.class.getName()).log(Level.SEVERE, null, ex);
               
           }
        }
        
       return unit;
   }
   
    private void replaceIncludes(TextParser parser){
        for(String incl : includes){
            if(!incl.equalsIgnoreCase(fullName)){
                String classname =incl.replaceAll("(\\w*\\.)", "");
                parser.renameType(classname, classname + SUFFIX);
                ADVicePrinter.info("including: "+incl+" -> "+(incl + SUFFIX));
            }
        }
    }
    
    private Object [] getSourceLines(String str){
        //Scanner scanner = new Scanner(str);
        ArrayList<String> lines = new ArrayList<>();
        // all empty lines are needed
        final BufferedReader br = new BufferedReader(new StringReader(str));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                lines.add(line.replaceAll("\\\\", ""));
            }
        } catch (IOException ex) {
            Logger.getLogger(ASTProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        while(scanner.hasNext()){
            String n = scanner.next("\\n");
            if(n != null){
                lines.add(n);
            }
            else{
                lines.add(scanner.nextLine());
                return lines.toArray();
            }
            
        }*/
        return lines.toArray();
    }
    
    public String getSourceLinesAsString(String str){
        Object [] lines = getSourceLines(str);
        StringBuilder builder = new StringBuilder();
        builder.append("new String [] { ");
        for(Object line : lines){
            builder.append("\"");
            builder.append(line.toString().replaceAll("\"", "'"));
            builder.append("\",");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("}");
        return builder.toString();
    }
    
    public void concatClassName(String className){
        for(DataStructure ds : dataStructures){
            ds.identifier =  className + Header.CONCAT + ds.identifier;
        }
    }

    @Override
    public void processSource(Object arg) {
        
        if(unit == null){
            throw new RuntimeException("ASTPRocessor: CompilationUnit is null");
        }
       
        String newClass = className + SUFFIX;
        String lines = getSourceLinesAsString(unit.toString());
        
        // pre parsing
        PreParser pp = new PreParser(
                methods, 
                className, 
                fullName
        );
        
        pp.visit(unit, new ASTArgument());
   
        //concatClassName(className);
        // main parsing
        adapter = new ClassLevelParser(
                className, 
                fullName,
                dataStructures, 
                getPrintingMethod(), 
                methods
        );
        
        adapter.visit(unit, new ASTArgument());
        
        // post parsing
        PostParser postParser = new PostParser(
                className, 
                fullName,
                dataStructures, 
                includes, 
                (LinkedList<ImportDeclaration>)unit.getImports(),
                unit.getPackage().getName().toString()
        );
        
        postParser.visit(unit, new ASTArgument());

        // textual changes
        TextParser parser = new TextParser(unit.toString());
        parser.renameClass(className, newClass);
        
        String oldClassName = className;
        className = newClass;
        
        parser.removeAnnotations();
        // replace type of included sources
        //replaceIncludes(parser);
        parser.insertInterceptorMethods(className, dataStructures);
        
        System.out.println("root dir: "+rootDirectory);
        parser.insertField("public static "+ASTLogger.class.getName()+" logger = \n"
                +   ASTLogger.class.getName()+".instance("
                +   "new "+SourceHeader.class.getName()+"("
                +   "\""+newClass+"\","
                +   ""+lines+","
                +   getPrintingPath()+","
                +   parser.printDataStructures(dataStructures) +","
                +   "new " + interpreterClass + "(),"
                +   "\""+rootDirectory.replaceAll("\\\\", ".")+"\""
                + "));", className);
        
        source = parser.getSource();
     
        
    }
    
   
   
        
    
}
