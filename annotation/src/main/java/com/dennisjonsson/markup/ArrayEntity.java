/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.markup;

import java.util.Arrays;

/**
 *
 * @author dennis
 */
public class ArrayEntity extends Entity {
    
    private int[] index;

    public ArrayEntity(String id) {
        super(id);
        index = new int[]{};
    }

    public ArrayEntity(int[] index, String id) {
        super(id);
        this.index = index;
    }
    
    public int[] getIndex() {
        return index;
    }
  
    /*
    public void extendIndex(int in){
        int[] newIndex = new int[index.length + 1];
        for(int i = 0; i < index.length; i++){
            newIndex[i] = index[i];
        }
        newIndex[index.length] = in;
        index = newIndex;
    }
*/
    @Override
    public void update(DataStructure dataStructure) {
        ArrayDataStructure arrayDataStrucutre = 
                (ArrayDataStructure)dataStructure;
        arrayDataStrucutre.updateSize(index);
    }
    
}
