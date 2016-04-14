/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.markup;

import java.util.ArrayList;

/**
 *
 * @author dennis
 */
public class Method {
    
    public final String name;
    public final String className;
    public final String [] arguments;
    public final String signature;
    public final ArrayList<Argument> annotetedArguments;
    
    public final String uniqueSignature;

    public Method(String className, String name, String signature) {
        this.name = name;
        this.className = className;
        this.signature = signature;
        this.arguments = signature.replaceAll("(.*\\()|\\)", "").split(",");
        this.annotetedArguments = new ArrayList<>();
        this.uniqueSignature = className + signature;
    }
    
    public void addArgument(Argument arg){
        arg.method = this;
        annotetedArguments.add(arg);
    }
    
    public boolean compareTypes(String [] types){
        if(arguments.length != types.length){
            return false;   
        }
            
        for(int i = 0; i < types.length; i++){
            String t1 = types[i];
            t1 = t1.substring(t1.lastIndexOf(".")+1)
                    .replaceAll("(\\[|\\])", "").trim();
            String t2 = arguments[i];
            t2 = t2.substring(t2.lastIndexOf(".")+1)
                    .replaceAll("(\\[|\\])", "").trim();
            if(!t1.equalsIgnoreCase(t2)){
                return false;
            }
        }
        return true;
    }
    
    
    
}
