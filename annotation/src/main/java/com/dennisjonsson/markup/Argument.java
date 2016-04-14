/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.markup;

/**
 *
 * @author dennis
 */

public class Argument {
    
    
    public String name;
    public Method method;
    public final int position;
    public DataStructure dataStructure;

    public Argument(String name, Method method, int position, DataStructure dataStructure) {
        this.name = name;
        this.method = method;
        this.position = position;
        this.dataStructure = dataStructure;
    }

    

    

}
