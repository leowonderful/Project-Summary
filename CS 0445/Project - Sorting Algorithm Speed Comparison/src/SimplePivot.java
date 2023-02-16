/*
 * Lingfan Zhang
 * Tue-Thurs 1PM Lecture Fri 9AM Lab
 * CS445
 */
public class SimplePivot <T extends Comparable<? super T>> implements Partitionable<T>  {
	
	public int partition(T[] array, int first, int last) {
		int pivIdex = last;
		T piv = array[last]; //The pivot is the last item.
		
		int leftDex = first;
		int rightDex = last-1;
		
		boolean done = false;
		while (!done) {
			while(array[leftDex].compareTo(piv) < 0) { //If the ordering is good, then go right
				leftDex++;
			}
			while(array[rightDex].compareTo(piv) > 0 && rightDex > first) { //If ordering is good and we are not past the first item, go left
				rightDex--;
			}
			if(leftDex < rightDex) { //If we get stuck that means one item on left needs to be right, one on right needs to be left. so swap em
				swap(array, leftDex, rightDex);
				leftDex++;
				rightDex--;
			}
			else done=true;
		}
		swap(array,pivIdex, leftDex); //The pivot was stored at the rightmost position, so let's move it into its correct spot now
		pivIdex = leftDex;
		return pivIdex; // This class returns the index of the pivot. 
	}
	
	
	//Swap the position of two items using a temp variable.
	private static void swap(Object[] a, int i, int j) {
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}
