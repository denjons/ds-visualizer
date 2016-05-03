/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.annotation.util;

/**
 *
 * @author dennis
 */
import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.*;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class SourceFinder
    extends SimpleFileVisitor<Path> {
    
    public final String sourceFile;
    public final String fullName;
    public Path result = null;
    
    public SourceFinder(String sourceFile, String fullClassName){
        super();
        this.sourceFile = sourceFile;
        this.fullName = fullClassName;
    }
    
    public boolean isFullPath(Path file){
        
        return (file.toString().contains(fullName.replaceAll("\\.", "/")) ||
                file.toString().contains(fullName.replaceAll("\\.", "\\\\")) );
     
    }

    // Print information about
    // each type of file.
    @Override
    public FileVisitResult visitFile(Path file,
                                   BasicFileAttributes attr) {
        if (attr.isSymbolicLink()) {
            //System.out.format("Symbolic link: %s ", file);
        } else if (attr.isRegularFile()) {
            
            if(file.getFileName().toString().equalsIgnoreCase(sourceFile) && 
                     isFullPath(file)){
               
                result = file;
                return FileVisitResult.TERMINATE;
            }
        }else if(attr.isDirectory()){
            //System.out.format("Directory: %s ", file);
            
        }
        else {
           // System.out.format("Other: %s ", file);
        }
        //System.out.println("(" + attr.size() + "bytes)");
        return CONTINUE;
    }

    // Print each directory visited.
    @Override
    public FileVisitResult postVisitDirectory(Path dir,
                                          IOException exc) {
        //System.out.format("Directory: %s%n", dir);
        return CONTINUE;
    }

    // If there is some error accessing
    // the file, let the user know.
    // If you don't override this method
    // and an error occurs, an IOException 
    // is thrown.
    @Override
    public FileVisitResult visitFileFailed(Path file,
                                       IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }
}