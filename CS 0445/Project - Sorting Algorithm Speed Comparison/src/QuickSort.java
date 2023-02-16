/*
 * Lingfan Zhang
 * Tue-Thurs 1PM Lecture Fri 9AM Lab
 * CS445
 */
public class QuickSort <T extends Comparable<? super T>> implements Sorter<T> {
	private Partitionable<T> partAlgo;
	private int MIN_SIZE; // min size to recurse, use InsertionSort
	// for smaller sizes to complete sort
	public QuickSort(Partitionable<T> part)
	{
		partAlgo = part;
		MIN_SIZE = 5;
	}
	// remaining code in QuickSort class not shown
	// You must complete this class – in particular the methods
	// sort() and setMin() You will use partAlgo for partition
	// within the sort() method.
	
	public void quickSortImplementation(T[] array, int first, int last) {
		if((last - first + 1) < MIN_SIZE) { //If below min, we do InsertionSort.
			this.insertionSort(array, first, last);
		}
		else {
		
		int partition = partAlgo.partition(array, first, last); //Else partition and let's start quicksort.

		this.quickSortImplementation(array, first, partition-1);
		this.quickSortImplementation(array, partition+1, last);
		}
	}
	
	public void setMin(int s) {
		MIN_SIZE = s;
	}
	
	public void sort(T[] array, int len) {
		quickSortImplementation(array, 0, len-1);
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

