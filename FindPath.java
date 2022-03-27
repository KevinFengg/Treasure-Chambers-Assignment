import java.io.FileNotFoundException;
import java.io.IOException;

public class FindPath {
	private Map pyramidMap;

	public FindPath(String fileName) {
		try {
			
			/*
			 * Creating a new map object with parameters "fileName" and 
			 * assigning its address to pyramidMap.
			 */
			Map aMap = new Map(fileName);
			pyramidMap = aMap;
		}
		catch (InvalidMapCharacterException e) {
			System.out.println("Error. Invalid character was found in input file.");
		}
		catch (FileNotFoundException e) {
			System.out.println("Error. File does not exist.");
		}
		catch (IOException e) {
			System.out.println("An error occured while trying to access the file.");
		}
	}
	
	/*
	 * This method is slightly altered from the method described in
	 * pseudo code in the assignment. The only change I made is that if all
	 * surrounding chambers are marked or null, I backtrack and mark the
	 * backtracked chambers as popped so that the program does not check
	 * the chambers again. The backtracked chamber is also popped from the
	 * doubly-linked list so that shorter paths are found. This process is
	 * repeated until a backtracked chamber that has a non-null and
	 * non-popped chamber is found.
	 */
	public DLStack path() {
		DLStack<Chamber> pathStack = new DLStack<Chamber>();
		Chamber start = pyramidMap.getEntrance();
		int treasureCount = 0;
		Chamber current;
		Chamber bestNeighbor;
		
		pathStack.push(start);
		pyramidMap.getEntrance().markPushed();
		
		while (pathStack.size() != 0) {
			current = pathStack.peek();
			if (current.isTreasure() == true && treasureCount == pyramidMap.getNumTreasures()) {
				current.markExit();
				break;
			}
			else {
				bestNeighbor = bestChamber(current);
				if (bestNeighbor == null) {
					while (bestNeighbor == null) {
						pathStack.pop();
						current.markPopped();
						bestNeighbor = pathStack.pop();
					}
				}
				if (bestNeighbor.isTreasure()) {
					treasureCount ++;
				}
			}
			if (bestNeighbor != null) {
				pathStack.push(bestNeighbor);
				current.markPushed();
			}
		}
		return pathStack;
	}
	
	public Map getMap() {
		return pyramidMap;
	}
	
	/*
	 * A dim chamber is defined as a chamber that is not null, sealed, or
	 * lighted and has at least one of its neighboring chambers lighted.
	 * Using this info, I use methods in "Chamber" to determine if the
	 * chamber is null, sealed, and lighted. Then, if all of these render
	 * not true, I check if any neighboring chambers are lighted. If so, I
	 * return true. False otherwise.
	 */
	public boolean isDim(Chamber currentChamber) {
		if (currentChamber != null && currentChamber.isSealed() == false && currentChamber.isLighted() == false) {
			for (int i = 0; i <= 5; i ++) {
				if (currentChamber.getNeighbour(i) == null) {
					continue;
				}
				if (currentChamber.getNeighbour(i).isLighted() == true) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * Method for finding the best chamber to enter. I first check if
	 * any chambers around the current chamber is null because .isTreasure()
	 * and other methods cannot be invoked because Chamber is null. I return
	 * null if all 6 surrounding chambers are null or marked. I then return
	 * the chamber of highest importance (Treasure > Lighted > Dim).
	 */
	public Chamber bestChamber(Chamber currentChamber) {
		Chamber bestOne = null;
		
		for (int i = 0; i <= 5; i ++) {
			if (currentChamber.getNeighbour(i) == null) {
				continue;
			}
			if (currentChamber.getNeighbour(i).isTreasure() == true && currentChamber.getNeighbour(i).isMarked() == false) {
				bestOne = currentChamber.getNeighbour(i);
				return bestOne;
			}
		}
		
		for (int j = 0; j <= 5; j ++) {
			if (currentChamber.getNeighbour(j) == null) {
				continue;
			}
			if (currentChamber.getNeighbour(j).isLighted() == true && currentChamber.getNeighbour(j).isMarked() == false) {
				bestOne = currentChamber.getNeighbour(j);
				return bestOne;
			}
		}
		
		for (int k = 0; k <= 5; k ++) {
			if (currentChamber.getNeighbour(k) == null) {
				continue;
			}
			if (isDim(currentChamber.getNeighbour(k)) == true && currentChamber.getNeighbour(k).isMarked() == false) {
				bestOne = currentChamber.getNeighbour(k);
				return bestOne;
			}
		}
		return bestOne;
	}
	
	public static void main(String[] args) {
		FindPath aTest = new FindPath("map5.txt");
		//System.out.println(aTest.pyramidMap.getEntrance().getNeighbour(3));
		//aTest.pyramidMap.s
		aTest.pyramidMap.showMap();
		System.out.println(aTest.bestChamber(aTest.pyramidMap.getEntrance()));
		System.out.println(aTest.path());
	}

}
