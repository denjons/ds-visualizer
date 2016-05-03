/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.annotation.markup.ArrayDataStructure;
import com.dennisjonsson.annotation.markup.DataStructure;
import static com.dennisjonsson.annotation.processor.parser.Parser.IS_ASSIGNMENT;
import static com.dennisjonsson.annotation.processor.parser.Parser.IS_BINARY;
import static com.dennisjonsson.annotation.processor.parser.Parser.IS_DECLARATION;
import static com.dennisjonsson.annotation.processor.parser.Parser.IS_UNARY;
import static com.dennisjonsson.annotation.processor.parser.Parser.SKIP;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import java.util.ArrayList;

/**
 *
 * @author dennis
 */
public class ExprLevelParser extends Parser {
    
    private String methodScope = null;
    private ArrayList<String> parameters;
    private MainParser mainParser;
    
    public ExprLevelParser(Parser parser, MainParser mainParser) {
        super(parser);
        this.mainParser = mainParser;
    }
    
    @Override
    public Node visit(ArrayAccessExpr n, Object arg) {
        ASTArgument astArg = (ASTArgument)arg;
        
        if(astArg.peek() == SKIP){
            astArg.pop();
            return super.visit(n, astArg);
        }
       
        /*
        if(astArg.peek() == IS_UNARY ){
            return super.visit(n, arg);
        }*/

        DataStructure dataStructure = mainParser.isAnnotated(n.getName());
        //System.out.println(n.toString());
        if(dataStructure != null && 
                TextParser.countHighLeveOccurences(n.toString(),"[", "]") == 1){
            if(astArg.stack.contains(IS_BINARY)){
                astArg.push(SKIP);
                return super.visit(mainParser.setEval(n), astArg);
            }
        }
        return super.visit(n, astArg);
    }
    
    public void setRead(DataStructure dataStructure, ArrayAccessExpr n,  ASTArgument astArg){
        String name = n.getName().toString();
        int dimension = TextParser.countHighLeveOccurences(name,"[", "]");

        ArrayList<Expression> args = new ArrayList<>();
        args.add(new StringLiteralExpr(dataStructure.getIdentifier()));
        args.add(new IntegerLiteralExpr(""+dimension));
        args.add(n.getIndex());
        n.setIndex(new MethodCallExpr(null, "read", args));
    }
    
    
  
    
    
    
}
