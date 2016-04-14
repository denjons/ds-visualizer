/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.app;

import com.dennisjonsson.annotation.Include;
import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.visualization.test.BubbleSort;
import com.dennisjonsson.visualization.test.HeapSort;
import com.dennisjonsson.visualization.test.MergeSort;
import com.dennisjonsson.visualization.test.QuickSort;
import com.dennisjonsson.visualization.test.app.ds.Node;
import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dennis
 */


@Include(classes = {"com.dennisjonsson.visualization.test.BubbleSort",
                    "com.dennisjonsson.visualization.test.QuickSort",
                    "com.dennisjonsson.visualization.test.MergeSort",
                    "com.dennisjonsson.visualization.test.HeapSort"})

@SourcePath(path = "C:/Users/dennis/Documents/NetBeansProjects/" 
        + "annotation-test/src/main/" 
        + "java/com/dennisjonsson/visualization/test/app/")
public class TestApp {
    
    public static Semaphore s = new Semaphore(3, true);

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
        
        int [] a = { 22,25,2, 6, 3, 5, 1 ,7,8,34,22,32,21,16,10,19,30,15,10,
            55,10,22,66,55,44,77,88,99,33,22,51,65,54,21,32,23,56,12,45,56,23,12,45,25,4,5,2,1};
        int [] b = { 22,25,2, 6, 3, 5, 1 ,7,8,34,22,32,21,16,10,19,30,15,10,
            55,10,22,66,55,44,77,88,99,33,22,51,65,54,21,32,23,56,12,45,56,23,12,45,25,4,5,2,1};
        int [] c = { 22,25,2, 6, 3, 5, 1 ,7,8,34,22,32,21,16,10,19,30,15,10,
            55,10,22,66,55,44,77,88,99,33,22,51,65,54,21,32,23,56,12,45,56,23,12,45,25,4,5,2,1};
        
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
        
        new Thread(r1).start();
        
        new Thread(r2).start();
        
        new Thread(r3).start();

        try {
            sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        aquire();
        
        print();
    }
    
    @Print(path="")
    public static void print(){}
}
