package de.uniluebeck.itm.schiffeversenken.game.ai;

import de.uniluebeck.itm.schiffeversenken.game.model.FieldTile;
import de.uniluebeck.itm.schiffeversenken.game.model.GameField;
import de.uniluebeck.itm.schiffeversenken.game.model.Ruleset;
import de.uniluebeck.itm.schiffeversenken.game.model.Ship;
import de.uniluebeck.itm.schiffeversenken.game.model.GameModel;

import java.util.Random;

/**
 * This class provides the blueprint and basic behavior for every AI agent
 * 
 * @author leondietrich modified by B. Voss, F. Junghans
 *
 */
public abstract class AIAgent {

    /**
     * The difficulty of the AI
     */
	private final int hardness;

    /**
     * Construct a new AI agent
     * 
     * @param hardness The difficulty of the AI
     */
	public AIAgent(int hardness) {
        this.hardness = hardness;
    }

    /**
     * Use this method in order to initialize the agent. It needs to place his ships now.
     * @param r The rule set of the game.
     * @param agentsField The field belonging to the agent
     */
    public abstract void setup(Ruleset r, GameField agentsField);

    /**
     * Use this method in order to let the agent perform its move.
     * @param playersField The game field of the human player.
     * @return true if the agent hit something or otherwise false.
     */
    public abstract boolean performMove(GameField playersField);

    /**
     * Call this method from within a setup method in order to conveniently place your ships.
     * @param r The rule set to obey
     * @param f The field to place the ships on
     */
    protected void placeShipsAccordingToRules (Ruleset r, GameField f) {

        final int width = r.getGameFieldSize().getX();
        final int height = r.getGameFieldSize().getY();

        final int[] shipsToBePlaced = new int[] {
                r.getNumberOf1Ships(),
                r.getNumberOf2Ships(),
                r.getNumberOf3Ships(),
                r.getNumberOf4Ships(),
                r.getNumberOf5Ships()};

        final Random rnd = new Random(System.currentTimeMillis());

        for (int shipsLengthIndex = 0; shipsLengthIndex < shipsToBePlaced.length; shipsLengthIndex++) {
            for (int ship = 0; ship < shipsToBePlaced[shipsLengthIndex]; ship++) {
                while (!checkAndPlace(f, rnd.nextBoolean(), rnd.nextInt(width),
                        rnd.nextInt(height), shipsLengthIndex + 1, width, height, r.getSocialDistance()));
            }
        }
    }

    /**
     * Use this method to correctly place a ship
     * @param f the game field of the AI
     * @param up the orientation of the ship
     * @param x the x coordinate of the ship
     * @param y the y coordinate of the ship
     * @param length the length of the ship
     * @param width the width of the AI player field
     * @param height the height of the AI player field
     * @param covid the information whether social distancing is needed
     * @return Has the ship successfully been placed?
     */
    private boolean checkAndPlace(GameField f, boolean up, int x, int y, int length, int width, int height, boolean covid) {
        if ((up && y + length > height) || (!up && x + length > width)) {
            return false;
        }

        if (covid){
                //checks for orientation of the ship, goes into this path if the ship is vertical
            if (up) {
                //nested for loop to parse over the 3xShipLength field from top left to bottom right
                for (int j = -1; j < 2; j++){
                    for (int currentShipsX = x + j, currentShipsY = y - 1, i = 0; i < length + 2; i++){
                        //sanity check for out of bounds exceptions if the ship is placed close to the field borders
                        if (((currentShipsX < 15)&&(currentShipsX >= 0)) 
                        && ((currentShipsY < 15)&&(currentShipsY >= 0))) {
                            //gets the tile at the current position and checks for other ships
                            final FieldTile t = f.getTileAt(currentShipsX, currentShipsY);
                            if (t.getTilestate() != FieldTile.FieldTileState.STATE_WATER || t.getCorrespondingShip() != null) {
                                return false;
                            }
                            currentShipsY++;
                        }
                        else {
                            currentShipsY++;
                            }		
                    }
                }	
            }
            //goes into this path if the ship is horizontal
            else {
                //nested for loop to parse over the 3xShipLength field from top left to bottom right
                for (int j = -1; j < 2; j++){
                    for (int currentShipsX = x - 1, currentShipsY = y + j, i = 0; i < length + 2; i++){
                        //sanity check for out of bounds exceptions if the ship is placed close to the field borders
                        if (((currentShipsX < f.getSize().getX())&&(currentShipsX >= 0)) 
                        && ((currentShipsY < f.getSize().getY())&&(currentShipsY >= 0))) {
                            //gets the tile at the current position and checks for other ships
                            final FieldTile t = f.getTileAt(currentShipsX, currentShipsY);
                            if (t.getTilestate() != FieldTile.FieldTileState.STATE_WATER || t.getCorrespondingShip() != null) {
                                return false;
                            }
                            currentShipsX++;
                        }
                        else {
                            currentShipsX++;
                            }		
                    }
                }	 
            }
        }
    else{       
        for(int currentShipsX = x, currentShipsY = y, i = 0; i < length; i++) {
            final FieldTile t = f.getTileAt(currentShipsX, currentShipsY);
            if (t.getTilestate() != FieldTile.FieldTileState.STATE_WATER || t.getCorrespondingShip() != null) {
                return false;
            }
            if (up) {
                currentShipsY++;
            } else {
                currentShipsX++;
            }
        }
    }
        final Ship shipToPlace = new Ship(length, up);
        f.placeShip(x, y, length, up, shipToPlace);
        return true;
    }

    /**
     * This method must provide the tile that was previously attacked by the AI agent.
     * @return The last tile under fire.
     */
    public abstract FieldTile getLastAttackedTile();

    /**
     * This method is a getter for the hardness level of the AI
     * @return The current hardness level
     */
    public int getHardness() {
        return this.hardness;
    }
}
