// CS 0445 Spring 2022
// Assignment 4
// Your QuickSort and MergeSort classes should work correctly with this program
// without any changes.  Your output should exactly match that shown in the
// sample output file (with size = 25).  Read over the additional comments below
// to see how your sorting classes must be set up.  You can also get some hints 
// about your main program (Assig4.java) implementation from this program.
// 
// Dependencies:  This program requires the following:
//		Interface Sorter<T> in file Sorter.java (provided)
//		Interface Partitionable<T> in file Partitionable.java (provided)
//		Class SimplePivot<T> in file SimplePivot.java
//		Class MedOfThree<T> in file MedOfThree.java
//		Class RandomPivot<T> in file RandomPivot.java
//		Class MedOfFive<T> in file MedOfFive.java
//		Class QuickSort<T> in file QuickSort.java
//		Class MergeSort<T> in file MergeSort.java

import java.util.*;
public class SortTest
{
	public static Random R = new Random();  // Make a single Random object
	
	// Sorter data will be an ArrayList of Sorter<Integer> objects.  Note
	// the nested type parameters -- think about what they mean.  Polymorphism
	// allows this to work, since all of the sort classes will implement the
	// Sorter<T> interface.
	private ArrayList<Sorter<Integer>> sorts;
	// Data to sort will be an array of Integer
	private Integer [] A, temp;	
	private int size;
	
	// Fill array with random data
	public void fillArray(Integer [] arr)
	{
		for (int i = 0; i < A.length; i++)
		{
			// Values will be 0 <= X < 2 billion
			arr[i] = Integer.valueOf(R.nextInt(2000000000));
		}
	}
	
	public void copyArray(Integer [] orig, Integer [] copy)
	{
		for (int i = 0; i < orig.length; i++)
			copy[i] = orig[i];
	}

	public void showArray(Integer [] arr)
	{
		for (int i = 0; i < arr.length; i++)
		{
			System.out.print(arr[i] + " ");
		}
		System.out.println("\n");
	}

	public SortTest(String sz)
	{
		// Set the array size from the sz parameter, which is passed in from a
		// command line arbumetn -- see main() method below.
		size = Integer.parseInt(sz);
		
		// Put the sorting objects into the ArrayList.  Note how each object is being
		// created.  The QuickSort<T> objects are passed implementations of the
		// Partitionable<T> interface in order to allow the 4 different ways of 
		// partitioning the data.
		sorts = new ArrayList<Sorter<Integer>>();
		sorts.add(new QuickSort<Integer>(new SimplePivot<Integer>()));
		sorts.add(new QuickSort<Integer>(new MedOfThree<Integer>()));
		sorts.add(new QuickSort<Integer>(new RandomPivot<Integer>()));
		sorts.add(new QuickSort<Integer>(new MedOfFive<Integer>()));
		sorts.add(new MergeSort<Integer>());
		
		temp = new Integer[size];
		A = new Integer[size];
		
		R.setSeed(1234);  // Seed the random number generator.  This will allow the
					// program to generate the exact same "random" data at each execution.
					// This is useful for comparison / analysis purposes.
		
		fillArray(temp);  // put random data into temp array
		
		// Iterate through all of the sorts and test each one
		for (int i = 0; i < sorts.size(); i++)
		{
			copyArray(temp, A);  // copy random data from temp into array A.  This will
						// enable all of the sorts to use the identical random data, without
						// having to generate new Integer objects for each algorithm.
						// When dealing with large amounts of data we need to be aware of
						// space requirements and how much memory would be consumed (and
						// need to be garbage collected) if we generate new Integer objects
						// for every test case.
						
			System.out.println("Initial data for algo " + i);
			showArray(A);
			// Get the current Sorter<T> object, set the min and sort the data
			sorts.get(i).setMin(5);
			sorts.get(i).sort(A, A.length);
			System.out.println("Sorted data for algo " + i);
			showArray(A);
			System.out.println("--------------------------------");
		}
	}
					
	public static void main(String [] args)
	{
		new SortTest(args[0]);
	}
}

