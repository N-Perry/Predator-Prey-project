// Nick Perry
// CPTR 241
// HW #20 Predator Prey project World Class: This class contains all the functionality that populates a grid of bugs, moves these bugs around,
// and handles interactions between them such as death (and hopefully eventually reproduction). Swing functionality is mosty in this class as well.
// 3-12-18
// ================================================================================================================================================

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class World {
	// size of the World, world is a 2D array of bug objects
	public final int size = 13;
	Bug grid[][] = new Bug[size][size];
	
	// add ImgaeIcon objects for ants and doodlebugs
	ImageIcon antIcon = new ImageIcon("ant.jpg");
	ImageIcon doodleIcon = new ImageIcon("bug.jpg");
	ImageIcon blankIcon = new ImageIcon("myIcon.gif");
	
	// random number generator for bug movement
	Random rand = new Random();
	
	// button to progress the game turn by turn
	JButton nextBtn = new JButton("Move");
	
	// an array of labels for the world frame
	JLabel labels[][] = new JLabel[size][size];
	
	// had to add this out here so that all functions could access it? (populate function)
	JFrame gridFrame = new JFrame("Doodle");
	
	
	World() {
		
		// one frame for the world, one frame that will hold a button to increment the world.
		JFrame btnFrame = new JFrame("TurnIncrementer");
		
		// establish the Layouts for both frames
		gridFrame.getContentPane().setLayout(new GridLayout(size, size));
		btnFrame.getContentPane().setLayout(new FlowLayout());
		
		// format the Jframe dimensions
		gridFrame.setSize(1700, 1100);
		btnFrame.setSize(150, 80);
		
		// Terminate each program when the user closes the applications. 
	    gridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    btnFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// initially populates world
		populate();
		
		// button to progress the game turn by turn
		JButton nextBtn = new JButton("Move");
		
		// add action listener for progression button
		nextBtn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		moveBugs();
	    		System.out.println();
	    	}	
	    });
				
		// add button to button frame
		btnFrame.add(nextBtn);
		
		// set both frames to visible
		gridFrame.setVisible(true);
		btnFrame.setVisible(true);
	}
	
	void populate() {
		for(int i = 0; i < size; i++) {
			for(int i2 = 0; i2 < size; i2++) {
				int randValue = rand.nextInt(100);
				
				// randomly populates board with empty 'bugs'
				if (randValue > 30) {
					grid[i][i2] = new Bug(0);
					labels[i][i2] = new JLabel(blankIcon);
				}
				
				// else, populate space with ant
				else if (randValue < 25){
					grid[i][i2] = new Bug(1);
					labels[i][i2] = new JLabel(antIcon);
				}
				
				// else, populate space with doodlebug
				else if (randValue > 25 && randValue <= 30){
					grid[i][i2] = new Bug(2);
					labels[i][i2] = new JLabel(doodleIcon);
				}
				
				// add labels to the frame
				gridFrame.getContentPane().add(labels[i][i2]);
			}
		}
	}
	
	
	// prints labels to console.
	/*void toConsole() {
		for(int i = 0; i < size; i++) {
			for(int i2 = 0; i2 < size; i2++) {
				System.out.print(labels[i][i2].getText());
			}
			System.out.println();
		}
	}*/
	
	public void moveBugs() {
		for(int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// if the bug has moved already, reset the Has Moved member variable to false, and skip all the movement checks.
				if (grid[i][j].getHM() == true) grid[i][j].setHM(false);
				
				// if the bug hasn't moved already in this run-through, go through checks.
				else {
					int randValue = rand.nextInt(4); // for each grid[][] location, a new rand value from 1-4 is generated
					
					// I've put all the "bug next-to bug" checks first, so that the doodlebugs will "prioritize"
					// eating a bug over moving to an empty space.
					
					// if a doodlebug has been alive 3 turns and hasn't eaten, it dies
					if (grid[i][j].getType() == 2 && grid[i][j].getSE() == 3) {
						grid[i][j].kill();
						labels[i][j].setIcon(blankIcon);
						System.out.println("a doodlebug has starved!");
					}
					
					// if an ant has been alive 3 turns, it should breed
					if (grid[i][j].getType() == 1 && grid[i][j].getStep() % 3 == 0 && grid[i][j].getStep() != 0) {
						
						// if space is open, new ant created above
						if ((i-1) >= 0 && grid[i-1][j].getType() == 0) {
							grid[i-1][j].newAnt();
							labels[i-1][j].setIcon(antIcon);
						}
						
						// if space is open, new ant created to the right
						else if ((j+1) < size && grid[i][j+1].getType() == 0) {
							grid[i][j+1].newAnt();
							labels[i][j+1].setIcon(antIcon);
						}
						
						// if space is open, new ant created below
						else if ((i+1) < size && grid[i+1][j].getType() == 0) {
							grid[i+1][j].newAnt();
							labels[i+1][j].setIcon(antIcon);
						}
						
						// if space is open, new ant created to the left
						else if ((j-1) >= 0 && grid[i][j-1].getType() == 0) {
							grid[i][j-1].newAnt();
							labels[i][j-1].setIcon(antIcon);
						}
					}
					
					// if a doodlebug has been alive 8 turns, it should breed
					if (grid[i][j].getType() == 2 && grid[i][j].getStep() % 8 == 0 && grid[i][j].getStep() != 0) {
						
						// if space is open, new doodle created above
						if ((i-1) >= 0 && grid[i-1][j].getType() == 0) {
							grid[i-1][j].newDoodle();
							labels[i-1][j].setIcon(doodleIcon);
						}
						
						// if space is open, new doodle created to the right
						else if ((j+1) < size && grid[i][j+1].getType() == 0) {
							grid[i][j+1].newDoodle();
							labels[i][j+1].setIcon(doodleIcon);
						}
						
						// if space is open, new doodle created below
						else if ((i+1) < size && grid[i+1][j].getType() == 0) {
							grid[i+1][j].newDoodle();
							labels[i+1][j].setIcon(doodleIcon);
						}
						
						// if space is open, new doodle created to the left
						else if ((j-1) >= 0 && grid[i][j-1].getType() == 0) {
							grid[i][j-1].newDoodle();
							labels[i][j-1].setIcon(doodleIcon);
						}
					}
					
					// increment step counter and sinceEaten counter for all bugs
					grid[i][j].setSC(grid[i][j].getStep() + 1);
					if (grid[i][j].getType() == 2) {
						grid[i][j].setSE(grid[i][j].getSE() + 1);
					}
					
					// for bugs with ants above them
					if (grid[i][j].getType() != 0 && (i-1) >= 0 && grid[i-1][j].getType() == 1) {
						
						// if an ant is above an ant
						if (grid[i][j].getType() == 1) {
							System.out.println(" an ant is above an ant. ");
						}
						
						// if an ant is above a doodlebug
						else if (grid[i][j].getType() == 2) {
							grid[i][j].setSE(0);                     // the doodlebug has eaten! set sinceEaten to 0
							                                         // all of these lines
							grid[i-1][j].setHM(grid[i][j].getHM());  // take the old bugs info
							grid[i-1][j].setSC(grid[i][j].getStep());  // and store it in the
							grid[i-1][j].setType(grid[i][j].getType()); // new bug's location.
							grid[i-1][j].setSE(grid[i][j].getSE()); // 
							grid[i][j].kill(); // "kill" (reset) old bug position
							labels[i-1][j].setIcon(doodleIcon); // reset labels appropriately. this code is repeated for all else-ifs below, one for each compass direction.
							labels[i][j].setIcon(blankIcon);
							System.out.println("a doodlebug ate an ant above it!");
						}
					}
					
					// for bugs with ants to the right of them
					else if (grid[i][j].getType() != 0 && (j+1) < size && grid[i][j+1].getType() == 1) {
						
						// if an ant is to the right of an ant
						if (grid[i][j].getType() == 1) {
							System.out.println("an ant is to the right of an ant!");
						}
						
						// if an ant is to the right of a doodlebug
						else if (grid[i][j].getType() == 2) {
							grid[i][j].setSE(0);
							grid[i][j].setHM(true);
							grid[i][j+1].setHM(grid[i][j].getHM());
							grid[i][j+1].setSC(grid[i][j].getStep());
							grid[i][j+1].setType(grid[i][j].getType());
							grid[i][j+1].setSE(grid[i][j].getSE());
							grid[i][j].kill();
							labels[i][j+1].setIcon(doodleIcon);
							labels[i][j].setIcon(blankIcon);
							System.out.println("a doodlebug ate an ant to the right of it! ");
						}
					}
					
					// for bugs with ants below them
					else if (grid[i][j].getType() != 0 && (i+1) < size && grid[i+1][j].getType() == 1) {
						
						// if an ant is below an ant
						if (grid[i][j].getType() == 1) {
							System.out.println("an ant is below an ant!");
						}
						
						// if an ant is below a doodlebug
						else if (grid[i][j].getType() == 2) {
							grid[i][j].setSE(0);
							grid[i][j].setHM(true);
							grid[i+1][j].setHM(grid[i][j].getHM());
							grid[i+1][j].setSC(grid[i][j].getStep());
							grid[i+1][j].setType(grid[i][j].getType());
							grid[i+1][j].setSE(grid[i][j].getSE());
							grid[i][j].kill();
							labels[i+1][j].setIcon(doodleIcon);
							labels[i][j].setIcon(blankIcon);
							System.out.println("a doodlebug ate an ant below it!");
						}
					}
					
					// for bugs with ants to the left of them
					else if (grid[i][j].getType() != 0 && (j-1) >= 0 && grid[i][j-1].getType() == 1) {
						
						// if an ant is to the left of an ant
						if (grid[i][j].getType() == 1) {
							System.out.println("an ant is to the left of an ant!");
						}
						
						// if an ant is to the left of a doodlebug
						else if (grid[i][j].getType() == 2) {
							grid[i][j].setSE(0);
							grid[i][j-1].setHM(grid[i][j].getHM());
							grid[i][j-1].setSC(grid[i][j].getStep());
							grid[i][j-1].setType(grid[i][j].getType());
							grid[i][j-1].setSE(grid[i][j].getSE());
							grid[i][j].kill();
							labels[i][j-1].setIcon(doodleIcon);
							labels[i][j].setIcon(blankIcon);
							System.out.println("A doodlebug ate an ant to the left of it!");
						}
					}
					
					// for bugs moving up into empty space
					if (grid[i][j].getType() != 0 && randValue == 0 && (i-1) >= 0 && grid[i-1][j].getType() == 0) {
						
						// ant move up
						if (grid[i][j].getType() == 1) {            // all of these lines
							grid[i-1][j].setHM(grid[i][j].getHM());  // take the old bugs info
							grid[i-1][j].setSC(grid[i][j].getStep());  // and store it in the
							grid[i-1][j].setType(grid[i][j].getType()); // new bug's location.
							grid[i][j].kill(); // "kill" (reset) old bug position
							labels[i-1][j].setIcon(antIcon); // reset labels appropriately. this code is repeated for all else-ifs below, one for each compass direction.
							labels[i][j].setIcon(blankIcon);
						}
						
						// doodlebug move up
						else if (grid[i][j].getType() == 2) {     // all of these lines
							grid[i-1][j].setHM(grid[i][j].getHM());  // take the old bugs info
							grid[i-1][j].setSC(grid[i][j].getStep());  // and store it in the
							grid[i-1][j].setType(grid[i][j].getType()); // new bug's location.
							grid[i-1][j].setSE(grid[i][j].getSE());
							grid[i][j].kill(); // "kill" (reset) old bug position
							labels[i-1][j].setIcon(doodleIcon); // reset labels appropriately. this code is repeated for all else-ifs below, one for each compass direction.
							labels[i][j].setIcon(blankIcon);
						}
					}
					
				
					// for bugs moving right into empty space
					else if (grid[i][j].getType() != 0 && randValue == 1 && (j+1) < size && grid[i][j+1].getType() == 0) {
						
						// ant move right
						if (grid[i][j].getType() == 1) {
							grid[i][j].setHM(true);
							grid[i][j+1].setHM(grid[i][j].getHM());
							grid[i][j+1].setSC(grid[i][j].getStep());
							grid[i][j+1].setType(grid[i][j].getType());
							grid[i][j].kill();
							labels[i][j+1].setIcon(antIcon);
							labels[i][j].setIcon(blankIcon);
						}
						
						// doodlebug move right
						else if (grid[i][j].getType() == 2) {
							grid[i][j].setHM(true);
							grid[i][j+1].setHM(grid[i][j].getHM());
							grid[i][j+1].setSC(grid[i][j].getStep());
							grid[i][j+1].setType(grid[i][j].getType());
							grid[i][j+1].setSE(grid[i][j].getSE());
							grid[i][j].kill();
							labels[i][j+1].setIcon(doodleIcon);
							labels[i][j].setIcon(blankIcon);
						}		
					}
					
					// for bugs moving down into empty space
					else if (grid[i][j].getType() != 0 && randValue == 2 && (i+1) < size && grid[i+1][j].getType() == 0) {
						
						// ant move down
						if (grid[i][j].getType() == 1) {
							grid[i][j].setHM(true);
							grid[i+1][j].setHM(grid[i][j].getHM());
							grid[i+1][j].setSC(grid[i][j].getStep());
							grid[i+1][j].setType(grid[i][j].getType());
							grid[i][j].kill();
							labels[i+1][j].setIcon(antIcon);
							labels[i][j].setIcon(blankIcon);
						}
						
						// doodlebug move down
						else if (grid[i][j].getType() == 2) {
							grid[i][j].setHM(true);
							grid[i+1][j].setHM(grid[i][j].getHM());
							grid[i+1][j].setSC(grid[i][j].getStep());
							grid[i+1][j].setType(grid[i][j].getType());
							grid[i+1][j].setSE(grid[i][j].getSE());
							grid[i][j].kill();
							labels[i+1][j].setIcon(doodleIcon);
							labels[i][j].setIcon(blankIcon);
						}		
					}
					
					// for bugs moving left into empty space
					else if (grid[i][j].getType() != 0 && randValue == 3 && (j-1) >= 0 && grid[i][j-1].getType() == 0) {
						
						// ant move left
						if (grid[i][j].getType() == 1) {
							grid[i][j-1].setHM(grid[i][j].getHM());
							grid[i][j-1].setSC(grid[i][j].getStep());
							grid[i][j-1].setType(grid[i][j].getType());
							grid[i][j].kill();
							labels[i][j-1].setIcon(antIcon);
							labels[i][j].setIcon(blankIcon);
						}
						
						// doodlebug move left
						else if (grid[i][j].getType() == 2) {
							grid[i][j-1].setHM(grid[i][j].getHM());
							grid[i][j-1].setSC(grid[i][j].getStep());
							grid[i][j-1].setType(grid[i][j].getType());
							grid[i][j-1].setSE(grid[i][j].getSE());
							grid[i][j].kill();
							labels[i][j-1].setIcon(doodleIcon);
							labels[i][j].setIcon(blankIcon);	
						}
					}
				}
			}
		}
	}
}

