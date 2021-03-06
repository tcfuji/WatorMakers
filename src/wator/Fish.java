package wator;

import java.awt.Color;

/**
 * @author David Matuszek
 */
public class Fish extends Denizen {
    
    /**
     * Constructs a Fish at a given (row, column) location. 
     * @param row The row to contain the Fish.
     * @param column The column to contain the Fish.
     */
     public Fish(int row, int column) {
        super(row, column);
        timeToGestation = Parameters.fishGestationPeriod;
//        timeToStarvation = Parameters.fishStarvationPeriod;
    }
    
    @Override
    public Color getColor() {
        return Color.blue;
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
        return neighbor == WATER;
    }
    
    /* (non-Javadoc)
     * @see wator.Denizen#moveAndMaybeGiveBirth(wator.Ocean, wator.Direction)
     */
    @Override
    public void moveAndMaybeGiveBirth(Ocean ocean, Direction direction) {
    	if (canMove(ocean, direction)) {
	        if (timeToGestation <= 0) {
	            giveBirth(ocean, myRow, myColumn);
	            timeToGestation = Parameters.fishGestationPeriod; 
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
	        timeToGestation -= 1;
    	}
    }

    /* (non-Javadoc)
     * @see wator.Denizen#giveBirth(wator.Ocean, int, int)
     */
    @Override
    public void giveBirth(Ocean ocean, int row, int column) {
        Fish babyFish = new Fish(row, column);
        ocean.set(row, column, babyFish);
    }
    
    @Override
    public void makeOneStep(Ocean ocean) {

        Direction direction = chooseRandomDirection();
        moveAndMaybeGiveBirth(ocean, direction);

        if (canMove(ocean, direction)) {
            moveAndMaybeGiveBirth(ocean, direction);
        }

    }
    
    /* (non-Javadoc)
     * @see wator.Denizen#toString()
     */
    @Override
    public String toString() {
        return "Fish at (" + myRow + ", " + myColumn + ")";
    }
}
