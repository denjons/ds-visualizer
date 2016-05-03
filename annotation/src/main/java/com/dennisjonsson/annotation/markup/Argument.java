/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.markup;

/**
 *
 * @author dennis
 */

public class Argument {
    
    public String name;
    public String simpleName;
    public Method method;
    public DataStructure dataStructure;
    
    public int position = 0;

    public Argument(String name, String simpleName, Method method, DataStructure dataStructure) {
        this.name = name;
        this.simpleName = simpleName;
        this.method = method;
        this.dataStructure = dataStructure;
    }

    

}
