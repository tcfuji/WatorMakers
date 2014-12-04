package wator;

import java.awt.Color;
import java.util.Random;

/**
 * This class represents a "denizen" (creature) that may be in the Ocean.
 * It specifies values that are necessary for each denizen, such as its
 * location and its time to starvation, and methods that are useful or
 * that must be supplied.
 * Subclasses of Denizen are Shark, Fish, and Water.
 * @author David Matuszek
 */
public abstract class Denizen {
    
    /** The row containing this Denizen. */
    int myRow;
    
    /** The column containing this Denizen. */
    int myColumn;
    
    /** The number of time units this Denizen can survive without starving. */
    int timeToStarvation;
    
    /** The number of time units this Denizen tries to give birth. */
    int timeToGestation;
    
    /** 
     * Whether this Denizen has moved this turn. Each Denizen should be
     * permitted only one chance to move each turn, else it could move
     * a considerable distance.
     */
    boolean justMoved;
    
    private Random rand = new Random();
    
    Denizen WATER = Water.getInstance();

    /**
     * Constructs a generic Denizen at a given (row, column) location. 
     * @param row The row to contain the Denizen.
     * @param column The column to contain the Denizen.
     */

   public Denizen(int row, int column) {
       myRow = row;
       myColumn = column;
   }
   
/**
 * @return The color of this Denizen.
 */
public abstract Color getColor();
    
    /**
     * @return A randomly chosen Direction.
     */
    protected Direction chooseRandomDirection() {
        int n = rand.nextInt(4);
        switch(n) {
            case 0: return Direction.LEFT;
            case 1: return Direction.RIGHT;
            case 2: return Direction.UP;
            default: return Direction.DOWN;
        }
    }
    
    /**
     * Gives each Denizen in the Ocean a chance to starve, move, and/or
     * give birth. This method takes care of starvation, but movement
     * and gestation must be handled by methods specific to the type
     * of Denizen.
     * @param ocean The Ocean containing all the Denizens.
     */
    public abstract void makeOneStep(Ocean ocean);
//    {
//        Denizen[][] array = ocean.getArray();
//        timeToStarvation -= 1;
//        if (timeToStarvation <= 0) {
//            array[myRow][myColumn] = WATER;
//            System.out.println(this + " starved.");
//            return;
//        }
//        Direction direction = chooseRandomDirection();
//        if (timeToStarvation <= 0) {
//        	die(ocean);
//        }
//        else if (canMove(ocean, direction)) {
//            moveAndMaybeGiveBirth(ocean, direction);
//        }
//        else {
//        	 moveAndMaybeGiveBirth(ocean, direction);
//        }
//    }
    
    /**
     * Determines whether this Denizen can move in the given direction.
     * @param ocean The Ocean that contains this Denizen.
     * @param direction The Direction in which this Denizen will try to move.
     * @return true if the move is possible.
     */
    public abstract boolean canMove(Ocean ocean, Direction direction);
    
    /**
     * Causes this Denizen to move, and perhaps give birth (by creating a
     * new Denizen of the same type in the location just vacated.)
     * @param ocean The ocean containing this Denizen.
     * @param direction The direction in which a move might occur.
     */
    public abstract void moveAndMaybeGiveBirth(Ocean ocean, Direction direction);
    
    /**
     * Creates a new Denizen of the same type.
     * @param ocean The ocean.
     * @param row The row which contains the new baby Denizen.
     * @param column The column which contains the new baby Denizen.
     * @return The new baby Denizen.
     */
    public abstract void giveBirth(Ocean ocean, int row, int column);
    
    public void die(Ocean ocean) {
    	ocean.set(myRow, myColumn, WATER);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Denizen at (" + myRow + ", " + myColumn + ")";
    }
}
