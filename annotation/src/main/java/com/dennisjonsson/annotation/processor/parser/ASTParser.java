/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.markup.DataStructure;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.EmptyMemberDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.AssertStmt;
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

   
    @Override
    public Node visit(ArrayAccessExpr n, Object arg) {
        System.out.println("ArrayAccessExpr: accessed: "+n.getName().toString() + n.getIndex().toString());  
        return super.visit(n, arg);
    }

    @Override
    public Node visit(ArrayInitializerExpr n, Object arg) {
        
        for(Expression ex : n.getValues()){
            if(ex instanceof ArrayAccessExpr){
                ArrayAccessExpr aaExp = (ArrayAccessExpr)ex;
                System.out.println("ArrayInitializerExpr: accessed "+
                        aaExp.getName().toString());
            }
        }

        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }

  
    @Override
    public Node visit(VariableDeclarator n, Object arg) {
        System.out.println("VariableDeclarator: declaring id "+n.getId().getName());

        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node visit(VariableDeclarationExpr n, Object arg) {
        for(VariableDeclarator dec : n.getVars()){
           // dec.getId().getName()
        }
        //System.out.println("inside visit: annotations: "+n.getAnnotations().size());
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.

        
    }

    @Override
    public Node visit(AssignExpr n, Object arg) {
        if(n.getValue() instanceof ArrayAccessExpr){
            ArrayAccessExpr aaExp = (ArrayAccessExpr)n.getValue();
            System.out.println("AssignExpr: accessed: "+aaExp.getName().toString());
        }
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public Node visit(BlockComment n, Object arg) {
        return null;
    }

    @Override
    public Node visit(LineComment n, Object arg) {
        return null;
    }
    
    
    
    
    
    

    
    
}
