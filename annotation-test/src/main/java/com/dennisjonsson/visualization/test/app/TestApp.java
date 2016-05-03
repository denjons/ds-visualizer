/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.app;

import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.visualization.test.BubbleSort;
import com.dennisjonsson.visualization.test.HeapSort;
import com.dennisjonsson.visualization.test.MergeSort;
import com.dennisjonsson.visualization.test.QuickSort;
import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dennis
 */

@SourcePath(path = "C:/Users/dennis/Documents/NetBeansProjects/annotation-test")
@VisualClass
public class TestApp {
    
    public static Semaphore s = new Semaphore(2, true);

    public static void aquire(){
        try {
            s.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(TestApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int [] a = { 55,10,22,66,55,44,77,88,99,33,22,51,65,54,21,32,23,56,12,45,56,23,12,45,25,4,5,2,1};
        int [] b = {55,10,22,66,55,44,77,88,99,33,22,51,65,54,21,32,23,56,12,45,56,23,12,45,25,4,5,2,1};
        int [] c = { 55,10,22,66,55,44,77,88,99,33,22,51,65,54,21,32,23,56,12,45,56,23,12,45,25,4,5,2,1};
        Integer [] d = { 55,10,22,66,55,44,77,88,99,33,22,51,65,54,21,32,23,56,12,45,56,23,12,45,25,4,5,2,1};
        
        Runnable r1 = new Runnable(){
            @Override
            public void run() {
                aquire();
                HeapSort hs = new HeapSort();
                hs.sort(a);
                s.release();
            }
        };
       
         Runnable r2 = new Runnable(){
            @Override
            public void run() {
                aquire();
                BubbleSort.sort(b);
                s.release();
            }
        };
      
        Runnable r3 = new Runnable(){
            @Override
            public void run() {
                aquire();
                QuickSort.sort(c);
                 s.release();
            }
        };
        
        Runnable r4 = new Runnable(){
            @Override
            public void run() {
                aquire();
                MergeSort.sort(d);
                 s.release();
            }
        };
        
       // new Thread(r1).start();
        
       new Thread(r2).start();
        
       // new Thread(r3).start();
       
       // new Thread(r4).start();
        
        aquire();
        
        try {
            sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestApp.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
}
