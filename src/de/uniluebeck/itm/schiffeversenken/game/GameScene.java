package de.uniluebeck.itm.schiffeversenken.game;

import de.uniluebeck.itm.schiffeversenken.engine.SmartScene;
import de.uniluebeck.itm.schiffeversenken.game.model.GameModel;

/**
 * This class's only purpose is to create a scene with new GameView and GameController objects
 * 
 * @author leondietrich
 *
 */
public class GameScene extends SmartScene<GameModel> {

    /**
     * The game model
     */
	private final GameModel model;

    /**
     * Construct a new scene for the game
     * @param m the game model
     */
	public GameScene(GameModel m) {
        super(new GameView(m), new GameController(m));
        this.model = m;
    }
}
