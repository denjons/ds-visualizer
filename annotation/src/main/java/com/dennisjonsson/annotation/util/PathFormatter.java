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
public class PathFormatter {
    public final static String classes = "classes";
    
    public static String toMavenClassPath(String str){
        return str
                .replace("src", "target")
                .replace("main/java", classes)
                .replace("main\\java",classes);
    }
}
