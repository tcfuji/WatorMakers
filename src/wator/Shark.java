package wator;

import java.awt.Color;

/**
 * @author David Matuszek
 */
public class Shark extends Denizen {
    
    /**
     * Constructs a Shark at a given (row, column) location. 
     * @param row The row to contain the Shark.
     * @param column The column to contain the Shark.
     */
    public Shark(int row, int column) {
        super(row, column);
        timeToGestation = Parameters.sharkGestationPeriod;
        timeToStarvation = Parameters.sharkStarvationPeriod;
        justMoved = false;
    }
    
    /* (non-Javadoc)
     * @see wator.Denizen#getColor()
     */
    @Override
    public Color getColor() {
        return Color.red;
    }

    /* (non-Javadoc)
     * @see wator.Denizen#canMove(wator.Ocean, wator.Direction)
     */
    @Override
    public boolean canMove(Ocean ocean, Direction direction) {
        if (justMoved) {
            justMoved = false;
            return false;
        }
        Denizen neighbor = ocean.get(myRow, myColumn, direction);
        return ! (neighbor instanceof Shark);
    }
    
    /* (non-Javadoc)
     * @see wator.Denizen#moveAndMaybeGiveBirth(wator.Ocean, wator.Direction)
     */
    @Override
    public void moveAndMaybeGiveBirth(Ocean ocean, Direction direction) {
    	if (canMove(ocean, direction)) {
	        if (timeToGestation <= 0) {
	        	System.out.println("Gestate");
	            giveBirth(ocean, myRow, myColumn);
	            timeToGestation = Parameters.sharkGestationPeriod;
	        } else {
	            ocean.set(myRow, myColumn, WATER);
	        }
	        ocean.set(myRow, myColumn, direction, this);
	        	      	        
	        if (myRow + direction.dx < 0) {
	        	myRow = 74;
	        }
	        else if (myRow + direction.dx > 74) {
	        	myRow = 0;
	        }
	        else {
	        	myRow = myRow + direction.dx;
	        }
	        
	        if (myColumn + direction.dy < 0) {
	        	myColumn = 74;
	        }
	        else if (myColumn + direction.dy > 74) {
	        	myColumn = 0;
	        }
	        else {
	        	myColumn = myColumn + direction.dy;
	        }
	        justMoved = true;
    	}
    }

    /* (non-Javadoc)
     * @see wator.Denizen#giveBirth(wator.Ocean, int, int)
     */
    @Override
    public void giveBirth(Ocean ocean, int row, int column) {
        Shark babyShark = new Shark(row, column);
        ocean.set(row, column, babyShark);
    }
    
    public void makeOneStep(Ocean ocean) {
      justMoved = false;
      Denizen[][] array = ocean.getArray();
      timeToStarvation -= 1;
      timeToGestation -= 1;
      System.out.println(timeToStarvation);
      if (timeToStarvation <= 0) {
          array[myRow][myColumn] = WATER;
          
          return;
      }
      
      Direction direction = chooseRandomDirection();     
      Denizen neighbor = ocean.get(myRow, myColumn, direction);
      
      // if you reach a fish...
      if (neighbor != WATER && !(neighbor instanceof Shark)) {
    	  // eat it and set starvation period back to original value
    	  System.out.println("Ate fish!");
    	  moveAndMaybeGiveBirth(ocean, direction);
    	  timeToStarvation = Parameters.sharkStarvationPeriod;
    	  System.out.println(timeToStarvation);
    	  justMoved = true;
      }
      else if (canMove(ocean, direction)) {
    	  moveAndMaybeGiveBirth(ocean, direction);
    	  justMoved = true;
      }

  }
    
    
    
    @Override
    public String toString() {
        return "Shark at (" + myRow + ", " + myColumn + ")";
    }
}
