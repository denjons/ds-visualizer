/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log;


import com.dennisjonsson.markup.Markup;
import com.dennisjonsson.markup.Read;
import com.dennisjonsson.markup.Write;

/**
 *
 * @author dennis
 */
public interface Stream {
    
    public void addMarkup(String className, Markup markup);
    //public void read(String className, int position);
    //public void write(String className, int position);
    
}
