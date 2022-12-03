package de.uniluebeck.itm.schiffeversenken.game.model;

import java.util.*;
import java.util.function.Consumer;

import de.uniluebeck.itm.schiffeversenken.engine.Vec2;
import de.uniluebeck.itm.schiffeversenken.game.model.FieldTile.FieldTileState;

/**
 * This class represents a players field. There are at least two instances of
 * this class in a game.
 * 
 * @author leondietrich, modified by I. Schumacher, modified by B. Voss, modified by F. Junghans
 * 
 */
public final class GameField {

	/**
	 * The vector containing the number of columns and rows
	 */
	private final Vec2 size;
	
	/**
	 * The 2D Matrix containing all FieldTiles
	 */
	private final FieldTile[][] field;
	
	/**
	 * The list of all ships
	 */
	private List<Ship> ships;

	/**
	 * Construct a new game field.
	 * 
	 * @param size The size of the new game field to use.
	 */
	public GameField(Vec2 size) {
		//this initializes the matrix
		this.size = size;
		this.field = new FieldTile[size.getX()][size.getY()];
		
		for (int x=0; x<size.getX(); x++) {
			for (int y=0; y<size.getY(); y++) {
				field[x][y] = new FieldTile();
			}
		}
		this.ships = new LinkedList<>();
	}
	/**
	 * Use this method in order to get the fields size.
	 * 
	 * @return The size of the field.
	 */
	public Vec2 getSize() {
		return this.size;
	}

	/**
	 * Use this method in order to get the tile at the desired location.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return The located tile
	 */
	public FieldTile getTileAt(int x, int y) {
		if(this.size.getX() < x || this.size.getY() < y)
			throw new RuntimeException("Field tile out of bounds");
		return this.field[x][y];
	}

	/**
	 * Use this method in order to place ships on the game field.
	 * @param posX The x coordinate where the ship should begin
	 * @param posY The y coordinate where the ship should begin
	 * @param length The length of the ship to place.
	 * @param up True if the ship should be placed vertically; false otherwise
	 * @param shipToPlace The ship instance to place
	 */
	public void placeShip(int posX, int posY, int length, boolean up, Ship shipToPlace) {
		/*
		 * Nach korrekter Implementation sollten sich die Schiffe korrekt vertikal 
		 * und horizontal mit der richtigen Laenge platzieren lassen. Sie werden aber 
		 * noch aus falschen Tiles zusammengesetzt.
		 */
	}

	/**
	 * This method passes the lambda action to the java implementation of a distributed for each action.
	 * @param action The action to perform while iterating
	 */
	public void iterateOverShips(Consumer<Ship> action) {
		this.ships.forEach(action);
	}
	
	/**
	 * An array containing a momentary copy of the ship list.
	 * @return An Array of the current ships.
	 */
	public Ship[] getCopyOfShipListAsArray() {
		Ship[] arr = new Ship[this.ships.size()];
		return this.ships.toArray(arr);
	}

}
