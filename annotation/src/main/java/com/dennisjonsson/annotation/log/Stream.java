/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.log;


import com.dennisjonsson.annotation.markup.Markup;
import com.dennisjonsson.annotation.markup.Read;
import com.dennisjonsson.annotation.markup.Write;

/**
 *
 * @author dennis
 */
public interface Stream {
    
    public void addMarkup(Markup markup);
    //public void read(String className, int position);
    //public void write(String className, int position);
    
}
