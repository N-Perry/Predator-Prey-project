// Nick Perry
// CPTR 241
// HW #20 Predator Prey project Bug Class: This class stores all the information for Bugs, both doodlebugs and ants are Bug objects.
// 3-12-18
// =================================================================================================================================

public class Bug {
	private int bugType; //0 = Empty, 1 = Ant, 2 = Doodle
	private int stepCtr = 0;
	private boolean hasMoved = false;
	int sinceEaten = 0;
	
	Bug(int b) {
		if((b > -1) & (b < 3)) {
			bugType = b;
		}
		else {
			bugType = 0;
			System.out.println("Invalid bug type (" + b
					+ "), default value (0) used");
		}
	}
	
	// accessor functions for all member variables
	public int getType() {return bugType;}
	public int getStep() {return stepCtr;}
	public boolean getHM() {return hasMoved;}
	public int getSE() {return sinceEaten;}
	
	// mutator methods for all member variables
	public void setType(int b) {
		if((b > -1) & (b < 3)) {
			bugType = b;
		}
		else {
			bugType = 0;
			System.out.println("Invalid bug type (" + b
					+ "), default value (0) used");
		}
	}
	public void setSC(int i) {stepCtr = i;}
	public void setHM(boolean m) {hasMoved = m;}
	public void setSE(int j) {sinceEaten = j;}
	
	// function to 'kill' a bug, resetting all it's information to that of an empty 'bug' on the grid.
	public void kill() {
		bugType = 0;
		stepCtr = 0;
		hasMoved = false;
		sinceEaten = 0;
	}
	
	// function for breeding, creates a new ant in a space
	void newAnt() {
		bugType = 1;
		stepCtr = 0;
		hasMoved = false;
		sinceEaten = 0;	
	}
	
	// function for breeding, creates a new doodlebug in a space
	void newDoodle() {
		bugType = 2;
		stepCtr = 0;
		hasMoved = false;
		sinceEaten = 0;
	}
}
