/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.markup;

import java.util.ArrayList;

/**
 *
 * @author dennis
 */
public class Markup {
    
    public final Header header;
    public final ArrayList<Operation> body;

    public Markup(Header header, ArrayList<Operation> body) {
        this.header = header;
        this.body = body;
    }
    
    public void appendDataStructure(DataStructure dataStructure){
        this.header.addDataStructure(dataStructure);
    }
 
    public void appendOperation(Read read){
        this.body.add(read);
        Entity source =  read.getSource();
        source.update(header.annotatedVariables.get(source.getId()));
       // updateSize(read.getSource().getId(), read.getSource().getIndex());
    }
    
    public void appendOperation(Write write){
        this.body.add(write);
        Entity source = write.getSource();
        if(!source.getId().equalsIgnoreCase(UndefinedEntity.UNDEFINED)){
            source.update(header.annotatedVariables.get(source.getId())); 
        }
        Entity target = write.getTarget();
        target.update(header.annotatedVariables.get(target.getId()));
       // updateSize(write.getTarget().getId(), write.getTarget().getIndex());
    }
     
}
