/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.log.ast;

import com.dennisjonsson.annotation.log.AbstractInterpreter;
import com.dennisjonsson.annotation.log.Stream;
import com.dennisjonsson.annotation.markup.Argument;
import com.dennisjonsson.annotation.markup.DataStructure;

/**
 *
 * @author dennis
 */
public class SourceHeader {
    
    public static final String CLASS_NAME = "com.dennisjonsson.log.ast.SourceHeader";
    
    public final String className;
    public final String [] source;
    public final String printingPath;
    public final DataStructure [] dataStructures;
    public final AbstractInterpreter interpreter;
    public final String rootDirectory;

    public SourceHeader(String className, String [] source, String printingPath, 
            DataStructure[] dataStructures, AbstractInterpreter interpreter, String rootDirectory) {
        this.className = className;
        this.source = source;
        this.printingPath = printingPath;
        this.dataStructures = dataStructures;
        this.interpreter = interpreter;
        this.rootDirectory = rootDirectory;
    }

    

    

    

    

   

    
    
    
    
}
