package de.uniluebeck.itm.schiffeversenken.game.menues;

import de.uniluebeck.itm.schiffeversenken.engine.*;

/**
 * This scene displays a loading screen while doing shady loading business.
 * After being done doing so it switches to the main menu scene.
 * 
 * @author leondietrich modified by B. Voss, F. Junghans
 *
 */
public class LoadingScene extends Scene {

	/**
	 * The controller used to control and execute all the shady loading business
	 */
	private final Controller<Object> c;

	/**
	 * Construct a new loading scene
	 */
	public LoadingScene() {
		super();
		this.c = new Controller<Object>(null){
			@Override
			public void clickedAt(Vec2 position) {}

			@Override
			public void keyPressed(int key, boolean shift, boolean alt, boolean ctrl, boolean down, boolean up, boolean left, boolean right) {}

			@Override
			public void performFrequentUpdates() {}

			@Override
			public void prepare() {}
		};
	}

	@Override
	public void draw(Canvas c, Vec2 mouseLocation) {
		c.setColor(0.9, 0.9, 0.9);
		c.drawString(15, 55, "Loading Data...");
	}

	@Override
	public void attach() {
		final String[][] tiles = new String[][]{
				{"water.hiddenshiphit", "assets/32x32/Tile_hidden_hit_32x32_uint8_rgba.png"},
				{"water", "assets/32x32/Tile_Water_32x32_uint8_rgba.png"},
				{"water.hit", "assets/32x32/Tile_Water_hit_32x32_uint8_rgba.png"},
				{"up.ship.bug", "assets/32x32/Tile_Bug_up_32x32_uint8_rgba.png"},
				{"up.ship.middle", "assets/32x32/Tile_Middle_up_32x32_uint8_rgba.png"},
				{"up.ship.aft", "assets/32x32/Tile_aft_up_32x32_uint8_rgba.png"},
				{"up.ship.bug.hit", "assets/32x32/Tile_Bug_up_hit_32x32_uint8_rgba.png"},
				{"up.ship.middle.hit", "assets/32x32/Tile_Middle_up_hit_32x32_uint8_rgba.png"},
				{"up.ship.aft.hit", "assets/32x32/Tile_aft_up_hit_32x32_uint8_rgba.png"},
				{"up.ship.single", "assets/32x32/Tile_singleship_up_32x32_uint8_rgba.png"},
				{"up.ship.single.hit", "assets/32x32/Tile_singleship_up_hit_32x32_uint8_rgba.png"},
				{"right.ship.bug", "assets/32x32/Tile_Bug_right_32x32_uint8_rgba.png"},
				{"right.ship.middle", "assets/32x32/Tile_Middle_right_32x32_uint8_rgba.png"},
				{"right.ship.aft", "assets/32x32/Tile_aft_right_32x32_uint8_rgba.png"},
				{"right.ship.bug.hit", "assets/32x32/Tile_Bug_right_hit_32x32_uint8_rgba.png"},
				{"right.ship.middle.hit", "assets/32x32/Tile_Middle_right_hit_32x32_uint8_rgba.png"},
				{"right.ship.aft.hit", "assets/32x32/Tile_aft_right_hit_32x32_uint8_rgba.png"},
				{"right.ship.single", "assets/32x32/Tile_singleship_right_32x32_uint8_rgba.png"},
				{"right.ship.single.hit", "assets/32x32/Tile_singleship_right_hit_32x32_uint8_rgba.png"},
				{"arrow.down", "assets/32x32/Tile_arrow_down_32x32_uint8_rgba.png"}};
	
	//loads the right assets to the right tile
	for (int y=0; y<tiles.length; y++){	
		final int x=y;
		this.c.dispatchWork(new Runnable() {
			@Override
			public void run() { 
					AssetRegistry.registerTile(tiles[x][0], Application.loadTile(tiles[x][1]));	
			}
		 });}

	//loads the seven segment assets
	for (int x=0; x<10; x++){
		String a = String.valueOf(x);
		this.c.dispatchWork(new Runnable() {
			@Override
			public void run() {
				AssetRegistry.registerTile("7seg." + a, Application.loadTile("assets/7seg/" + a + "_small.png"));
			}
		});}

		this.c.startWorkStack();
	}

	@Override
	public void update(long milis) {
		if (!this.c.hasWork()) {
			MainMenu m = new MainMenu();
			Application.switchToScene(m.getScene());
		}
	}

	@Override
	public void detach() {}

	@Override
	public void clickedAt(Vec2 position) {}

	@Override
	public void keyPressed(char key, boolean shift, boolean alt, boolean ctrl, boolean down, boolean up, boolean left, boolean right) {}

}
