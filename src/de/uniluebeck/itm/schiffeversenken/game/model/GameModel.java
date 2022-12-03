package de.uniluebeck.itm.schiffeversenken.game.model;

import de.uniluebeck.itm.schiffeversenken.engine.Vec2;
import de.uniluebeck.itm.schiffeversenken.game.ai.AIAgent;

/**
 * This class represents the game's model
 * 
 * @author leondietrich
 * 
 */
public class GameModel{
    
	/**
	 * The game field of the human player
	 */
	private final GameField humanPlayerField;
    
	/**
	 * The game field of the enemy AI player
	 */
	private final GameField computerPlayerField;
    
	/**
	 * The rule set for the game
	 */
	private final Ruleset rules;
    
	/**
	 * The AIAgent determining the difficulty of the enemy player
	 */
	private final AIAgent agent;

	/**
     * The name of the human player
     */
	private final String playerName;
    
	/**
	 * The name of the enemy player, the AI
	 */
	private final String computerName;

	/**
     * The vector containing the coordinates of the opponent's game field
     */
	private Vec2 opponentsFieldPosition;
	
	/**
	 * The vector containing the height and width of the opponent's game field
	 */
	private Vec2 opponentsFieldDimensions;
    
	/**
	 * The number of rounds
	 */
	private int roundCounter;
    
	/**
	 * The variable determining whether the round 
	 */
	private boolean changingRound;

	/**
     * The number of points the player has during the game
     */
	private int playerPoints;
    
	/**
     * The number of points the AI has during the game
     */
	private int aiPoints;

	/**
     * Construct a new game model
     * @param humanPlayerField The game field of the human player
     * @param computerPlayerField The game field of the enemy AI player
     * @param rules The rule set for the game
     * @param agent The AIAgent determining the difficulty of the enemy player
     * @param playerName The name of the human player
     * @param computerName The name of the enemy AI player
     */
	public GameModel(GameField humanPlayerField, GameField computerPlayerField, Ruleset rules, AIAgent agent, String playerName, String computerName) {
        this.humanPlayerField = humanPlayerField;
        this.computerPlayerField = computerPlayerField;
        this.rules = rules;
        this.agent = agent;
        this.playerName = playerName;
        this.computerName = computerName;
        this.roundCounter = 0;
        this.changingRound = false;
        this.opponentsFieldPosition = new Vec2(0, 0);
        this.opponentsFieldDimensions = new Vec2(-1, -1);
        this.playerPoints = 0;
        this.aiPoints = 0;
    }

	/**
     * Use this method in order to retrieve the game field of the human player
     * @return humanPlayerField
     */
	public GameField getHumanPlayerField() {
        return humanPlayerField;
    }

	/**
     * Use this method in order to retrieve the game field of the enemy AI player
     * @return computerPlayerField
     */
	public GameField getComputerPlayerField() {
        return computerPlayerField;
    }

	/**
     * Use this method in order to retrieve the rule set
     * @return rules
     */
	public Ruleset getRules() {
        return rules;
    }

	/**
     * Use this method in order to retrieve the AIAgent
     * @return agent
     */
	public AIAgent getAgent() {
        return agent;
    }

	/**
     * Use this method in order to retrieve the name of the human player
     * @return playerName
     */
	public String getPlayerName() {
        return playerName;
    }

    /**
     * Use this method in order to retrieve the name of the enemy AI player
     * @return computerName
     */
	public String getComputerName() {
        return computerName;
    }

    /**
     * This method increases the round counter by 1.
     */
    public void increaseRoundCounter() {
        this.roundCounter++;
    }

    /**
     * Use this getter in order to get the current round counter value.
     * @return The current round number.
     */
    public int getRoundCounter() {
        return this.roundCounter;
    }

    /**
     * Use this method in order to change the round changing flag status.
     * @param newValue true if the round is currently changing or otherwise false.
     */
    public void setRoundChangingFlag(boolean newValue) {
        this.changingRound = newValue;
    }

    /**
     * Use this method in order to check if the round is currently changing.
     * @return True if it is or otherwise false.
     */
    public boolean isRoundChanging() {
        return this.changingRound;
    }

    /**
     * Use this method in order to update the opponents game field visualization data.
     * @param position The new position to set
     * @param dimensions The new dimensions to set
     */
    public void updateOpponentsFieldOnScreenData(Vec2 position, Vec2 dimensions) {
        this.opponentsFieldDimensions = dimensions;
        this.opponentsFieldPosition = position;
    }

    /**
     * This method is a getter for the opponents game field position.
     * @return The rendered position on screen.
     */
    public Vec2 getOpponentsFieldPosition() {
        return this.opponentsFieldPosition;
    }

    /**
     * This method is a getter for the opponents game field dimensions.
     * @return The actual dimensions of the field on screen.
     */
    public Vec2 getOpponentsFieldDimensions() {
        return this.opponentsFieldDimensions;
    }

    /**
     * Use this method in order to get the points of the human player.
     * @return The amount of points the player has.
     */
    public int getPlayerPoints() {
        return this.playerPoints;
    }

    /**
     * Use this method in order to increase the amount of points the human player has.
     * @param points The points to add
     */
    public void addPlayerPoints(int points) {
        this.playerPoints += points;
    }

    /**
     * Use this method in order to get the points of the AI.
     * @return The amount of points the AI has.
     */
    public int getAiPoints() {
        return this.aiPoints;
    }

    /**
     * Use this method in order to increase the amount of points the AI player has.
     * @param points The points to add
     */
    public void addAiPoints(int points) {
        this.aiPoints += points;
    }
}
