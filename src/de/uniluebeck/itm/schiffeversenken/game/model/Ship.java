package de.uniluebeck.itm.schiffeversenken.game.model;

/**
 * This class represents a ship.
 * @author leondietrich, modified by I. Schumacher, 
 *  B. Voss, F. Junghans
 * 
 */
public class Ship {
	
	/**
	 * length The length of the ship
	 */
	private int length;
	
	/**
	 * orientation The orientation of the ship: true for vertical, false for horizontal
	 */
	private boolean orientation;
	
	/**
	 * hits The amount of this the ship has currently taken 
	 */
	private int hits = 0;

	/**
	 * This constructor constructs a new ship.
	 * @param length The length of the ship to create.
	 * @param orientation The orientation of the ship: true for vertical, false for horizontal
	 */
	public Ship(int length, boolean orientation) {
		
		//these are the new params for the constructor
		this.length = length;

		this.orientation = orientation;
	}
	/**
	 * Use this method in order to mark another hit.
	 */
	public void hit() {
		
		if (isSunken() == false ) {
			hits++;
		}
	}

	/**
	 * Use this method to check if the ship sunk.
	 * @return True if all parts of the ship were hit and otherwise false.
	 */
	public boolean isSunken() {
		
		if (length == hits) {
			return true;
		}
		else {
		return false;
		}
	}
	
	/**
	 * Use this method as a getter for the orientation 
	 * @return orientation of the ship
	 */
	public boolean isVertical() {
		return orientation;
	}
}
