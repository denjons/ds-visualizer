/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.annotation.markup.DataStructure;
import com.dennisjonsson.annotation.markup.Method;
import static com.dennisjonsson.annotation.processor.parser.MainParser.PRINT_METHOD;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.lang.model.element.Element;

/**
 *
 * @author dennis
 */
public class ClassLevelParser extends Parser {
    
    MainParser parser;

    public ClassLevelParser(
            String className, 
            String fullClassName, 
            ArrayList<DataStructure> dataStruct, 
            Element printMethod, 
            HashMap<String, Method> methods) {
        
        super(className, fullClassName, dataStruct, printMethod, methods);
        parser = new MainParser(this);
        
    }

    @Override
    public Node visit(FieldDeclaration n, Object arg) {
        parser.visit(n, new ASTArgument());
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Node visit(MethodDeclaration n, Object arg) {
        //System.out.println(n.getDeclarationAsString(false, false, true));
        
        setPrintMethod(n);
        parseMethodBody(n, arg);
        return super.visit(n, arg); 
    }
    
     @Override
    public Node visit(Parameter n, Object arg) {
        //System.out.println(n.getType() + " " + n.getId());
        
        
        // check this out
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    
    private ArrayList<String> getArguments(String declaration){
        ArrayList<String> args = new ArrayList<>();
        String [] pieces = declaration.split(" ");
        if(pieces.length > 2){
            args.add(pieces[pieces.length-1]);
            for(int i = 0; i < pieces.length-1; i++){
                if(pieces[i].contains(",")){
                    args.add(pieces[i]);
                }
            }
            String arg;
            for(int i = 0; i < args.size(); i++){
                arg = args.get(i);
                int j = arg.lastIndexOf(" ")+1;
                args.set(i, arg.substring(j, arg.length()).replaceAll("(\\[|\\]|\\)|\\,)", "").trim());
               // System.out.println(args.get(i));
            }
        }

        return args;
    }
    
    private void parseMethodBody(MethodDeclaration n, Object arg){
        parser.setMethodScope(n.getName());
        parser.setMethodParamaters(
                getArguments(n.getDeclarationAsString(false, false, true))
        );
        parser.visit(n, new ASTArgument());
        parser.setMethodScope(null);
        
    }
    
    private void setPrintMethod(MethodDeclaration n){
        
        if(printMethod == null){
            return; 
        }
        
        String name = n.getName();
        String print = printMethod.toString().replaceAll("\\(.*\\)", "");
        //System.out.println(name + ", "+print);
        if(name.equalsIgnoreCase(print)){
            ArrayList<Expression> args = new ArrayList<>();
            n.getBody().getStmts().add(new ExpressionStmt (new MethodCallExpr(null, PRINT_METHOD, args)));
        }
    }
    
}
