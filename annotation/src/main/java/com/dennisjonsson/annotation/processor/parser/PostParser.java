/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.annotation.log.ast.LogUtils;
import com.dennisjonsson.annotation.markup.DataStructure;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.TypeParameter;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.TypeDeclarationStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author dennis
 */
public class PostParser extends ModifierVisitorAdapter {
    
    public final String className;
    final String fullClassName;
    final String newClassName;
    public final ArrayList<DataStructure> dataStructures;
    public final HashMap<String, String> includes;
    public final HashMap<String, String> imports;
    public final String packageName;
    public String loggerField;
    
    public boolean fieldIsSet = false;
     
        
    ArrayList<Statement> classBody;

    public PostParser(String className, String fullClassName, String newClassName, ArrayList<DataStructure> dataStructures, 
            ArrayList<String> includes, LinkedList<ImportDeclaration> imports, 
            String packageName) {
        
        this.className = className;
        this.fullClassName = fullClassName;
        this.newClassName = newClassName;
        this.dataStructures = dataStructures;
        this.includes = new HashMap<>();
        this.imports = new HashMap<>();
        this.packageName = packageName;
        //System.out.println(this.packageName);
        initIncludes(includes);
        initImports(imports);
        
    }

    public void setLoggerField(String loggerField) {
        this.loggerField = loggerField;
    }

 
    private void initIncludes(ArrayList<String> includes){
        for(String include : includes){
           // System.out.println("include: "+include);
            this.includes.put(include, include);
        }
    }
    
    private void initImports(LinkedList<ImportDeclaration> imports){
        for(ImportDeclaration imp : imports){
            
            int i = imp.getName().toString().lastIndexOf(".") + 1;
            this.imports.put(imp.getName().toString().substring(i),
                    imp.getName().toString());
           // System.out.println("import: "+imp.getName().toString().substring(i)
                   // + " -> "+imp.getName());
        }
    }

    @Override
    public Node visit(ClassOrInterfaceDeclaration n, Object arg) {
        //System.out.println(n.getName());
        
        if(n.getName().equalsIgnoreCase(className)){
            n.setName(newClassName);
            
            /*
            LinkedList<BodyDeclaration> list = (LinkedList<BodyDeclaration>)n.getMembers();
            if(list.size() > 0){
                BodyDeclaration body = list.getFirst();
           
            }*/
            //classDeclarationBlock = true;
            // insert field as string
            // will not be traversed in post parsing
            /*
            LinkedList<Node> children = (LinkedList<Node>)n.getChildrenNodes();
            if(children.size() > 0){
                
                Node n1 = children.getFirst();
                System.out.println("setting parent for :" + n1.toString());
                ExpressionStmt estmt = new ExpressionStmt(new IntegerLiteralExpr (loggerField));
                estmt.setParentNode(n1.getParentNode());
                n1.setParentNode(estmt);
            }*/
            
          
        }
       
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    /*
    @Override
    public Node visit(FieldDeclaration n, Object arg) {
        if(!fieldIssSet){
            fieldIssSet = true;
            ExpressionStmt estmt = new ExpressionStmt(new IntegerLiteralExpr (loggerField));
            estmt.setParentNode(n.getParentNode());
            n.setParentNode(estmt);
        }
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    
    */

    /*
    @Override
    public Node visit(BlockStmt n, Object arg) {
        if(!fieldIsSet){
            fieldIsSet = true;
            LinkedList<Statement> statements = 
                    (LinkedList<Statement>)n.getStmts();
            ExpressionStmt estmt = new ExpressionStmt(new IntegerLiteralExpr (loggerField));
            statements.addFirst(estmt);
            
        }
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    */

    
    @Override
    public Node visit(ConstructorDeclaration n, Object arg) {
        if(n.getName().equalsIgnoreCase(className)){
            n.setName(newClassName);
            /*
            ExpressionStmt estmt = new ExpressionStmt(new IntegerLiteralExpr (loggerField));
            System.out.println("setting parent node for: "+n.toString());
            estmt.setParentNode(n.getParentNode());
            n.setParentNode(estmt);
            */
        }
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public Node visit(ClassExpr n, Object arg) {
        
        //System.out.println("class name: "+n.toString());
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    
 

    @Override
    public Node visit(TypeDeclarationStmt n, Object arg) {
      //  String name = n.getTypeDeclaration().getName().toString();
        
       // System.out.println(className+" post: TypeDeclarationStmt : "+name);
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Node visit(ClassOrInterfaceType n, Object arg) {
        //System.out.println(className+" post: ClassOrInterfaceType : "+n.getName());
       
        if(n.getName().equalsIgnoreCase(className)){
            n.setName(newClassName);
            return super.visit(n, arg);
        }
        return super.visit((ClassOrInterfaceType)applyIncludesOnType(n), arg); 
    }
    

    @Override
    public Node visit(NameExpr n, Object arg) {
       // System.out.println(className+" post: NameExpr : "+n.toString());
        n.setName(applyIncludesOnName(n.toString()));
        return super.visit(n, arg); 
    }
    
    /*
        checks imports against other annotated classes
    */
    public String checkTypes(String typeName){
        
        String importName = imports.get(typeName);
        if(importName != null){
            return includes.get(importName);
        }
        
        String include = includes.get(packageName + "." + typeName);
       // System.out.println("type checking: "+packageName + "." + typeName);
        if(include != null){
            return include;
        }
        
        return includes.get(typeName);

    }
    
    /*
        replaces all class name expressions which have a visual counter part
    */
    private String applyIncludesOnName(String name){
        int i = name.lastIndexOf(".") +1;
        String includeName = checkTypes(name.substring(i));
        if( includeName != null){
               return includeName + ASTProcessor.SUFFIX;
        }
        return name;
    }
    
    private Type applyIncludesOnType(Type type){
        String name = type.toString();
        int i = name.lastIndexOf(".") +1;
        String t = checkTypes(name.substring(i));
        if( t != null){
               return includeType(type, t);
        }
        return type;
    }
     
    public Type includeType(Type type, String name){
        if(type instanceof ClassOrInterfaceType ){
            return new ClassOrInterfaceType(name + ASTProcessor.SUFFIX);
        }else if(type instanceof ReferenceType){
            ReferenceType refType = (ReferenceType)type;
            Type innerType = includeType(refType.getType(), name);
            return new ReferenceType(innerType,refType.getArrayCount());
        }else if(type instanceof PrimitiveType){
            return type;
        }
        throw new RuntimeException(type.toString() + " is not a class or interface.");
    }
 
    
    /*
    @Override
    public Node visit(BlockStmt n, Object arg) {
        if (n.getParentNode() instanceof ClassOrInterfaceDeclaration){
            ClassOrInterfaceDeclaration decl = 
                    (ClassOrInterfaceDeclaration)n.getParentNode();
            if(decl.getName().equalsIgnoreCase(className)){
                classBody = (ArrayList<Statement>)n.getStmts();
                insertMethods();
            }
        }
        return super.visit(n, arg); //To change body of generated methods, choose Tools | Templates.
    }*/
    

    
}
