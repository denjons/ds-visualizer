/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.annotation.log.ast.EvalOperation;
import com.dennisjonsson.annotation.log.ast.WriteOperation;
import com.dennisjonsson.annotation.markup.Argument;
import com.dennisjonsson.annotation.markup.ArrayDataStructure;
import com.dennisjonsson.annotation.markup.DataStructure;
import com.dennisjonsson.annotation.markup.Header;
import com.dennisjonsson.annotation.markup.Method;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.lang.model.element.Element;

/**
 *
 * @author dennis
 */
public class MainParser extends Parser
{
    
    
    private String methodScope = null;
    private ArrayList<String> parameters;
    String level;
    ExprLevelParser exprParser;

    public MainParser(
            String className, 
            String fullClassName, 
            ArrayList<DataStructure> dataStruct, 
            Element printMethod, 
            HashMap<String, Method> methods) {
        super(className, fullClassName, dataStruct, printMethod, methods);

    }

    public MainParser(Parser parser) {
        super(parser);
        
    }
    
    public ExprLevelParser getExprParser(MainParser parser){
        if(exprParser == null){
            exprParser = new ExprLevelParser(this, this);
        }
        return exprParser;
    }

    public void setMethodScope(String scope){
        this.methodScope = scope;
    }
    
    public void setMethodParamaters(ArrayList<String> parameters){
        this.parameters = parameters;
    }

    private String addScope(String id){
        if(methodScope != null){
            return addClassScope( methodScope + Header.CONCAT + id);
        }
        return addClassScope(id);

    }
    
    private String addClassScope(String id){
        return fullClassName + Header.CONCAT + id;
    }
    
