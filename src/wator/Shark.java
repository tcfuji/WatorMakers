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
	            giveBirth(ocean, myRow, myColumn);
	            timeToGestation = Parameters.sharkGestationPeriod;
	        } else {
	            ocean.set(myRow, myColumn, WATER);
	        }
	        ocean.set(myRow, myColumn, direction, this);
	        justMoved = true;
	        
	        if (myRow + direction.dx < 0) {
	        	myRow = 74;
	        }
	        else if (myRow + direction.dx > 74) {
	        	myRow = 0;
	        }
	        else {
	        	// myRow = (myRow + Wator.getOceanSize()) % Wator.getOceanSize() + direction.dx;
	        	myRow = myRow + direction.dx;
	        }
	        
	        if (myColumn + direction.dy < 0) {
	        	myColumn = 74;
	        }
	        else if (myColumn + direction.dy > 74) {
	        	myColumn = 0;
	        }
	        else {
	        	// myColumn = (myColumn + Wator.getOceanSize()) % Wator.getOceanSize() + direction.dy;
	        	myColumn = myColumn + direction.dy;
	        }
	        timeToGestation -= 1;
    	}
    }

    /* (non-Javadoc)
     * @see wator.Denizen#giveBirth(wator.Ocean, int, int)
     */
    @Override
    public void giveBirth(Ocean ocean, int row, int column) {
        Shark babyShark = new Shark(row, column);
        ocean.set(row, column, babyShark);
//        return babyShark;
    }
    
    public void makeOneStep(Ocean ocean) {
      Denizen[][] array = ocean.getArray();
      timeToStarvation -= 1;
      if (timeToStarvation <= 0) {
          array[myRow][myColumn] = WATER;
          System.out.println(this + " starved.");
          return;
      }
      
      Direction direction = chooseRandomDirection();     
      Denizen neighbor = ocean.get(myRow, myColumn, direction);
      
      if (canMove(ocean, direction) && neighbor != WATER && !(neighbor instanceof Shark)) {
    	  moveAndMaybeGiveBirth(ocean, direction);
    	  timeToStarvation = Parameters.sharkStarvationPeriod;
      }
      else if (canMove(ocean, direction)) {
    	  moveAndMaybeGiveBirth(ocean, direction);
      }

  }
    
    
    
    @Override
    public String toString() {
        return "Shark at (" + myRow + ", " + myColumn + ")";
    }
}
