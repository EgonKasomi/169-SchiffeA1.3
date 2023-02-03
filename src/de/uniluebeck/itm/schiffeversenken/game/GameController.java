package de.uniluebeck.itm.schiffeversenken.game;

import de.uniluebeck.itm.schiffeversenken.engine.Application;
import de.uniluebeck.itm.schiffeversenken.engine.Controller;
import de.uniluebeck.itm.schiffeversenken.engine.Vec2;
import de.uniluebeck.itm.schiffeversenken.game.menues.EndOfGameMenu;
import de.uniluebeck.itm.schiffeversenken.game.model.FieldTile;
import de.uniluebeck.itm.schiffeversenken.game.model.GameModel;
import de.uniluebeck.itm.schiffeversenken.game.model.Ship;
import de.uniluebeck.itm.schiffeversenken.game.model.GameField;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class handles user input during the game
 * @author leondietrich modified by B. Voss, F. Junghans
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
            final int tileX = (positionOnOpponentsField.getX())/res;
            final int tileY = (positionOnOpponentsField.getY())/res;
            final FieldTile fieldTile = model.getComputerPlayerField().getTileAt(tileX, tileY);
        //now we check if the tile has already been hit    
            if (!fieldTile.wasAlreadyBombarded()) {
                startTheBombardment(fieldTile, model);
            }
        }
    }

    /**
     * Use this method to start the bombardment process, beginning with the player and then changing to the ai
     * 
     * @param fieldTile the tile to bombard
     * @param model the game model
     */
    public void startTheBombardment(FieldTile fieldTile, GameModel model){
        //first check if the player hit a ship
        if(fieldTile.bombard()) {
            model.addPlayerPoints(Constants.POINTS_FOR_HIT);
            final Ship s = fieldTile.getCorrespondingShip();
        //now check if said ship has been sunken and wether the game is over
            if (s.isSunken()){
                model.addPlayerPoints(Constants.POINTS_FOR_SHIP_SUNK);
                handlePossibleGameEnd();
            }
        }
        //if the player did'nt hit a ship change to the ai's turn
        else {
            model.setRoundChangingFlag(true);
            aiBombardment(fieldTile, model);
        }
    }

    /**
     * Use this method to activate the ai bombarding protocol which bombards the players field
     * 
     * @param fieldTile the tile the ai is supposed to bombard
     * @param model the game model
     */
    public void aiBombardment(FieldTile fieldTile, GameModel model){
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
     * @param field the GameField on which to check 
     * 
     * @return true if all the ships of either player are sunken
     */
    private boolean areShipsSunken(GameField field){
        final AtomicInteger i = new AtomicInteger(0);
        //iterate over the ShipList and count up i for each sunken ship
        field.iterateOverShips( 
            (Ship) -> {if (Ship.isSunken())
                {i.getAndAdd(1);}
            });
        //compare the counters value with the total amount of ships in the list
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
