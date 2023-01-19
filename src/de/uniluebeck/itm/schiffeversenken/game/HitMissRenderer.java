package de.uniluebeck.itm.schiffeversenken.game;
import de.uniluebeck.itm.schiffeversenken.game.model.GameField;
import de.uniluebeck.itm.schiffeversenken.engine.*;

/**
 * This class renders the opponents GameField
 * 
 * @author Bendix Voss, Fabio Junghans
 * 
 * Group 169
 */
public class HitMissRenderer extends GameFieldRenderer{

    /**
     * This is the renderer for the opponents field
     * 
     * @param field
     */
    public HitMissRenderer(GameField field) {
        super(field);
    }

     /**
     * This method gets called by the rendering method in order to look up the correct tile at a given position.
     * @param x The x coordinate of the tile to look up
     * @param y The y coordinate of the tile to look up
     * @param waterTile A cached version for a water tile (will be the most returned one)
     * @param waterHitTile A cached version of the water_missed tile (Will be the second most returned one)
     * @return The correct tile to render
     */
    @Override
    protected Tile getTileAt(int x, int y, Tile waterTile, Tile waterHitTile) {
        switch(this.getField().getTileAt(x, y).getTilestate()) {
            default:
                return AssetRegistry.getTile("unknown");
            case STATE_SHIP:
            case STATE_WATER:
                return waterTile;
            case STATE_MISSED:
                return waterHitTile;
            case STATE_SHIP_HIT:
                return lookupShipTile(x, y, true);
        }
    }    

    /**
     * This method looks up the correct tile if the position happens to be a ship.
     * @param x The x coordinate of the ships tile
     * @param y The y coordinate of the ships tile
     * @param alreadyHit A Flag that indicates whether nor not the tile has been hit yet.
     * @return The composed ships tile
     */
    private Tile lookupShipTile(int x, int y, boolean alreadyHit) {

        return AssetRegistry.getTile("water.hiddenshiphit");
    }
}
