/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.markup.DataStructure;
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
            source = source.replaceAll("\\@Visualize(\\((.|\\=)*\\))?+","");
            source = source.replaceAll("\\@(VisualClassPath)(\\([^\\(\\)]*\\))","");
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
        builder.append("new String [] {");
        for(DataStructure dataStructure : dataStructures){
            builder.append("\""+dataStructure.getAbstractType()+"\",");
            builder.append("\""+dataStructure.getType()+"\",");
            builder.append("\""+dataStructure.getIdentifier()+"\",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }
    
    public void insertInterceptionCalls(ArrayList<DataStructure> dataStructures){

        //source = source.replaceAll("data\\[","snata[");

        for(DataStructure dataStruct : dataStructures){

            String varName = dataStruct.getIdentifier();
            StringBuilder builder = new StringBuilder(source.length());
            String startBracket = "[";
            String endBracket = "]";
            int writeLength = 2;
            readArrayOperations(source, varName, builder, UUID.randomUUID(), startBracket, endBracket, writeLength);

            source = builder.toString();
        }

    }
    
     public void readArrayOperations(String source, String varName ,StringBuilder builder, 
            UUID id, String startBracket, String endBracket, int writeLength){

            int bracketLength = startBracket.length();
            if(source.length() <= varName.length()+bracketLength+endBracket.length()){
                    builder.append(source);
                    return;
            }
            int startIndex = 0;
            int index = source.indexOf(varName, startIndex);
            int length = varName.length();

            // go to next appearance of variable name
            while( index != -1 && source.length() - index > length){
                // check prefix
                if((index > 0 && source.substring(index - 1, index + length)
                                        .matches("[^a-zA-Z0-9]"+varName)) || index == 0){			
                    int i = nextChar( index + length, source);
                    boolean hasBrackets = false;

                    int count = 0; 
                    if(source.substring(i,i + bracketLength).equalsIgnoreCase(startBracket)){
                        builder.append(source.substring(startIndex, i-length));
                        builder.append("eval(\""+id.toString()+"\", ");
                        builder.append(source.substring(i-length, i));
                        startIndex = i;

                    }
                    // check if next char is '['
                    while(i < source.length() && source.substring(i,i + bracketLength).equalsIgnoreCase(startBracket)){
                            hasBrackets = true;
                            // append starting (
                            builder.append(source.substring(startIndex, i + bracketLength) + 
                                    "read("
                                    + "\""+varName+"\",\n"
                                    + "\""+id.toString()+"\",\n"
                                    + ""+count+",");
                            count ++;
                            // find closing bracket
                            int j = findNextMatch(startBracket,endBracket,i+bracketLength, source);
                            // recursively handle source inside brackets
                            readArrayOperations(source.substring(i + bracketLength, j - 1), varName, builder, UUID.randomUUID(),startBracket,  endBracket, writeLength);
                            // append ending ) and close brackets ] 
                            builder.append(")"+endBracket);
                            // go to next char
                            // problems with 'get(' and ')' must add 1 to j because of adding an extra ( for read(
                            if(endBracket.equalsIgnoreCase(")")){
                                i = nextChar( j+1, source);
                            }else{
                                i = nextChar( j, source);
                            }

                            index = j;
                            startIndex = index;
                    }

                    if(!hasBrackets){
                            builder.append(source.substring(startIndex, i ));
                            index = i ;
                            startIndex = index;
                    }else{
                        startIndex = handleWriteOperation(builder, varName, index, source, id, startBracket,  endBracket, writeLength);
                        if(source.length() > startIndex && 
                                source.substring(startIndex, 
                                        startIndex + 1).equalsIgnoreCase(";")){
                            builder.append(",logger.endStatement())");
                        }
                        else{
                            builder.append(",0)");
                        }
                    }

                }else{
                        builder.append(source.substring(startIndex, index + length));
                        index = index + length;
                        startIndex = index;
                }

               // startIndex = handleWriteOperation(builder, varName, index, source, id);
                 //       builder.append(")");
                //startIndex = index;
                // find next appearance of variable name
                index = source.indexOf(varName, startIndex);
                id  = UUID.randomUUID();
        }
        builder.append(source.substring(startIndex, source.length()));
    }

    /*
        used by readArrayOperations
        creates more read operations with the same uuid
        can be prosseed later to create write
    */

    private int handleWriteOperation(StringBuilder builder, String varName,int index, String source, UUID id, String startBracket, String endBracket, int writeLength){

        if(source.substring(index,source.length())
                .trim().length() <= writeLength+1){
             return index;
        }

        int i = nextChar( index, source);

        int j = index;
        if(source.substring(i,i+writeLength).matches(
                "(\\=(\\s|\\w|\\(|\\-|\\+)|"
                + "((\\+|\\-|\\*|\\/|\\%|\\&|\\^|\\|)\\=))"
                       )){

            i = i + writeLength;
            j = source.indexOf(";",i);
            builder.append(source.substring(index, i));
            builder.append("\nwrite(\""+varName+"\", "
                   + "\""+id.toString()+"\",");

            readArrayOperations(source.substring(i,j), 
                    varName, builder, id, startBracket, 
                    endBracket, writeLength);

            builder.append(")");

        }

        return j;
    }

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

    public void insertInterceptorMethods(ArrayList<DataStructure> dataStructures){
            MethodsSource methods = new MethodsSource();
            int i = source.lastIndexOf("}");

            StringBuilder builder = new StringBuilder();
            builder.append(source.substring(0,i)); 
            for(DataStructure struct : dataStructures){  
                builder.append(methods.getMethods(struct));     
            }
            builder.append(methods.getPrimitiveEvals());
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
