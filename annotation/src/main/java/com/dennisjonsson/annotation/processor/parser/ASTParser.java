/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.log.ast.EvalOperation;
import com.dennisjonsson.log.ast.WriteOperation;
import com.dennisjonsson.markup.AbstractType;
import com.dennisjonsson.markup.ArrayDataStructure;
import com.dennisjonsson.markup.DataStructure;
import com.dennisjonsson.markup.DataStructureFactory;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import java.util.ArrayList;

/**
 *
 * @author dennis
 */
public class ASTParser extends ModifierVisitorAdapter
{
    ArrayList<DataStructure> dataStructures;
    ArrayList<DataStructure> uknowns;
    
    public static final String SKIP = "skip";
    public static final String IS_ASSIGNMENT = "assignment";
    public static final String IS_DECLARATION = "declaration";
    public static final String IS_BINARY = "binary";
    public static final String CONTINUE = "continue";


    public ASTParser(ArrayList<DataStructure> dataStruct) {
        this.dataStructures = dataStruct;
        uknowns = new ArrayList<>();
       
    }
    
 
  
    public boolean arrayMatchesIdentifier(String identifier, String arrayAccess){
        return arrayAccess.matches(identifier+"(\\s*(\\[(.*)\\]))*");
    }
    
    public DataStructure isAnnotated(String identifier){
        for(DataStructure dataStructure : dataStructures ){
            if(arrayMatchesIdentifier(dataStructure.getIdentifier(),
                    identifier)){
                return dataStructure;
            }
        }
        return null;
    }
    
     /*
        highest branch
    */
    @Override
    public Node visit(ExpressionStmt n, Object arg) {
        
        Expression expr = n.getExpression();
        if(expr instanceof AssignExpr){
           setEval(n, (AssignExpr)expr, arg);
        }else if(expr instanceof VariableDeclarationExpr){
            setEval(n, (VariableDeclarationExpr)expr, arg);   
        }   
        return super.visit(n, null); 
    }
    
    /* 
                EVAL LEVEL
    */
    
    public void setEval(ExpressionStmt n, VariableDeclarationExpr vdExpr, Object arg){
        
        // eval(String statementId, "+primitiveType+" value, int statement)
        for(VariableDeclarator decl : vdExpr.getVars()){
            eval(decl);
   
        }
    }
    
    public void eval(VariableDeclarator decl){
        Expression init = decl.getInit();
        MethodCallExpr call = null;
        if( init instanceof ArrayAccessExpr){
            ArrayAccessExpr aaExpr = (ArrayAccessExpr)init;
            call = setWriteFromArray(aaExpr);

        }/*else if(init instanceof NameExpr){
            NameExpr varable = (NameExpr)init;
            call = setWtriteFromVariable(varable);
        }else{
            call = setWriteFromUndefined(init);
        }*/
        
        if(call == null){
            return;
        }

        call.getArgs().add(new IntegerLiteralExpr(WriteOperation.VARIABLE+""));

        ArrayList<Expression> args = new ArrayList<>();
        args.add(new StringLiteralExpr(decl.getId().toString()));
        args.add(call);
        args.add(new IntegerLiteralExpr(""+EvalOperation.ASSIGNMENT));
        decl.setInit(new MethodCallExpr(null, "eval", args));
    }
    
    public void setEval(ExpressionStmt n, AssignExpr aExpr, Object arg){
        // eval(String statementId, "+primitiveType+" value, int statement)
        if(!isTracked(aExpr)){
            return;
        }
        ArrayList<Expression> args = new ArrayList<>();
        args.add(new StringLiteralExpr(aExpr.getTarget().toString()));
        args.add(aExpr);
        args.add(new IntegerLiteralExpr(EvalOperation.ASSIGNMENT+""));
        n.setExpression(new MethodCallExpr(null, "eval", args));
    }
    
