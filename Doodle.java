// Nick Perry
// CPTR 241
// HW #20 Predator Prey project Main Class
// 3-12-18
// ========================================

import javax.swing.*;

public class Doodle {
	  public static void main(String args[]) {  
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() { 
				new World(); //Aladin reference.
			} 
		}); 
	}
}