    private boolean matchesParameters(String id){
        if(parameters == null){
            return false;
        }
        id = cleanId(id);
        for(String str: parameters){
            if(str.equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }
    
    private String cleanId(String id){
        return id.replaceAll("((\\w*\\.)|(\\[.*\\]))","");
    }
  
    private boolean matchesFullScope(String identifier, String exprIdentifier){
        return addScope(cleanId(exprIdentifier)).matches(identifier);
        
    }
    
    private boolean matchesClassScope(String identifier, String exprIdentifier){
        exprIdentifier = cleanId(exprIdentifier);
        return addClassScope(exprIdentifier).matches(identifier);
        
    }

    private DataStructure searchClassScope(String identifier){
        for(DataStructure dataStructure : dataStructures ){
            if(matchesClassScope(dataStructure.getIdentifier(),
                    identifier)){
                return dataStructure;
            }
        }
        return null;
    }
    
    private DataStructure searchFullScope(String identifier){
        for(DataStructure dataStructure : dataStructures ){
            if(matchesFullScope(dataStructure.getIdentifier(),
                    identifier)){
                return dataStructure;
            }
        }
        return null;
    }
    
    
    public DataStructure isAnnotated(String identifier){
        
        
        DataStructure ds = searchFullScope(identifier);
        
        if(ds != null){
            return ds;
        }
        
        if(!matchesParameters(identifier)){
            return searchClassScope(identifier);
        }
        return null;
    }
    

    public DataStructure isAnnotated(Expression expr){

        // contains ..this. ..
        if(expr == null){
            return null;
        }
        if(expr instanceof ThisExpr){
            //System.out.println("thisExpr: "+expr.toString());
        }
        if(expr instanceof FieldAccessExpr || expr.toString().matches("([^\\w]*this\\..*)")){
            return searchClassScope(expr.toString());
        }
        if(expr instanceof ArrayAccessExpr){
            ArrayAccessExpr aae = (ArrayAccessExpr)expr;
            return isAnnotated(aae.getName().toString());
        }
        if(expr instanceof NameExpr){
            NameExpr ne = (NameExpr)expr;
            return isAnnotated(ne.getName());
        }
        
        if(expr instanceof ThisExpr){
            ThisExpr te = (ThisExpr)expr;
        }
        return null;

    }
   
    private String getIdentifier(String id){
       
        DataStructure ds = isAnnotated(id);
        
        if(ds != null){
            id = ds.identifier;
        }
        return id;
        
    }
    
    private String getIdentifier(Expression expr, String id){
        //System.out.println(expr.toString());
        //System.out.println(expr instanceof FieldAccessExpr);
        DataStructure ds = isAnnotated(expr);
        
        if(ds != null){
            return ds.identifier;
        }
        return id;
        
    }
    
     /*
       TRACKING
    */
  
    
    private boolean isTracked(Expression expr){
        return isAnnotated(expr) != null;   
    }

    private boolean isTracked(VariableDeclarator n){
        DataStructure data = isAnnotated(n.getId().toString());
        return (data != null) || isTracked(n.getInit());
    }
  
    /* 
                EVAL LEVEL
    */
    
    @Override
    public Node visit(VariableDeclarator n, Object arg) {

        ASTArgument astArg = (ASTArgument)arg;
        if(isTracked(n)){
            evalDeclaration(n);
            
        }
        astArg.set(IS_DECLARATION);
        return super.visit(n, astArg); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void evalDeclaration(VariableDeclarator decl){
        
        Expression init = decl.getInit();
        
        if(init == null){
            return;
        }
        
        MethodCallExpr call = setWriteFrom(init);
        
        call.getArgs().add(new IntegerLiteralExpr(WriteOperation.VARIABLE+""));
        
        String id = getIdentifier(decl.getId().toString().replaceAll("\\[\\s*\\]",""));

        decl.setInit(setEvalCall(
                id,
                call,
                EvalOperation.ASSIGNMENT,
                new int [] {decl.getBeginLine(), decl.getEndLine()}
        ));
    }
    
    private MethodCallExpr setEval(AssignExpr aExpr){
 
        String id = getIdentifier(aExpr.getTarget(), aExpr.getTarget().toString());
        
        return setEvalCall(
                id, 
                aExpr, 
                EvalOperation.ASSIGNMENT, 
                new int[]{aExpr.getBeginLine(), aExpr.getEndLine()});
    }
    
    /* 
                WRITE LEVEL
    */
    
    @Override
    public Node visit(AssignExpr n, Object arg) {
         
        ASTArgument astArg = (ASTArgument)arg;
        
        if( astArg.peek() == SKIP){
            astArg.set(IS_ASSIGNMENT);
            return super.visit(n, astArg);
        }
       
        Expression value = n.getValue();
        MethodCallExpr call = setWriteFrom(value);
        
        Expression target = n.getTarget();
        setWriteTo(target, call);
        
        if( isTracked(value) || isTracked(target)){
            astArg.push(SKIP);
            n.setValue(call); 
            MethodCallExpr evalCall = setEval(n);
            return super.visit(evalCall, astArg);
        }

        astArg.set(IS_ASSIGNMENT);
        return super.visit(n, astArg);
    }
    
    public void setWriteTo(Expression target, MethodCallExpr call){
        
        if(target instanceof FieldAccessExpr){
            FieldAccessExpr fae = (FieldAccessExpr)target;
            NameExpr ne = fae.getFieldExpr();
            if(fae.toString().contains("[")){
                seWriteToArray(ne.toString(), call);
            }else{
                setWriteTo(fae.getFieldExpr(), call);
            }
        }
        else if(target instanceof ArrayAccessExpr){
            seWriteToArray((ArrayAccessExpr)target, call);
        }else if(target instanceof NameExpr){
            setWriteToVariable((NameExpr)target, call);
        }else{
            call.getArgs().add(new IntegerLiteralExpr(WriteOperation.UNDEFINED+""));
        }
    }
    
    public MethodCallExpr setWriteFrom(Expression expr){
        MethodCallExpr call = null;
        
        if(expr instanceof ArrayAccessExpr){ 
            call = setWriteFromArray((ArrayAccessExpr)expr);
        }else if(expr instanceof NameExpr){
            call = setWtriteFromVariable((NameExpr)expr);
        }
        else if(expr instanceof NullLiteralExpr){
            call = setWriteFromUndefined(expr);
        }
        else{
            call = setWriteFromUndefined(expr);
        }
        return call;
    }

    
    public MethodCallExpr createWrite(ArrayList<Expression> args){
        return new MethodCallExpr(null, WriteOperation.OPERATION,args);
    }
    
    /*
        UNDEFINED WRITE
    */
    
    public MethodCallExpr setWriteFromUndefined(Expression exp){
        
        ArrayList<Expression> args = new ArrayList<>();
        args.add(new NullLiteralExpr());
        args.add(exp);
        args.add(new IntegerLiteralExpr(WriteOperation.UNDEFINED+""));
        return createWrite(args);
    }
    
    /*
        ARRAY WRITE
    */
    
    public MethodCallExpr setWriteFromArray(ArrayAccessExpr aValueExp){

        DataStructure dataStructure = isAnnotated(aValueExp);
        
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
        
        DataStructure dataStructure = isAnnotated(aTargetExp);
        if(dataStructure!= null){
            call.getArgs().add(new IntegerLiteralExpr(WriteOperation.ARRAY+""));
        }else{
            call.getArgs().add(new IntegerLiteralExpr(WriteOperation.UNDEFINED+""));
        }

    }
    
    public void seWriteToArray(String name, MethodCallExpr call){
        
        DataStructure dataStructure = isAnnotated(name);
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
        
        String id = getIdentifier(nExpr, nExpr.getName() );
        
        ArrayList<Expression> args = new ArrayList<>();  
        args.add(new StringLiteralExpr(id));
        args.add(nExpr);
        args.add(new IntegerLiteralExpr(WriteOperation.VARIABLE+""));
        return createWrite(args);
    }
    
    public void setWriteToVariable(NameExpr nExpr, MethodCallExpr call ){
        call.getArgs().add(
            new IntegerLiteralExpr(WriteOperation.VARIABLE+""));
        
    }
    
    /* 
                READ LEVEL
    */
   
    @Override
    public Node visit(ArrayAccessExpr n, Object arg) {
        ASTArgument astArg = (ASTArgument)arg;
        
        if((astArg.stack.contains(IS_ASSIGNMENT) || astArg.stack.contains(IS_DECLARATION)) 
                || astArg.peek() == IS_BINARY){
            ExprLevelParser parser = getExprParser(this);
            DataStructure dataStructure = isAnnotated(n.getName());
            if(dataStructure != null){
                parser.setRead(dataStructure, n, astArg);
            }
        }
        
        return super.visit(n, astArg);
    }

    
    
    
    
    public MethodCallExpr setEval(ArrayAccessExpr aaExpr){
        return setEvalCall(
             new NullLiteralExpr(),
             aaExpr,
             EvalOperation.ARRAY_ACCESS,
             new int[] {aaExpr.getBeginLine(), aaExpr.getEndLine()}
        );
    }

    @Override
    public Node visit(MethodCallExpr n, Object arg) {
        handleArguments(n);
        return super.visit(n, arg); 
    }
    
    private void handleArguments(MethodCallExpr n){
        
        Method method = methods.get(n.getName());
        if(method != null){
            for(Argument argItem : method.annotetedArguments){
               MethodCallExpr call = handleArgument(n.getArgs().get(argItem.position),argItem);
                n.getArgs().set(argItem.position, call);
            }
        }
    }
    
    private MethodCallExpr handleArgument(Expression expr, Argument arg){
        
        DataStructure ds = isAnnotated(expr.toString());
        
        Expression source;
        int writeSourceContext;
        int writeTargetContext = WriteOperation.VARIABLE;
        
        if(ds != null){
            source = new StringLiteralExpr(ds.getIdentifier());
            if(expr instanceof ArrayAccessExpr){
                writeSourceContext = WriteOperation.ARRAY;
            }else{
              writeSourceContext = WriteOperation.VARIABLE;  
            }
            
        }else{
            source = new NullLiteralExpr();
            writeSourceContext = WriteOperation.UNDEFINED;
        }
  
        return setEvalCall(
                arg.name,
                setWriteCall(source, expr, writeSourceContext, writeTargetContext),
                EvalOperation.METHOD_CALL,
                new int[]{expr.getBeginLine(), expr.getEndLine()});
    }
    
    public MethodCallExpr setEvalCall(String target, Expression value, 
            int context, int [] line){
        return setEvalCall(new StringLiteralExpr(target),
                value,
                context,
                line
        );
    }
    
    public MethodCallExpr setEvalCall(Expression target, Expression value, 
            int context, int [] line){
        ArrayList<Expression> args = new ArrayList<>();
        args.add(target);
        args.add(value);
        args.add(new IntegerLiteralExpr(context+""));
        args.add(new ArrayCreationExpr(
                new PrimitiveType(PrimitiveType.Primitive.Int), 
                line.length - 1, 
                arrayToExpr(line)
        ));
        
        return new MethodCallExpr(null, EvalOperation.OPERATION, args);
    }
    
    
    public MethodCallExpr setWriteCall(Expression source, Expression value, 
            int sourceType, int targetType){
        ArrayList<Expression> args = new ArrayList<>();
        args.add(source);
        args.add(value);
        args.add(new IntegerLiteralExpr(sourceType+""));
        args.add(new IntegerLiteralExpr(targetType+""));
        return new MethodCallExpr(null, WriteOperation.OPERATION, args);
    }
    
    public ArrayInitializerExpr arrayToExpr(int [] array){
        ArrayList<Expression> expressions = new ArrayList<>();
        for(int i : array){
            expressions.add(new IntegerLiteralExpr(i+""));
        }
        return new ArrayInitializerExpr(expressions);
    }

    @Override
    public Node visit(ConditionalExpr n, Object arg) {
        ASTArgument astArg = (ASTArgument)arg;
        astArg.clear();
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node visit(IfStmt n, Object arg) {
        
        ASTArgument astArg = (ASTArgument)arg;
        astArg.clear();
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node visit(ForStmt n, Object arg) {
        ASTArgument astArg = (ASTArgument)arg;
        astArg.clear();
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Node visit(WhileStmt n, Object arg) {
        ASTArgument astArg = (ASTArgument)arg;
        astArg.clear();
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public Node visit(BinaryExpr n, Object arg) {
    
       // System.out.println("bi: "+n.toString());
        ASTArgument astArg = (ASTArgument)arg;

        if(!astArg.stack.contains(IS_BINARY)){
            astArg.push(IS_BINARY);
            ExprLevelParser parser = getExprParser(this);
            parser.visit(n, arg);
        }else{
            astArg.push(IS_BINARY);
        }
        
        return super.visit(n, astArg); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node visit(UnaryExpr n, Object arg) {
        ASTArgument astArg = (ASTArgument)arg;
        astArg.push(IS_UNARY);
        return super.visit(n, astArg); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Node visit(ReturnStmt n, Object arg) {
        
        Expression e = n.getExpr();
        DataStructure ds = isAnnotated(e);
        ASTArgument astArg = (ASTArgument)arg;
        if(ds != null && e instanceof ArrayAccessExpr){
            ArrayAccessExpr a = (ArrayAccessExpr)e;
            //System.out.println("not null: "+e.toString());
            astArg.set(IS_ASSIGNMENT);
            MethodCallExpr call = setEval(a);
            n.setExpr(call);
        }else{
            astArg.clear();
        }

        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    

    

    
}
