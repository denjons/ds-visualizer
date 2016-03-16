package com.dennisjonsson.visualization.test;



import com.dennisjonsson.annotation.VisualClassPath;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.markup.AbstractType;
import java.util.*;

/*
@VisualClassPath(path="C:/Users/dennis/Documents/NetBeansProjects/" +
             "annotation-test/src/main/"
        + "java/com/dennisjonsson/visualization/test/")*/
public class PrioQueue {
	
	interface Comparator{
		int compare(Object elm1, Object elm2);
	}

	private Comparator comparator;

	// Underlying array for storing all elements in heap
        //@Visualize(type = AbstractType.ARRAY)
	private Object[] array;
	// Starting capacity of the array
	private static final int DEFAULT_CAPACITY = 2;
	// Keeps track of the number of elements in the array.
	private int currentSize;

    
  	public PrioQueue(Comparator comparator){
	      	
		array = new Object[DEFAULT_CAPACITY+1];
		currentSize = 0;
		this.comparator = comparator;
   	 }

	public PrioQueue(Comparator comparator, Object [] Array){
		array = Array;
		currentSize = Array.length;
		this.comparator = comparator;
		buildHeap();
   	 }

	/*
		resets the queue to a previous size
	*/
	public void reset(int size){
		if(array[size-1] != null){
			currentSize = size;
			buildHeap();
		}
	}

	/*
		removes all from a certain index
	*/
	public void decrease(int size){
		int oldSize = currentSize;
		currentSize = currentSize-size;
		for(int i = currentSize; i < oldSize; i++){
			array[i] = null;
		}

		
	}


	private void buildHeap(){
		for (int k = size()/2; k > 0; k--){
			shiftDown(k);
		}
	}
    
	/**
	* 
	* @return first element in heap
	*/
    
    	public void clear(){
	    	array = new Object[DEFAULT_CAPACITY+1];
	    	currentSize = 0;
   	}
    
    	public Object top(){
		if(currentSize <= 0)
		    throw new RuntimeException("not possible");
		return (Object)array[0];
   	}
   	 
    	public Object bottom(){
	    	if(size() <= 0)
	    		throw new RuntimeException("not possible, size too small");
	    	return (Object)array[size() - 1];
    	}

	public Object[] toArray(){
		Object [] arr = new Object[currentSize];
		System.arraycopy(array,0,arr,0,currentSize);
		return arr;
	}

	public Object[] getSorted(){

		Object [] arr = new Object [currentSize];
		int index = 0;
		while(size() > 0){
			arr[index] = remove();
			index ++;
		}
		return arr;
		
	}

	/**
	* Inserts an new object into the heap
	 * Time complexity is O(log n), O(n) when it needs to be extended.
	* @param x object to inserted
	*/
    	public void add(Object x){
	
		//assert invariant() : showHeap();
		
		if(currentSize + 1 > array.length )
		    doubleArray();
		array[currentSize] = x;
		int index = shiftUp(currentSize);
		currentSize++;
		
		//assert invariant() : showHeap();
    	}
    
	/**
	* removes smallest element in heap
	 * Time complexity is O(log n)
	* @return smallest element in heap
	*/
    	public Object remove(){
		if(currentSize <= 0){
		    throw new RuntimeException("Cannot remove from an empty heap!");
		}
		//assert invariant() : showHeap();	
		// move last element to the top
       	        currentSize --;
		Object firstElm = (Object)array[0];
		Object lastElm = (Object)array[currentSize];
		// tweak
		array[currentSize] = firstElm;
	
		if(currentSize > 0){
			array[0] = lastElm;
			int index = shiftDown(0);
		}
		//assert invariant() : showHeap();
		return firstElm;
    	}
    
	/**
	* Internal method for shifting an element down the heap
	 * Time complexity is O(log n)
	* @param index starting index
	* @return final index
	*/
   	 private int shiftDown(int index){
	    	Object x = array[index];
		int child;
		while(index*2 + 1 <currentSize){
		    child = index*2 + 1;
		    if(child + 1 < currentSize && 
		            comparator.compare(array[child],array[child+1])>0){
		        child++;
		    }if(comparator.compare(x,array[child])>0){
		        array[index] = (Object)array[child];
		        index = child;
		    }else
		        break;
		}
		array[index] = x;
		return index;
  	  }
    
	/**
	* Internal method for shifting an element up the heap
	 * Time complexity is O(log n) (worst case)
	* @param index starting index
	* @return final index where the element ended up
	*/
    	private int shiftUp(int index){
		int parent;
		Object x = array[index];
		while(index>0){
		    if(index%2==0)
		        parent = (index-2)/2;
		    else
		        parent = (index - 1)/2;
		    
		    if(parent>=0 && 
		            comparator.compare(x,array[parent])<0){
		        array[index] = (Object)array[parent];
				
		        index = parent;
		    }else
		        break;
		}
		array[index] = x;
		return index;
    	}
    
	/**
	* Internal method for extending the array when it is full
	 * Time complexity is O(n)
	*/
    	private void doubleArray(){
	    	Object[] newArray = new Object[array.length * 2];
		for( int i = 0; i < array.length; i++ )
		    newArray[i] = array[i];
		array = newArray;
    	}
    
    	public int size(){
      	  return currentSize;
    	}

	@Override
	public String toString() {
		String str = "";
		for(int i =0; i < currentSize; i++){
		    str += array[i].toString();
		    if(i<currentSize - 1)
		        str += ", ";
		}
		
		return str;
    	}
	
	/*
		INVARIANT
		looks through the heap recursively before and after add, 
		remove and update makes any changes the heap
	*/
    
	/**
	* Internal method for checking the heap order invariant
	* Looks through the heap starting from the root
	*/
	private boolean invariant(){
		return lookThroughHeap(0);
	}
	
	/**
	* Internal method used by the invariant.
	* Recursively looks through the heap, starting from a given index
	* @param index index to start from
	* @return true if the heap order is kept below the given node index
	*/
	public boolean lookThroughHeap(int index){
		int child = index * 2 + 1;
		if(child < currentSize){
			if(!checkChildren(index,child))
				return false;
			else{
				child ++;
				if(child < currentSize)
					return checkChildren(index,child);
				else
					return true;
			}
		}else
			return true;
		
	}
	
	/**
	* Internal method used by the invariant
	* Compares a given child against its parent.
	* If the heap order is kept, it continues checking the heap order below the child.
	* @param parent index of parent
	* @param child index of child
	* @return true if the heap order is kept below the parent
	*/
	private boolean checkChildren(int parent, int child){
		if(comparator.compare(array[parent],array[child]) <= 0)
			return lookThroughHeap(child);
		else
			return false;
	}

	/**
	* Internal method used by the invariant.
	* prints out the heap.
	*/
	private String showHeap(){
			// TODO: return description of heap contents.
			return toString();
	}
        
        public static void main(String [] args){
            Integer [] integers = new Integer[20];
            for(int i = 0; i < integers.length; i++){
                integers[i] = (int)(Math.random()* 100);
            }
            PrioQueue p = new PrioQueue(new Comparator(){
                @Override
                public int compare(Object elm1, Object elm2) {
                    Integer i1 = (Integer)elm1;
                    Integer i2 = (Integer)elm2;
                    if(i1 < i2){
                        return -1;
                    }
                    if(i1 > i2){
                        return 1;
                    }
                    return 0;
                }
            
            },
            integers);
            
            /*end visualize*/
        }
        
        

}

