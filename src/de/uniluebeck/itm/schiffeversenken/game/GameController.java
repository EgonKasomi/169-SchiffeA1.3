package de.uniluebeck.itm.schiffeversenken.game;

import de.uniluebeck.itm.schiffeversenken.engine.Application;
import de.uniluebeck.itm.schiffeversenken.engine.Controller;
import de.uniluebeck.itm.schiffeversenken.engine.Vec2;
import de.uniluebeck.itm.schiffeversenken.game.menues.EndOfGameMenu;
import de.uniluebeck.itm.schiffeversenken.game.model.FieldTile;
import de.uniluebeck.itm.schiffeversenken.game.model.GameModel;
import de.uniluebeck.itm.schiffeversenken.game.model.Ship;
import de.uniluebeck.itm.schiffeversenken.game.model.GameField;

import java.io.ObjectInputStream.GetField;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class handles user input during the game
 * @author leondietrich modified by B. Voss, F. Junnghans
 * 
 *
 */
public class GameController extends Controller<GameModel> {

	/**
     * the constructor for the game controller
     * @param m the game model
     */
	public GameController(GameModel m) {
        super(m);
    }

    @Override
    public void clickedAt(Vec2 mousePosition) {
        final GameModel model = this.getModelInstance();
        final Vec2 positionOnOpponentsField = mousePosition.add(model.getOpponentsFieldPosition().multiply(-1));
        final Vec2 opponentsFieldDimensions = model.getOpponentsFieldDimensions();

        // First of all we need to test if the player clicked within the field
        if(positionOnOpponentsField.getX() >= 0 && positionOnOpponentsField.getY() >= 0
                && positionOnOpponentsField.getX() < opponentsFieldDimensions.getX()
                && positionOnOpponentsField.getY() < opponentsFieldDimensions.getY()) {
            final int res = Constants.TILE_SIZE;
        //Then we calculate where exactly the player clicked 
            final int tileX = positionOnOpponentsField.getX()/res;
            final int tileY = positionOnOpponentsField.getY()/res;
            Application.log("Bombarding position " + tileX + ", " + tileY);
            final FieldTile fieldTile = model.getComputerPlayerField().getTileAt(tileX, tileY);
            if (!fieldTile.wasAlreadyBombarded()) {
                if(fieldTile.bombard()) {
                	model.addPlayerPoints(Constants.POINTS_FOR_HIT);
                	final Ship s = fieldTile.getCorrespondingShip();
                    if (s.isSunken()){
                        model.addPlayerPoints(Constants.POINTS_FOR_SHIP_SUNK);
                        handlePossibleGameEnd();
                    }
                } else {
                    //Change turns to the ai
                    model.setRoundChangingFlag(true);
                    this.dispatchWork(new Runnable() {

                        @Override
                        public void run() {
                            while (model.getAgent().performMove(model.getHumanPlayerField())) {
                                rewardAgentForDestroyingPlayer();
                            }
                        }

                        private void rewardAgentForDestroyingPlayer() {
                            model.addAiPoints(Constants.POINTS_FOR_HIT);
                            final Ship s = model.getAgent().getLastAttackedTile().getCorrespondingShip();
                            if(s != null && s.isSunken()) {
                            	model.addAiPoints(Constants.POINTS_FOR_SHIP_SUNK);
                                handlePossibleGameEnd();
                            }
                        }

                    });
                    this.startWorkStack();
                }
            }
        }
    }

    /**
     * This method checks whether the game is over
     */
    private void handlePossibleGameEnd() {
        if (areShipsSunken(this.getModelInstance().getComputerPlayerField())){
            endGame(true);
        }
        else if (areShipsSunken(this.getModelInstance().getHumanPlayerField())){
            endGame(false);
        }
    }

    /**
     * This method checks if all ships of either player are sunken
     * 
     * @return true if all the ships of either player are sunken
     */
    private boolean areShipsSunken(GameField field){
        final AtomicInteger i = new AtomicInteger(0);
        field.iterateOverShips( 
            (Ship) -> {if (Ship.isSunken())
                {i.getAndAdd(1);}
            });
        if (field.getCopyOfShipListAsArray().length == i.get()){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Use this function in order to end the game and display the EndOfGameMenu.
     * @param playerWon Pass true if the player won and false if the computer won.
     */
    private void endGame(boolean playerWon) {
        Application.log("Ending game.");
        Application.switchToScene(new EndOfGameMenu(this.getModelInstance(), playerWon).getScene());
    }

    @Override
    public void keyPressed(int key, boolean shift, boolean alt, boolean ctrl, boolean down, boolean up, boolean left, boolean right) {
        // Doesn't do anything anymore since we've dropped multiple resolutions
    }

    @Override
    public void performFrequentUpdates() {
        if (!this.hasWork() && this.getModelInstance().isRoundChanging()) {
            // We need to assume that the round changing work has finished.
            this.getModelInstance().increaseRoundCounter();
            this.getModelInstance().setRoundChangingFlag(false);
        }
    }

    @Override
    public void prepare() {

    }
}
