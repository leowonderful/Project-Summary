import java.util.Random;

/*
 * Lingfan Zhang
 * Tue-Thurs 1PM Lecture Fri 9AM Lab
 * CS445
 */

public class Assig4 {
	public static void main(String[] args) {
		int arraySize = Integer.parseInt(args[0]);
		int numRuns = Integer.parseInt(args[1]);
		double avgBest = 100; //Placeholder value. This value should not be attained in testing
		double avgWorst = 0; //Placeholder value
		int best = 0; //Best runtime algo; we will always get a value so it is safe to put 0 as default
		int worst = 0; //Worst runtime algo; we will always get a value so it is safe to put 0 as default
		
		
		System.out.println("Initialization Information:");
		System.out.println("Array size: " + arraySize);
		System.out.println("Number of runs per test: " + numRuns);
		
		runAlgo[] run = new runAlgo[5];
		
		Sorter[] Sorts = new Sorter[5];
		Sorts[0] = new QuickSort<Integer>(new SimplePivot<Integer>());
		Sorts[1] = new QuickSort<Integer>(new RandomPivot<Integer>());
		Sorts[2] = new QuickSort<Integer>(new MedOfThree<Integer>());
		Sorts[3] = new QuickSort<Integer>(new MedOfFive<Integer>());
		Sorts[4] = new MergeSort<Integer>();
		
		String[] aNames = new String[5];
		aNames[0] = "Simple Pivot";
		aNames[1] = "Random Pivot";
		aNames[2] = "Median Of 3";
		aNames[3] = "Median Of 5";
		aNames[4] = "MergeSort";
		
		for(int i=0;i<=4;i++) {
			run[i] = new runAlgo(Sorts[i],arraySize,numRuns); //run every sort. from here we can get the information of runtimes as well
		}
		
		//Compute worst and best runtimes
		for(int j=0;j<5;j++) {
			if(run[j].getABst() < avgBest) {
				best = j;
				avgBest = run[j].getABst();
			}
			if(run[j].getAWst() > avgWorst) {
				worst = j;
				avgWorst = run[j].getAWst();
			}
		}
		//Display results
		System.out.println("After the tests, here is the best setup: ");
		System.out.println("\t Algorithm: " + aNames[best]);
		System.out.println("\t Min Recurse: " + run[best].getBRec());
		System.out.println("\t Average: " + run[best].getABst() + "\n");
		System.out.println("After the tests, here is the worst setup: ");
		System.out.println("\t Algorithm: " + aNames[worst]);
		System.out.println("\t Min Recurse: " + run[worst].getWrec());
		System.out.println("\t Average: " + run[worst].getAWst() + "\n");
		System.out.println("Here are the per algorithm results: ");
		for(int f=0;f<5;f++) {
			System.out.println("Algorithm: " + aNames[f]);
			System.out.println("\t Best Result: ");
			System.out.println("\t \t Min Recurse: " + run[f].getBRec());
			System.out.println("\t \t Average " + run[f].getABst());
			System.out.println("\t Worst Result: ");
			System.out.println("\t \t Min Recurse: " + run[f].getWrec());
			System.out.println("\t \t Average " + run[f].getAWst());
			
		}
	}

}
