/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.markup;

import java.util.ArrayList;

/**
 *
 * @author dennis
 */
public class ArrayDataStructure extends DataStructure {
    
    private static final String SIZE = "size";
    
    public ArrayDataStructure(String abstractType, String rawType, String type, String name) {
        super(abstractType, rawType, type, name);
        this.attributes.put(SIZE, new ArrayList<Integer>());
        
    }
    
    public void updateSize(int ... index){
        ArrayList<Integer> size = (ArrayList<Integer>)this.attributes.get(SIZE);
        for(int i = 0; i < index.length; i ++){
            if(size.size() <= i){
                size.add(index[i]+1);
            }else{
                size.set(i, Math.max(index[i]+1, size.get(i)));
            }
        }
        
    }
    
    public int getDimensions(){
        String dim = this.rawType.replaceAll("\\[", "");
        return rawType.length() - dim.length();
    }
    
}
