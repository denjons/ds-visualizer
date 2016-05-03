/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.log.ast;

import com.dennisjonsson.annotation.markup.ArrayEntity;
import com.dennisjonsson.annotation.markup.DataStructure;
import com.dennisjonsson.annotation.markup.Entity;
import com.dennisjonsson.annotation.markup.Markup;
import com.dennisjonsson.annotation.markup.Operation;
import com.dennisjonsson.annotation.markup.Read;
import com.dennisjonsson.annotation.markup.UndefinedEntity;
import com.dennisjonsson.annotation.markup.VariableEntity;
import com.dennisjonsson.annotation.markup.Write;
import java.util.ArrayList;

/**
 *
 * @author dennis
 */
public class MarkupComposer {
    
    public final String className;
    public final Markup markup;

    public MarkupComposer(String className, Markup markup) {
        this.className = className;
        this.markup = markup;
    }
    

    public void composeWriteArrayToArray(
            ArrayList<ParseOperation> fromSection,
            ArrayList<ParseOperation> toSection, 
            WriteOperation op,
            EvalOperation eval){
        
        ArrayEntity from = toArrayEntity((ReadArray)fromSection.get(0));
        collectNestedReads(fromSection, eval);
        ArrayEntity to = toArrayEntity((ReadArray)toSection.get(0));
        collectNestedReads(toSection, eval);
        markup.body.add(createWrite(from, to, op.value, eval.beginLine, eval.endLine));
        
    }
   
    
    public void composeWriteArrayToVariable(
            ArrayList<ParseOperation> fromSection,
            EvalOperation eval){
        
        ArrayEntity from = toArrayEntity((ReadArray)fromSection.get(0));
        VariableEntity to = new VariableEntity(eval.identifier);
        //System.out.print("\nwrite from "+from.getId()+" to "+to.getId());
        
        collectNestedReads(fromSection, eval);
        
        markup.body.add(createWrite(from, to, eval.value, eval.beginLine, eval.endLine));
        
    }
    
    public void composeWriteVariableToArray(
            WriteOperation write,
            ArrayList<ParseOperation> toSection, EvalOperation eval){
        
        VariableEntity from = new VariableEntity(write.identifier);
        ArrayEntity to = toArrayEntity((ReadArray)toSection.get(0));
        //System.out.print("\nwrite from "+from.getId()+" to "+to.getId());
        
        collectNestedReads(toSection, eval);
        
        markup.body.add(createWrite(from, to, write.value, 
                eval.beginLine, eval.endLine));
        
    }
    
    public void composeWriteVariableToVariable(WriteOperation write, 
            EvalOperation eval){
        VariableEntity from = new VariableEntity(write.identifier);
        VariableEntity to = new VariableEntity(eval.identifier);
        markup.body.add(createWrite(from, to, eval.value, eval.beginLine, eval.endLine));
    }
    
    public Read composeReadArrayToUknown(ReadArray readArray, int beginLine, int endLine){
        ArrayEntity from = toArrayEntity(readArray);
        Entity to = UndefinedEntity.UNDEFINED_ENTITY;
        //Read(Entity source, Entity target, String[] value) 
        Read read = createRead(from, to, new String [] {""}, beginLine, endLine);
        markup.body.add(read);
        return read;
    }
    
    public void composeReadArrayToUknown(
            ArrayList<ParseOperation> section,
            EvalOperation eval){
        for(ParseOperation op : section){
            composeReadArrayToUknown((ReadArray)op, eval.beginLine, eval.endLine).setValue(eval.value);
        }
    }
    
    public void composeReadArraysToUknowns(ArrayList<ParseOperation> section, EvalOperation eval){
        for(ParseOperation op : section){
            composeReadArrayToUknown((ReadArray)op, eval.beginLine, eval.endLine);
        }
    }
    

    public ArrayEntity toArrayEntity(ReadArray readArray){
        ArrayEntity entity = 
                new ArrayEntity( readArray.index, readArray.identifier);
        DataStructure ds = markup.header.getDataStructure(className, entity.getId());
        entity.update(ds);
        return entity;
    }
    
    private void collectNestedReads(ArrayList<ParseOperation> operations, EvalOperation eval){
        if(operations.size() > 1){
            ArrayList<ParseOperation> subSequent = new ArrayList<>();
            subSequent.addAll(operations.subList(1, operations.size()-1));
            composeReadArraysToUknowns(subSequent, eval);
        }
    }
    
    
    
    public Operation createWrite(Entity from, Entity to, Object value, int beginLine, int endLine){
        if(!isAnnotated(to)){
            return createRead(from, to, value, beginLine, endLine);
        }
        return new Write(from, to, value, className, beginLine, endLine);
    }
    
    public Read createRead(Entity from, Entity to, Object value, int beginLine, int endLine){
        
        return new Read(from, to, value, className, beginLine, endLine);
    }
    
    public boolean isAnnotated(Entity entity){
        return markup.header.getDataStructure(className,entity.getId()) != null;
    }

    
}
