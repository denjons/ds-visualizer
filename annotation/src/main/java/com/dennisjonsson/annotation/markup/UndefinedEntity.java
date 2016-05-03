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
public class UndefinedEntity extends Entity{
    public static final String UNDEFINED = null;
    
    public static final UndefinedEntity UNDEFINED_ENTITY = new UndefinedEntity();

    private UndefinedEntity() {
        super(UNDEFINED);
    }

    @Override
    public void update(DataStructure dataStructure) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
    
}
