/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.log.ast;

import com.dennisjonsson.markup.ArrayEntity;
import com.dennisjonsson.markup.DataStructure;
import com.dennisjonsson.markup.Entity;
import com.dennisjonsson.markup.Markup;
import com.dennisjonsson.markup.Operation;
import com.dennisjonsson.markup.Read;
import com.dennisjonsson.markup.UndefinedEntity;
import com.dennisjonsson.markup.VariableEntity;
import com.dennisjonsson.markup.Write;
import java.util.ArrayList;

/**
 *
 * @author dennis
 */
public class MarkupComposer {
    
    public final Markup markup;

    public MarkupComposer(Markup markup) {
        this.markup = markup;
    }
    
    public void composeWriteArrayToArray(
            ArrayList<ParseOperation> fromSection,
            ArrayList<ParseOperation> toSection, 
            WriteOperation op){
        
        ArrayEntity from = toArrayEntity((ReadArray)fromSection.get(0));
        collectNestedReads(fromSection);
        ArrayEntity to = toArrayEntity((ReadArray)toSection.get(0));
        collectNestedReads(toSection);
        markup.body.add(createWrite(from, to, op.value));
        
    }
   
    
    public void composeWriteArrayToVariable(
            ArrayList<ParseOperation> fromSection,
            EvalOperation eval){
        
        ArrayEntity from = toArrayEntity((ReadArray)fromSection.get(0));
        VariableEntity to = new VariableEntity(eval.identifier);
        //System.out.print("\nwrite from "+from.getId()+" to "+to.getId());
        
        collectNestedReads(fromSection);
        
        markup.body.add(createWrite(from, to, eval.value));
        
    }
    
    public void composeWriteVariableToArray(
            WriteOperation write,
            ArrayList<ParseOperation> toSection){
        
        VariableEntity from = new VariableEntity(write.identifier);
        ArrayEntity to = toArrayEntity((ReadArray)toSection.get(0));
        //System.out.print("\nwrite from "+from.getId()+" to "+to.getId());
        
        collectNestedReads(toSection);
        
        markup.body.add(createWrite(from, to, write.value));
        
    }
    
    public void composeWriteVariableToVariable(WriteOperation write, 
            EvalOperation eval){
        VariableEntity from = new VariableEntity(write.identifier);
        VariableEntity to = new VariableEntity(eval.identifier);
        markup.body.add(createWrite(from, to, eval.value));
    }
    
    public Read composeReadArrayToUknown(ReadArray readArray){
        ArrayEntity from = toArrayEntity(readArray);
        Entity to = UndefinedEntity.UNDEFINED_ENTITY;
        //Read(Entity source, Entity target, String[] value) 
        Read read = createRead(from, to, new String [] {""});
        markup.body.add(read);
        return read;
    }
    
    public void composeReadArrayToUknown(
            ArrayList<ParseOperation> section,
            EvalOperation eval){
        for(ParseOperation op : section){
            composeReadArrayToUknown((ReadArray)op).setValue(eval.value);
        }
    }
    
    public void composeReadArrayToUknown(ArrayList<ParseOperation> section){
        for(ParseOperation op : section){
            composeReadArrayToUknown((ReadArray)op);
        }
    }
    

    public ArrayEntity toArrayEntity(ReadArray readArray){
        ArrayEntity entity = 
                new ArrayEntity( readArray.index, readArray.identifier);
        DataStructure ds = markup.header.annotatedVariables.get(entity.getId());
        entity.update(ds);
        return entity;
    }
    
    private void collectNestedReads(ArrayList<ParseOperation> operations){
        if(operations.size() > 1){
            ArrayList<ParseOperation> subSequent = new ArrayList<>();
            subSequent.addAll(operations.subList(1, operations.size()-1));
            composeReadArrayToUknown(subSequent);
        }
    }
    
    
    
    public Operation createWrite(Entity from, Entity to, Object value){
        if(!isAnnotated(to)){
            return new Read(from, to, value);
        }
        return new Write(from, to, value);
    }
    
    public Read createRead(Entity from, Entity to, Object value){
        
        return new Read(from, to, value);
    }
    
    public boolean isAnnotated(Entity entity){
        return markup.header.
                annotatedVariables.get(entity.getId()) != null;
    }

    
}
