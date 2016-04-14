/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log.ast;

import com.dennisjonsson.log.AbstractInterpreter;
import com.dennisjonsson.log.Stream;
import com.dennisjonsson.markup.Argument;
import com.dennisjonsson.markup.DataStructure;

/**
 *
 * @author dennis
 */
public class SourceHeader {
    
    public static final String CLASS_NAME = "com.dennisjonsson.log.ast.SourceHeader";
    
    public final String className;
    public final String printingPath;
    public final DataStructure [] dataStructures;
    public final AbstractInterpreter interpreter;

    public SourceHeader(String className, String printingPath, DataStructure[] dataStructures, AbstractInterpreter interpreter) {
        this.className = className;
        this.printingPath = printingPath;
        this.dataStructures = dataStructures;
        this.interpreter = interpreter;
    }

    

    

    

   

    
    
    
    
}
