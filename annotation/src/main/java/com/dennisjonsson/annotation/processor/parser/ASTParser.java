/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.markup.DataStructure;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author dennis
 */
public class ASTParser extends ModifierVisitorAdapter
{
    ArrayList<DataStructure> dataStructures;

    public ASTParser(ArrayList<DataStructure> dataStruct) {
        this.dataStructures = dataStruct;
    }
    
   
    public void visitArray(ArrayAccessExpr n, Object arg, String expressionId){
    
    }
   
    @Override
    public Node visit(ArrayAccessExpr n, Object arg) {
        
        ExpressionDetails details = null;
        
        if(arg instanceof ExpressionDetails){
           details = (ExpressionDetails)arg;
        }else{
            details = new ExpressionDetails();
            details.scope = null;
            details.statementId = UUID.randomUUID().toString();
        }

        for(DataStructure datastructure : dataStructures){
            
            if(n.getName().equals(datastructure.getIdentifier())){
                
                ArrayList<Expression> arguments = 
                        new ArrayList<Expression>();
                
                arguments.add(new StringLiteralExpr(details.statementId));
                arguments.add((Expression)visit(n, arg));
                arguments.add(new IntegerLiteralExpr("0"));
                
                return new MethodCallExpr(
                        details.scope, 
                        details.interceptionMethod, 
                        arguments);
            }
        }
       
        return super.visit(n, arg);
    }
    
  

    @Override
    public Node visit(UnaryExpr n, Object arg) {
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node visit(AssignExpr n, Object arg) {
        
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
}
