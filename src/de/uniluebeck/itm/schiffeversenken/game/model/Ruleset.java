package de.uniluebeck.itm.schiffeversenken.game.model;

import de.uniluebeck.itm.schiffeversenken.engine.Vec2;

/**
 * This class contains the rules for a running game.
 * @author leondietrich, modified by B. Voss, F. Junghans
 *
 */
public final class Ruleset {
	
	/**
	 * The first rule set
	 */
	public static final Ruleset OFFICIAL_FIRST_VERSION_RULESET = new Ruleset("official_version_1", false, 2, 1, 1, 1, 0, new Vec2(15, 15), true);
	
	/**
	 * The second rule set
	 */
	public static final Ruleset OFFICIAL_SECOND_VERSION_RULESET = new Ruleset("official_version_2", false, 1, 1, 1, 0, 1, new Vec2(15, 15), false);

	/**
	 * The number of ships that are one tiles long
	 */
	private int numberOf1Ships = 0;
	
	/**
	 * The number of ships that are two tiles long
	 */
	private int numberOf2Ships = 0;
	
	/**
	 * The number of ships that are three tiles long
	 */
	private int numberOf3Ships = 0;
	
	/**
	 * The number of ships that are four tiles long
	 */
	private int numberOf4Ships = 0;
	
	/**
	 * The number of ships that are five tiles long
	 */
	private int numberOf5Ships = 0;
	
	/**
	 * The vector containing the dimensions of the game field
	 */
	private Vec2 gameFieldSize = new Vec2(1, 1);

	/**
	 * The variable determining whether ships need to social distance or not
	 */
	private boolean socialDistance = false;
	
	/**
	 * The name of the rule set
	 */
	private final String ruleSetName;
	
	/**
	 * The variable determining whether the rule set's values can be changed
	 * after initialization
	 */
	private final boolean mutable;
	
	/**
	 * Use this constructor in order to get a new rule set.
	 * @param name The name of the rule set
	 */
	public Ruleset(String name) {
		this.ruleSetName = name;
		this.mutable = true;
	}
	
	/**
	 * This private constructor enables the static block to create immutable rule sets.
	 * @param name The name of the new rule set
	 * @param mutable Should the rule set be mutable?
	 * @param s1Ships The number of ships with length 1
	 * @param s1Ships The number of ships with length 2
	 * @param s1Ships The number of ships with length 3
	 * @param s1Ships The number of ships with length 4
	 * @param s1Ships The number of ships with length 5
	 * @param fieldSize the size of the game field
	 */
	private Ruleset(String name, boolean mutable, int s1Ships, int s2Ships, int s3Ships, int s4Ships, int s5Ships, Vec2 fieldSize, boolean covid) {
		this.ruleSetName = name;
		this.mutable = mutable;
		this.numberOf1Ships = s1Ships;
		this.numberOf2Ships = s2Ships;
		this.numberOf3Ships = s3Ships;
		this.numberOf4Ships = s4Ships;
		this.numberOf5Ships = s5Ships;
		this.gameFieldSize  = fieldSize;
		this.socialDistance = covid;
	}

	/**
	 * Use this method in order to retrieve the number of ships that fit into a single cell.
	 * @return the number of small ships
	 */
	public final int getNumberOf1Ships() {
		return numberOf1Ships;
	}

	/**
	 * Use 
	 * @param numberOf1Ships the numberOf1Ships to set
	 */
	public final void setNumberOf1Ships(int numberOf1Ships) {
		if(!this.mutable) {
			throw new RuntimeException("This rule set is immutable");
		}
		this.numberOf1Ships = numberOf1Ships;
	}

	/**
	 * @return the numberOf2Ships
	 */
	public final int getNumberOf2Ships() {
		return numberOf2Ships;
	}

	/**
	 * @param numberOf2Ships the numberOf2Ships to set
	 */
	public final void setNumberOf2Ships(int numberOf2Ships) {
		if(!this.mutable) {
			throw new RuntimeException("This rule set is immutable");
		}
		this.numberOf2Ships = numberOf2Ships;
	}

	/**
	 * @return the numberOf3Ships
	 */
	public final int getNumberOf3Ships() {
		return numberOf3Ships;
	}

	/**
	 * @param numberOf3Ships the numberOf3Ships to set
	 */
	public final void setNumberOf3Ships(int numberOf3Ships) {
		if(!this.mutable) {
			throw new RuntimeException("This rule set is immutable");
		}
		this.numberOf3Ships = numberOf3Ships;
	}

	/**
	 * @return the numberOf4Ships
	 */
	public final int getNumberOf4Ships() {
		return numberOf4Ships;
	}

	/**
	 * @param numberOf4Ships the numberOf4Ships to set
	 */
	public final void setNumberOf4Ships(int numberOf4Ships) {
		if(!this.mutable) {
			throw new RuntimeException("This rule set is immutable");
		}
		this.numberOf4Ships = numberOf4Ships;
	}

	/**
	 * @return the numberOf5Ships
	 */
	public final int getNumberOf5Ships() {
		return numberOf5Ships;
	}

	/**
	 * @param numberOf5Ships the numberOf5Ships to set
	 */
	public final void setNumberOf5Ships(int numberOf5Ships) {
		if(!this.mutable) {
			throw new RuntimeException("This rule set is immutable");
		}
		this.numberOf5Ships = numberOf5Ships;
	}

	/**
	 * @return the gameFieldSize
	 */
	public final Vec2 getGameFieldSize() {
		return gameFieldSize;
	}

	/**
	 * @param gameFieldSize the game field size to set
	 */
	public final void setGameFieldSize(Vec2 gameFieldSize) {
		if(!this.mutable) {
			throw new RuntimeException("This rule set is immutable");
		}
		this.gameFieldSize = gameFieldSize;
	}

	/**
	 * Use this method in order to retrieve the rule set name.
	 * @return the rule set name
	 */
	public String getRulesetName() {
		return ruleSetName;
	}

	@Override
	public String toString() {
		return this.getRulesetName();
	}

	/**
	 * Use this method to get whether ships social distance or not
	 * @return the current regulations
	 */
	public boolean getSocialDistance(){
		return this.socialDistance;
	}
}
