/*
 * Lingfan Zhang
 * Tue-Thurs 1PM Lecture Fri 9AM Lab
 * CS445
 */
public class MergeSort<T extends Comparable<? super T>> implements Sorter<T> {
	private int MIN_SIZE; // min size to recurse, use InsertionSort
	// for smaller sizes to complete sort
	public MergeSort() {
		MIN_SIZE = 5;
	}
	
	public void setMin(int min) {
		MIN_SIZE = min;
	}
	
	// remaining code in MergeSort class not shown
	// You must complete this class – in particular the methods
	// sort() and setMin().
	
	
	public void sort(T[] array, int len) {
		T[] temp = (T[]) new Comparable<?>[array.length];
		this.mergeSortImplementation(array,temp,0,len-1);
	}
	
	private void mergeSortImplementation(T[] array, T[] temp, int first, int last) {
		   if ((last - first + 1) < MIN_SIZE) { //If below min size, begin insertionsorting
			   this.insertionSort(array, first, last);
		   }
		   else {
		      int mid = (first + last)/2;
		      mergeSortImplementation(array, temp, first, mid); //sort left half
		      mergeSortImplementation(array, temp, mid + 1, last); //sort right half

				if (array[mid].compareTo(array[mid + 1]) > 0)      
		     	 	merge(array, temp, first, mid, last); 
		   }  
		}  
	
	//Code references TextMergeQuick
	
	private void merge(T[] a, T[] tempArray, int first, int mid, int last) {
		{
			int beg = first; 
			int end = mid;
			int beg2 = mid + 1;
			int end2 = last;
			int i = beg;
			//Ordering
			for (; (beg <= end) && (beg2 <= end2); i++) {
		      if (a[beg].compareTo(a[beg2]) <= 0) {  
		      	tempArray[i] = a[beg];
		        beg++;
		      }
		      else {  
		      	tempArray[i] = a[beg2];
		        beg2++;
		      }
		   }
			// finish first and second arrays then copy back
		   for (; beg <= end; beg++, i++)
		      tempArray[i] = a[beg];
			for (; beg2 <= end2; beg2++, i++)
		      tempArray[i] = a[beg2];
		   for (i= first; i <= last; i++)
		      a[i] = tempArray[i];
		}  // end merge
	}
	
	// Below is InsertionSort From Recitation 7
	
	private void insertionSort(T[] a, int first, int last) {
		int unsorted;
		
		for(unsorted = first + 1; unsorted <= last; unsorted++) {
			T firstOne = a[unsorted];
			this.insertInOrder(firstOne, a, first, unsorted-1);
		}
	}
	
	private void insertInOrder(T compare, T[] array, int begin, int end) {
		int index;
		for(index = end; (index >= begin) && (compare.compareTo(array[index]) < 0); index--) {
			if(compare.compareTo(array[index]) < 0) {
				array[index+1] = array[index];
			}
			else break;
		}
		array[index+1] = compare;
	}
	
	
}