    public boolean isTracked(AssignExpr aExpr){
        Expression target = aExpr.getTarget();
        Expression value = aExpr.getValue();
        /*
        if(target instanceof NameExpr || 
            value instanceof NameExpr ){
            return true;
        }
        if(target instanceof ArrayAccessExpr &&
           isTracked((ArrayAccessExpr)target)){
            return true;  
        }
        if(value instanceof ArrayAccessExpr &&
                isTracked((ArrayAccessExpr)value)){
            return true;
        }
        return false;*/
        return (isTrackedArray(target) || isTrackedArray(value));
       
        
    }
    
    private boolean isTrackedArray(Expression expr){
        return ((expr instanceof ArrayAccessExpr) 
                && isTracked((ArrayAccessExpr)expr));
    }
    
    public boolean isTracked(ArrayAccessExpr aaExpr){
       return isAnnotated(aaExpr.getName().toString()) != null;
    }
    

    /* 
                WRITE LEVEL
    */
    
    @Override
    public Node visit(AssignExpr n, Object arg) {
       
        MethodCallExpr call = null;
        
        if(n.getValue() instanceof ArrayAccessExpr){ 
            ArrayAccessExpr aValueExp = (ArrayAccessExpr)n.getValue();
           // System.out.println("AssignExpr: accessed value: "+aValueExp.getName().toString()+" "+aValueExp.getIndex());
            call = setWriteFromArray((ArrayAccessExpr)n.getValue());
        }else if(n.getValue() instanceof NameExpr){
            call = setWtriteFromVariable((NameExpr)n.getValue());
        }else{
            call = setWriteFromUndefined(n.getValue());
        }
        
        if(call == null){
            return super.visit(n, IS_ASSIGNMENT);
        }
        
        if(n.getTarget() instanceof ArrayAccessExpr){
            seWriteToArray((ArrayAccessExpr)n.getTarget(), call);
        }else if(n.getTarget() instanceof NameExpr){
            setWriteToVariable((NameExpr)n.getTarget(), call);
        }else{
            call.getArgs().add(new StringLiteralExpr(WriteOperation.UNDEFINED+""));
        }
        
        // only add if one of source and target are interesting
        String from = call.getArgs().get(2).toString();
        String to = call.getArgs().get(3).toString();
        /*
        if(!( isUndefinedWrite(from) && isUndefinedWrite(to))){
           n.setValue(call); 
        }*/
        if(( isArray(from) || isArray(to))){
           n.setValue(call); 
        }

        return super.visit(n, IS_ASSIGNMENT);
    }
    
    private boolean isUndefinedWrite(String str){
        return str.equalsIgnoreCase(WriteOperation.UNDEFINED+"");
    }

    private boolean isArray(String str){
        return str.equalsIgnoreCase(WriteOperation.ARRAY+"");
    }
    
