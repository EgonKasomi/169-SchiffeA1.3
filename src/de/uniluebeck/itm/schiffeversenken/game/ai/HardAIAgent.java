package de.uniluebeck.itm.schiffeversenken.game.ai;

import de.uniluebeck.itm.schiffeversenken.game.model.FieldTile;
import de.uniluebeck.itm.schiffeversenken.game.model.GameField;
import de.uniluebeck.itm.schiffeversenken.game.model.Ruleset;
import de.uniluebeck.itm.schiffeversenken.game.model.FieldTile.FieldTileState;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents a hard AIAgent that is at least a little better than
 * just hitting random tiles
 * 
 * @author Bendix Voss, Fabio Junghans
 */
public class HardAIAgent extends AIAgent {

    /**
     * The last tile attacked by the AI
     */
    private FieldTile lastTile;

    /**
     * The right tracker tile
     */
    private FieldTile anchorTileRight;

    /**
     * The left tracker tile
     */
    private FieldTile anchorTileLeft;

    /**
     * The up tracker tile
     */
    private FieldTile anchorTileUp;

    /**
     * The down tracker tile
     */
    private FieldTile anchorTileDown;

    /**
     * The ship tracking tile
     */
    private FieldTile mainShipTile;

    /**
     * the x coordinate of the tile to bombard or of the tile that has recently been bombarded
     */
    final AtomicInteger x = new AtomicInteger(0);

    /**
     * the y coordinate of the tile to bombard or of the tile that has recently been bombarded
     */
    final AtomicInteger y = new AtomicInteger(0);

    /**
     * the threadsafe variable for tracking cases in the ship destruction 
     */
    final AtomicInteger z = new AtomicInteger(0);

    /**
     * the variable to make sure the first move protocol only gets called once
     */
    int a = 0;

    /**
     * Constructor for new hard AIAgent
     * 
     * @param hardness
     */
    public HardAIAgent(int hardness) {
        super(hardness);

    }

    @Override
    public void setup(Ruleset r, GameField agentsField) {
        this.placeShipsAccordingToRules(r, agentsField);
    }

