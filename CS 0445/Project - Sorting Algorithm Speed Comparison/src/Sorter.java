// CS 0445 Spring 2022
// Sorter<T> interface.  All of your sorting classes must implement
// this interface.  To see how this interface is used, read over
// the SortTest.java program.
/*
 * Lingfan Zhang
 * Tue-Thurs 1PM Lecture Fri 9AM Lab
 * CS445
 */
public interface Sorter<T extends Comparable<? super T>> 
{
	public void sort(T[] a, int size);
	public void setMin(int minSize);
}