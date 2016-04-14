/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.markup.Argument;
import com.dennisjonsson.markup.DataStructure;
import com.dennisjonsson.markup.Method;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author dennis
 */
public class PreParser extends ModifierVisitorAdapter{
    
    final HashMap<String, Method> methods;

    public PreParser( HashMap<String, Method> methods) {
        this.methods = methods;
    }
    

    @Override
    public Node visit(MethodDeclaration n, Object arg) {
        

        String m = n.getDeclarationAsString(false, false,true);
        String [] parts = m.replaceAll("(\\[|\\])","")
                .substring(m.indexOf(" ")+1).trim().split("\\(");
        
        String name = parts[0].trim();
        String arguments = parts[1].trim();
        
        arguments = arguments.substring(0, arguments.length() -1);
        
        if(arguments.length() <= 2 ){
            return super.visit(n, arg);
        }
        
        String [] argList = arguments.split(",");
        String [] typeList = new String[argList.length];
        
        for(int i = 0; i < argList.length; i++){
            int p = argList[i].lastIndexOf(" ");
            String temp = argList[i];
            argList[i] = temp.substring(p+1).trim();
            typeList[i] = temp.substring(0, p).trim();
        }
        
        Method method = methods.get(name);
        
        if(method != null){
            
            method.compareTypes(typeList);
            
            for(Argument argument : method.annotetedArguments){
                argument.name = argList[argument.position];
                argument.dataStructure.identifier = argument.name;
                //System.out.println("got name: "+argument.name);
            }
        }
        
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
