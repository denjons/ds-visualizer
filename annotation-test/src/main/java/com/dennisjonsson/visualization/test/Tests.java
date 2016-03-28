/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;

/**
 *
 * @author dennis
 */
public class Tests {
    
    public static void main(String [] args){
        String a = "a[k[i]][1+7/something()]";

        boolean res = a.matches("a"+"(\\s*(\\[(.*)\\]))*");

        Object [] objects = new Object[0];
        all(objects);
        System.out.println(res);
    }
    
    public static void all(Object obj){
    
    }
    
}
