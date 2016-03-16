/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.processor.parser;

import com.github.javaparser.ast.expr.Expression;

/**
 *
 * @author dennis
 */
public class ExpressionDetails {

    public String statementId;
    public String interceptionMethod;
    public Expression scope;
   
}
