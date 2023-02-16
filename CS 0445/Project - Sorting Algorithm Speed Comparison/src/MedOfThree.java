/*
 * Lingfan Zhang
 * Tue-Thurs 1PM Lecture Fri 9AM Lab
 * CS445
 */
public class MedOfThree <T extends Comparable<? super T>> implements Partitionable<T>  {
	
	public int partition(T[] array, int first, int last) {
		
		int mid = (first+last) / 2; //Find the median value 
		sortParts(array, first, mid, last); //Order the medians.
		
		swap(array, mid, last-1); //Move the soon-to-be pivot the second to last spot.
		int pivIdex = last-1;
		T pivot = array[pivIdex]; // Second to last spot is the pivot. 
		
		int leftDex = first+1; //First item that is not front pivot
		int rightDex = last-2; //First item from the right that is not a pivot. 
		
		//Rest is just like SimplePivot. 
		
		boolean done = false;
		while(!done) {
			while(array[leftDex].compareTo(pivot) < 0) {
				leftDex++;
			}
			while(array[rightDex].compareTo(pivot) > 0) {
				rightDex--;
			}
			
			if(leftDex < rightDex) {
				swap(array,leftDex,rightDex);
				leftDex++;
				rightDex--;
			}
			else done = true;
		}
		
		swap(array, pivIdex, leftDex);
		return leftDex;
		
		
	}
	
	
	//Put the three pivots in order.
	private void sortParts(T[] array, int first, int mid, int last) {
		order(array, first, mid);
		order(array, mid, last);
		order(array, first, mid);
	}
	
	
	//Compare two pivots with each other and swap their positions if necessary. 
	private void order(T[] array, int i, int j) {
		if(array[i].compareTo(array[j]) > 0) {
			swap(array,i,j);
		}
	}
	
	//Swap two items with each other with a temp object. 
	private void swap(Object[] array, int i, int j) {
		Object temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

}
