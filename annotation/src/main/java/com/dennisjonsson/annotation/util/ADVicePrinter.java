/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.util;

/**
 *
 * @author dennis
 */
public class ADVicePrinter {
    
    public static final String ADVice = "[ADVice]";
    public static final String INFO = ADVice+" ";
    public static final String WARNING = ADVice+" WARNING: ";
    public static final String ERROR = ADVice+" ERROR: ";
    
    public static void info(String msg){
        System.out.println(INFO+msg);
    }
    
    public static void warning(String msg){
        System.out.println(WARNING+msg);
    }
    
    public static void error(String msg){
        System.out.println(ERROR+msg);
    }
    
}
