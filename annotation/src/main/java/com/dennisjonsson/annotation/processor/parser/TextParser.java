/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.markup.Argument;
import com.dennisjonsson.markup.DataStructure;
import com.dennisjonsson.markup.DataStructureFactory;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

/**
 *
 * @author dennis
 */
public class TextParser {
    
    private String source;

    public TextParser(String source) {
        this.source = source;
    }
    
    
    public String getSource(){
            return source;
    }
    
    public void removeAnnotations(){
            source = source.replaceAll("(\\@VisualizeArg|\\@Visualize|\\@SourcePath|\\@Print|\\@Include)(\\((.|\\=|\")*\\))?+","");
            /*
            source = source.replaceAll("\\@(SourcePath)(\\([^\\(\\)]*\\))","");
            source = source.replaceAll("\\@(Print)(\\([^\\(\\)]*\\))","");
            source = source.replaceAll("\\@(VisualizeArg)(\\([^\\(\\)]*\\))","");
            */
    }
    
     public void removePackage(){
       /*
        TODO: fix replacement
    */
       source = source.replaceFirst(
                //"package\\s++(\\w++\\.)*\\w(\\s)*\\;", 
                "package",
                "//");
    }

    public void renameClass(String className, String newName){
            rename("(\\)|\\(|\\{|\\}|\\n|\\s|)", "(\\(|\\{|\\}|\\n|\\s|)", className, newName);
           // className = newName;
           // source.replaceAll(className, newName);
    }
    
    public void renameType(String className, String newName){
            source = source.replaceAll(className, newName);
            int i = className.lastIndexOf(".") + 1;
            rename("(\\)|\\(|\\{|\\}|\\n|\\s|\\.)", "(\\(|\\{|\\}|\\n|\\s|\\.)", className.substring(i), newName);
           // className = newName;
           // source.replaceAll(className, newName);
    }
    
    
    public void insertField(String field, String className){
        source = source.replaceFirst("\\sclass\\s++"+className+"\\s*\\{", " class "+className+"{\n"+field);
        //insertAfter("\n"+field+"\n", "\\sclass\\s++QuickSortVisual\\s*\\{");
    }

    public void insertAfter(String str, String match){
       Scanner scanner = new Scanner(source);

        //throw new RuntimeException(match);
       String beginning = scanner.next(match);

       source = beginning + str + scanner.toString();

    }
    
    public String printDataStructures(ArrayList<DataStructure> dataStructures){
        StringBuilder builder = new StringBuilder();
        builder.append("new "+DataStructure.class.getName()+" [] {  ");
        for(DataStructure dataStructure : dataStructures){
           prindDataStructure(builder, dataStructure);
           builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }
    
    public void prindDataStructure(StringBuilder builder, DataStructure ds){
        builder.append(DataStructureFactory.class.getName()+"."+DataStructureFactory.METHOD+"(");
        builder.append("\""+ds.getAbstractType()+"\",");
        builder.append("\""+ds.getType()+"\",");
        builder.append("\""+ds.getIdentifier()+"\")");
    }
    
    public String printArguments(ArrayList<Argument> arguments){
        StringBuilder builder = new StringBuilder();
        builder.append("new "+Argument.class.getName()+" [] {  ");
        
        for(Argument argument : arguments){
            builder.append("new "+Argument.class.getName()+"(");
            builder.append("\""+argument.method+"\",");
            builder.append(argument.position+",");
            prindDataStructure(builder,argument.dataStructure);
            builder.append("),");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }
    
    /*
        used by readArrayOperations
        creates more read operations with the same uuid
        can be prosseed later to create write
    */

    /*
            rename any variables with matching contexts
    */
    public void rename(String begin, String end, String word, String replacement){

            StringBuilder builder = new StringBuilder(source.length());
            String [] sources = source.split(word);
            for(int i = 1; i < sources.length; i++){
                    int len = sources[i-1].length();
                    if( sources[i-1].substring(len - 1, len).matches(end) && // word x ....
                        sources[i].substring(0, 1).matches(begin) ){ // ... x word
                                    builder.append(sources[i - 1]+replacement);
                            }
                            else{
                                    builder.append(sources[i - 1]+word);
                            }

            }
            builder.append(sources[sources.length-1]);

            source = builder.toString();
    }

    public void insertInterceptorMethods(String className, ArrayList<DataStructure> dataStructures){
            MethodsSource methods = new MethodsSource(className);
            int i = source.lastIndexOf("}");

            StringBuilder builder = new StringBuilder();
            builder.append(source.substring(0,i)); 
            for(DataStructure struct : dataStructures){  
                builder.append(methods.getMethods(struct));     
            }
            builder.append(methods.getReadMethod());
            builder.append(source.substring(i, source.length()));
            
           source = builder.toString();
    }
    

    public int nextChar(int i, String source){
            while(i < source.length() && source.substring(i,i + 1).equalsIgnoreCase(" ")){
                    i++;
            }
            return i;
    }

    public static int findNextMatch(String open, String close, int i, String source){
            int opening = 1;
            int closing = 0;

            while(opening != closing ){
                    if(source.substring(i,i+1).equalsIgnoreCase(open)){
                            opening ++;
                    }
                    if(source.substring(i,i+1).equalsIgnoreCase(close)){
                            closing ++;
                    }
                    i++;
            }

            return i;
    }
    
    public static int countOcurences(String source, String occurence){
        int result = 0;
        int i = source.indexOf(occurence, 0);
        while(i >= 0){
            result ++;
            i = source.indexOf(occurence, i+1);
        }
        return result;
    }
    
    public static int countHighLeveOccurences(String source, 
            String open, String close){
        int result = 0;
        int i = source.indexOf(open, 0);
        while(i >= 0){
            result ++;
            i = source.indexOf(open, findNextMatch(open, close, i+1, source));
        }
        return result;
    }


    public void replace(String old, String replacement){
        source = source.replace(old, replacement);
   
    }
    
    
    
}
