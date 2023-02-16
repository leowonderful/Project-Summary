// CS 0445 Spring 2022
// Partitionable interface.  Your QuickSort classes should have a
// Partitionable<T> object inside them from with the partition() 
// method will be called.
/*
 * Lingfan Zhang
 * Tue-Thurs 1PM Lecture Fri 9AM Lab
 * CS445
 */
public interface Partitionable<T extends Comparable<? super T>> 
{
	public int partition(T[] a, int first, int last);
}