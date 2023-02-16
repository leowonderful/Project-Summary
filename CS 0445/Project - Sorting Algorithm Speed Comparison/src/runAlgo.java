import java.util.Random;
/*
 * Lingfan Zhang
 * Tue-Thurs 1PM Lecture Fri 9AM Lab
 * CS445
 */
public class runAlgo {
	
	private int seed = 5318008;
	private Sorter algo;
	private double avgBest = 100; //No runs conducted so best/worst is currently 100. 
	private double avgWorst = 0; //No runs conducted so best/worst is currently 0.
	private int bestRec; 
	private int worstRec;
	
	public runAlgo(Sorter algo, int len, int numRuns) {
		this.algo = algo;
		this.test(len, numRuns);
	}
	//Getters and setters for when we are pulling best and worst results etc from the assig4 
	public int getBRec() {
		return bestRec;
	}
	public int getWrec() {
		return worstRec;
	}
	
	public double getABst() {
		return avgBest;
	}
	
	public double getAWst() {
		return avgWorst;
	}
	
	private void test(int len, int numRuns) {
		Integer[] toSort = new Integer[len];  //Initialize the array, 1 to N
		for(int j=0;j<len;j++) {
			toSort[j] = j+1;
		}
		Random rand = new Random(seed); //Seed our array
		
		for(int recSize = 5; recSize <= 160; recSize = recSize * 2) {
			//Integer[] toSort = new Integer[len];
			double runTime = 0;
			int arrLength = toSort.length;
			
			//Shuffle the array around
			for(int i = 0; i<numRuns;i++) {
				for(int f=0;f<len;f++) {
					int s = rand.nextInt(len);
					Integer temp = toSort[i];
					toSort[i] = toSort[s];
					toSort[s] = temp;
				}
				
				//Set recursive sizes
				algo.setMin(recSize);
				long initialTime = System.nanoTime();
				algo.sort(toSort, arrLength);
				long endTime = System.nanoTime();
				double thisRun = (double) (endTime - initialTime) / 1000000000; //Conversion to seconds
				runTime = runTime + thisRun;
			}
			double average = runTime / numRuns;
			//Update the best and worst if necessary every run.
			if(average > avgWorst) {
				avgWorst = average;
				worstRec = recSize;
			}
			if(average < avgBest) {
				avgBest = average;
				bestRec = recSize;
			}
		}
	}
}