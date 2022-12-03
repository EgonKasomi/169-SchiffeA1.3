package de.uniluebeck.itm.schiffeversenken.game.model;

/**
 * This class represents a ship.
 * @author leondietrich, modified by I. Schumacher, 
 * modified by B. Voss, modified by F. Junghans
 * Group   169	
 */
public class Ship {
	
	/**
	 * @param length The length of the ship
	 * @param orientation The orientation of the ship: true for vertical, false for horizontal
	 * @param hits The amount of this the ship has currently taken 
	 */
	private int length;
	
	private boolean orientation;
	
	private int hits = 0;

	/**
	 * This constructor constructs a new ship.
	 * @param length The length of the ship to create.
	 * @param orientation The orientation of the ship: true for vertical, false for horizontal
	 * @param hits The amount of hits the ship has currently taken
	 */
	public Ship(int length, boolean orientation) {
		
		//these are the new params for the constructor
		length = this.length;

		orientation = this.orientation;
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
	
	/*
	 * Use this method as a getter for the orientation 
	 */
	public boolean isVertical() {
		return orientation;
	}
}