    @Override
    public boolean performMove(GameField playersField) {
        //System.out.println("der harte");

        final Random rnd = new Random(System.currentTimeMillis());
        final int fieldWidth = playersField.getSize().getX();
        final int fieldHeight = playersField.getSize().getY();
        FieldTile tile;
        FieldTile firstTile = playersField.getTileAt(1, 1);
        // starts the very first turn
        if (a == 0) {
            //System.out.println("leider bin ich hier");
            //search for a random tile that is not occupied by a ship to set our "reset tile" to
            do{
            x.set(rnd.nextInt(fieldWidth));
            y.set(rnd.nextInt(fieldHeight));
            firstTile = playersField.getTileAt(x.get(), y.get());
            } while(firstTile.getTilestate() != FieldTileState.STATE_SHIP);
            //set the tracker tiles to the safe tile to avoid crashes
            this.mainShipTile = firstTile;
            this.anchorTileRight = firstTile;
            this.anchorTileLeft = firstTile;
            this.anchorTileUp = firstTile;
            this.anchorTileDown = firstTile;
            //set a = 1 to ensure this first move protocol only gets called once
            this.a = 1;
            lastTile = playersField.getTileAt(x.get(), y.get());
            //System.out.println(x.get() + " X standart sucher");
            //System.out.println(y.get() + " Y Standart sucher");
            return bombardIfFree(lastTile, playersField);
        }
        // sets up the ship tracking if the last hit was on a ship
        if (this.lastTile.getCorrespondingShip() != null) {
            this.mainShipTile = this.lastTile;
            System.out.println("tracker");
            // checks wether the last hit on a tile has sunken the ship
            if (this.lastTile.getCorrespondingShip().isSunken()) {
                //if true it sets all tracking tiles back to the safe tile
                this.mainShipTile = firstTile;
                this.anchorTileRight = firstTile;
                this.anchorTileLeft = firstTile;
                this.anchorTileUp = firstTile;
                this.anchorTileDown = firstTile;
                z.set(0);
            }
        }

        //when the ai hits a ship it goes into this case protocol and tracks its hits on that ship
        //with those hits it deduces the location of the other ship parts after testing around the first hit
        if (this.mainShipTile.getTilestate() == FieldTile.FieldTileState.STATE_SHIP_HIT) {
            System.out.println("caseabfrage");
            switch (z.get()) {
                //it checks right, left, up, down in that order
                case 0:
                    tile = playersField.getTileAt(x.get() + 1, y.get());
                    this.lastTile = tile;
                    z.getAndAdd(1);
                    return bombardIfFree(tile, playersField);
                case 1:
                    if (this.lastTile.getTilestate() == FieldTile.FieldTileState.STATE_SHIP_HIT) {
                        this.anchorTileRight = this.lastTile;
                    }
                    tile = playersField.getTileAt(x.get() - 1, y.get());
                    this.lastTile = tile;
                    z.getAndAdd(1);
                    return bombardIfFree(tile, playersField);
                case 2:
                    if (this.lastTile.getTilestate() == FieldTile.FieldTileState.STATE_SHIP_HIT) {
                        this.anchorTileLeft = this.lastTile;
                    }
                    tile = playersField.getTileAt(x.get(), y.get() - 1);
                    this.lastTile = tile;
                    z.getAndAdd(1);
                    return bombardIfFree(tile, playersField);
                case 3:
                    if (this.lastTile.getTilestate() == FieldTile.FieldTileState.STATE_SHIP_HIT) {
                        this.anchorTileUp = this.lastTile;
                    }
                    tile = playersField.getTileAt(x.get(), y.get() + 1);
                    this.lastTile = tile;
                    z.getAndAdd(1);
                    return bombardIfFree(tile, playersField);
                //now that it has checked around the first hit it can deduce the location of the other parts with some certainty    
                case 4:
                    if (this.lastTile.getTilestate() == FieldTile.FieldTileState.STATE_SHIP_HIT) {
                        this.anchorTileDown = this.lastTile;
                    }
                    //here goes to destroy a horizontal ship
                    if (anchorTileRight.getTilestate() == FieldTile.FieldTileState.STATE_SHIP_HIT) {
                        if (anchorTileLeft.getTilestate() == FieldTile.FieldTileState.STATE_SHIP_HIT) {
                            tile = playersField.getTileAt(x.get() + 2, y.get());
                            this.lastTile = tile;
                            z.getAndAdd(1);
                            return bombardIfFree(tile, playersField);
                        }
                        tile = playersField.getTileAt(x.get() + 2, y.get());
                        this.lastTile = tile;
                        z.getAndAdd(2);
                        return bombardIfFree(tile, playersField);
                    }
                    if (anchorTileLeft.getTilestate() == FieldTile.FieldTileState.STATE_SHIP_HIT) {
                        tile = playersField.getTileAt(x.get() - 2, y.get());
                        this.lastTile = tile;
                        z.getAndAdd(3);
                        return bombardIfFree(tile, playersField);
                    }
                    //here it goes to destroy a vertical ship
                    if (anchorTileUp.getTilestate() == FieldTile.FieldTileState.STATE_SHIP_HIT) {
                        if (anchorTileDown.getTilestate() == FieldTile.FieldTileState.STATE_SHIP_HIT) {
                            tile = playersField.getTileAt(x.get(), y.get() - 2);
                            this.lastTile = tile;
                            z.getAndAdd(4);
                            return bombardIfFree(tile, playersField);
                        }
                        tile = playersField.getTileAt(x.get(), y.get() - 2);
                        this.lastTile = tile;
                        z.getAndAdd(5);
                        return bombardIfFree(tile, playersField);
                    }
                    if (anchorTileLeft.getTilestate() == FieldTile.FieldTileState.STATE_SHIP_HIT) {
                        tile = playersField.getTileAt(x.get(), y.get() + 2);
                        this.lastTile = tile;
                        z.getAndAdd(6);
                        return bombardIfFree(tile, playersField);
                    }
                    //here it finds the last parts of any ship
                case 5:
                    tile = playersField.getTileAt(x.get() - 2, y.get());
                    this.lastTile = tile;
                    z.getAndAdd(6);
                    return bombardIfFree(tile, playersField);
                case 6:
                    tile = playersField.getTileAt(x.get() + 3, y.get());
                    this.lastTile = tile;
                    z.getAndAdd(5);
                    return bombardIfFree(tile, playersField);
                case 7:
                    tile = playersField.getTileAt(x.get() - 3, y.get());
                    this.lastTile = tile;
                    z.getAndAdd(4);
                    return bombardIfFree(tile, playersField);
                case 8:
                    tile = playersField.getTileAt(x.get(), y.get() + 2);
                    this.lastTile = tile;
                    z.getAndAdd(3);
                    return bombardIfFree(tile, playersField);
                case 9:
                    tile = playersField.getTileAt(x.get(), y.get() - 3);
                    this.lastTile = tile;
                    z.getAndAdd(2);
                    return bombardIfFree(tile, playersField);
                case 10:
                    tile = playersField.getTileAt(x.get(), y.get() - 3);
                    this.lastTile = tile;
                    z.getAndAdd(1);
                    return bombardIfFree(tile, playersField);
                //this is the catcher for cases where its trying to bombard a already
                //bombarded tile while locked in a specific case to avoid a stackoverflow  
                case 11:
                    x.set(rnd.nextInt(fieldWidth));
                    y.set(rnd.nextInt(fieldHeight));  
                    tile = playersField.getTileAt(x.get(), y.get());   
                    return bombardIfFree(tile, playersField); 
            }
        }

        // routine to get into when there is no ship to sink currently
        if ((this.mainShipTile.getTilestate() == FieldTile.FieldTileState.STATE_WATER
            || this.mainShipTile.getTilestate() == FieldTile.FieldTileState.STATE_MISSED)) {
            //this is just the standard random tile bombardment
            //System.out.println("standart");
            x.set(rnd.nextInt(fieldWidth));
            y.set(rnd.nextInt(fieldHeight));
            tile = playersField.getTileAt(x.get(), y.get());
            this.lastTile = tile;
            this.mainShipTile = tile;
            //System.out.println(x.get() + " X standart sucher");
            //System.out.println(y.get() + " Y Standart sucher");
            return bombardIfFree(tile, playersField);
        }
        //if all else fails just return false
        return false;
    }
    /**
     * Use this method in order to only bombard tiles that have not yet been bombarded
     * 
     * @param tileToBombard
     * @param field
     * @return true when ai hit a ship with the bombardment 
     */
    private boolean bombardIfFree(FieldTile tileToBombard, GameField field) {
        //reruns the performMove method to find a different tile to bombard
        if (tileToBombard.wasAlreadyBombarded()) {
            return performMove(field);
        }
        return tileToBombard.bombard();
    }

    @Override
    public FieldTile getLastAttackedTile() {
        return this.lastTile;
    }

}