    @Override
    public Node visit(VariableDeclarator n, Object IS_DECLARAT) {
        return super.visit(n, IS_DECLARATION); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private MethodCallExpr createWrite(ArrayList<Expression> args){
        return new MethodCallExpr(null, "write",args);
    }
    
    /*
        UNDEFINED WRITE
    */
    
    private MethodCallExpr setWriteFromUndefined(Expression exp){
        ArrayList<Expression> args = new ArrayList<>();
        args.add(new StringLiteralExpr("undefined"));
        args.add(exp);
        args.add(new IntegerLiteralExpr(WriteOperation.UNDEFINED+""));
        return createWrite(args);
    }
    
    /*
        ARRAY WRITE
    */
    
    private MethodCallExpr setWriteFromArray(ArrayAccessExpr aValueExp){

        DataStructure dataStructure = isAnnotated(aValueExp.getName().toString());
        
        if(dataStructure != null){
            ArrayList<Expression> args = new ArrayList<>(); 
            args.add(new StringLiteralExpr(dataStructure.getIdentifier()));
            args.add(aValueExp);
            args.add(new IntegerLiteralExpr(WriteOperation.ARRAY+""));
            return createWrite(args);
        }
  
        return setWriteFromUndefined(aValueExp);
        
    }
    
    
    
    public void seWriteToArray(ArrayAccessExpr aTargetExp, MethodCallExpr call){
        
        DataStructure dataStructure = 
                isAnnotated(aTargetExp.getName().toString());
        if(dataStructure!= null){
            call.getArgs().add(new IntegerLiteralExpr(WriteOperation.ARRAY+""));
        }else{
            call.getArgs().add(new IntegerLiteralExpr(WriteOperation.UNDEFINED+""));
        }

    }
    
    /*
        VARIABLE WRITE
    */
    
    public MethodCallExpr setWtriteFromVariable(NameExpr nExpr){
        ArrayList<Expression> args = new ArrayList<>();  
        args.add(new StringLiteralExpr(nExpr.getName()));
        args.add(nExpr);
        args.add(new IntegerLiteralExpr(WriteOperation.VARIABLE+""));
        return createWrite(args);
    }
    
    public void setWriteToVariable(NameExpr nExpr, MethodCallExpr call ){
        call.getArgs().add(
            new IntegerLiteralExpr(WriteOperation.VARIABLE+""));
        
    }
    
    
    public void register(String identifier){
       // PrimitiveDataStructure(String abstractType, String type, String name)
        uknowns.add(DataStructureFactory.getDataStructure(
                AbstractType.UNKNOWN.toString(), 
                "unknown", identifier));
    }

    
    /* 
                READ LEVEL
    */
   
    
    @Override
    public Node visit(ArrayAccessExpr n, Object arg) {
        
        
        if(arg != null && arg instanceof String){
            //System.out.println(arg+" : "+n.toString());
            String str = (String)arg;
            
            if(str.equalsIgnoreCase(SKIP)){
                return super.visit(n, CONTINUE);
            }
        }else{
            //System.out.println(arg+" : "+n.toString() );
        }
        
        // skip all which are outside of assignments and declarations
        if(arg == null){
            return super.visit(n, arg);
        }
        
        DataStructure dataStructure = isAnnotated(n.getName().toString());
        
        if(dataStructure != null){
            ArrayDataStructure ads = (ArrayDataStructure) dataStructure;
            
            String name = n.getName().toString();
            int dimension = TextParser.countHighLeveOccurences(name,"[", "]");

            ArrayList<Expression> args = new ArrayList<>();
            args.add(new StringLiteralExpr(dataStructure.getIdentifier()));
            args.add(new StringLiteralExpr(""));
            args.add(new IntegerLiteralExpr(""+dimension));
            args.add(n.getIndex());
            n.setIndex(new MethodCallExpr(null, "read", args));
            //System.out.println(" "+ads.getDimensions() +", "+ dimension + 1);
            
            /*
            if((ads.getDimensions() == dimension + 1) 
                    && arg == null){
                //return super.visit(setEval(n), SKIP);
            }*/
            if(((String)arg).equalsIgnoreCase(IS_BINARY)){
                return super.visit(setEval(n), SKIP);
            }else{
            
            }
            
            return super.visit(n, arg);
        }
        
        return super.visit(n, arg);
    }
    
    public MethodCallExpr setEval(ArrayAccessExpr aaExpr){
        ArrayList<Expression> args = new ArrayList<>();
        args.add(new StringLiteralExpr("undefined"));
        args.add(aaExpr);
        args.add(new IntegerLiteralExpr(EvalOperation.ARRAY_ECCESS+""));
        return new MethodCallExpr(null, "eval", args);
    }

    @Override
    public Node visit(BinaryExpr n, Object arg) {
        return super.visit(n, IS_BINARY); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node visit(UnaryExpr n, Object arg) {
        return super.visit(n, null); //To change body of generated methods, choose Tools | Templates.
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
