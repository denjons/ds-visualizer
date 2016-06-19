/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test;


import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.annotation.markup.AbstractType;
import java.util.Arrays;

@VisualClass
public class HeapSort {
 
   @Visualize(abstractType="tree")
   private static int[] a;
   private static int n;
   private static int left;
   private static int right;
   private static int largest;
 
 
   public void buildheap(int []a) {
      n = a.length-1;
      for(int i=n/2; i>=0; i--){
         maxheap(a,i);
      }
   }
 
   public void maxheap(int[] a, int i) { 
      left = 2*i;
      right = 2*i+1;
 
      if(left <= n && a[left] > a[i]){
         largest=left;
      } else {
         largest=i;
      }
 
      if(right <= n && a[right] > a[largest]) {
         largest=right;
      }
      if(largest!=i) {
         exchange(i, largest);
         maxheap(a, largest);
      }
   }
 
   public void exchange(int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t; 
   }
 
   public void sort(int[] myarray) {
      a = myarray;
      buildheap(a);
      for(int i=n; i>0; i--) {
         exchange(0, i);
         n=n-1;
         maxheap(a, 0);
      }
   }
   
   public static void main(String[] args) {
        HeapSort hs = new HeapSort();
        
        hs.sort(new int [] {5,4,1,2,3,69,8,7,4,2,5,4,10});
    }
 
}

