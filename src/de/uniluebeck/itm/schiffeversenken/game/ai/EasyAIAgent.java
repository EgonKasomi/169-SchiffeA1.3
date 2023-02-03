package de.uniluebeck.itm.schiffeversenken.game.ai;

import de.uniluebeck.itm.schiffeversenken.game.model.FieldTile;
import de.uniluebeck.itm.schiffeversenken.game.model.GameField;
import de.uniluebeck.itm.schiffeversenken.game.model.Ruleset;

import java.util.Random;

/**
 * The class represents  easy AI that randomly bombards the player field until it hits a tile that
 * hasn't been bombarded yet
 * 
 * @author leondietrich
 *
 */
public class EasyAIAgent extends AIAgent {

    /**
     * The last field tile bombarded by the AI
     */
	private FieldTile lastTile;

    /**
     * Construct a new easy AI agent
     * 
     * @param hardness The difficulty of the AI
     */
	public EasyAIAgent(int hardness) {
        super(hardness);
    }

    @Override
    public void setup(Ruleset r, GameField agentsField) {
        this.placeShipsAccordingToRules(r, agentsField);
    }

    @Override
    public boolean performMove(GameField playersField) {
        final Random rnd = new Random(System.currentTimeMillis());
        final int fieldWidth = playersField.getSize().getX();
        final int fieldHeight = playersField.getSize().getY();
        FieldTile tile;
        do {
            tile = playersField.getTileAt(rnd.nextInt(fieldWidth), rnd.nextInt(fieldHeight));
        } while (tile.wasAlreadyBombarded());
        this.lastTile = tile;
        return tile.bombard();
    }

    @Override
    public FieldTile getLastAttackedTile() {
        return this.lastTile;
    }   
}
