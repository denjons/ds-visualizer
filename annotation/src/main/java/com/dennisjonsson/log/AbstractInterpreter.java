/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log;

import com.dennisjonsson.markup.Header;
import com.dennisjonsson.markup.Markup;
import com.dennisjonsson.markup.Read;
import com.dennisjonsson.markup.Write;
import java.util.HashMap;

/**
 *
 * @author dennis
 */
public abstract class AbstractInterpreter implements Stream{
    
    protected final HashMap<String, Markup> classes;
    
    protected AbstractInterpreter() {
        classes = new HashMap<>();
    }

    @Override
    public void addMarkup(String className, Markup markup) {
        classes.put(className, markup);
    }

    public abstract void interpret(String className, int position);
   


    
}
