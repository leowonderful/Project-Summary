import java.util.Random;
/*
 * Lingfan Zhang
 * Tue-Thurs 1PM Lecture Fri 9AM Lab
 * CS445
 */
public class RandomPivot <T extends Comparable<? super T>> implements Partitionable<T>  {
	
	public int partition(T[] array, int first, int last) {
		Random rand = new Random();
		int pivIdex = rand.nextInt(last - first) + 1; // Pick a random index for the pivot.
		T piv = array[pivIdex];
		
		swap(array, pivIdex, last); //The pivot may or may not be the last item, but move it to the end anyways
		pivIdex = last;
		
		int leftDex = first;
		int rightDex = last-1;
		
		//Rest is standard partitioning as found in simple pivot; I won't be commenting on it for this reason. 
		
		boolean done = false;
		while (!done) {
			while(array[leftDex].compareTo(piv) < 0) {
				leftDex++;
			}
			while(array[rightDex].compareTo(piv) > 0 && rightDex > first) {
				rightDex--;
			}
			if(leftDex < rightDex) {
				swap(array, leftDex, rightDex);
				leftDex++;
				rightDex--;
			}
			else done=true;
		}
		swap(array,pivIdex, leftDex);
		pivIdex = leftDex;
		return pivIdex;
	}
	
	private static void swap(Object[] a, int i, int j) {
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}