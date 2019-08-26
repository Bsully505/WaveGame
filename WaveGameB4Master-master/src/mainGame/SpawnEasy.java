package mainGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import mainGame.Game.STATE;

/**
 * Contains the programming of levels 1-10, as well as handles level progression
 * 
 * @author Brandon Loehle 5/30/16
 */

public class SpawnEasy {

	public static int LEVEL_SET = 1;
	private Handler handler;
	private HUD hud;
	private Game game;
	private int scoreKeep = 0;
	private Random r = new Random();
	private int randx, randy;
	private int spawnTimer;
	private int levelTimer;
	private String[] side = { "left", "right", "top", "bottom" };
	ArrayList<Integer> levels = new ArrayList<Integer>(); // MAKE THIS AN ARRAY LIST SO I CAN REMOVE OBJECTS
	private int index;
	private int levelsRemaining;
	private int levelNumber = 0;
	private int tempCounter = 0;
	public static boolean spawning = true;
	private int levelCounter = 1;
	private LevelText welcomePit;

	public SpawnEasy(Handler handler, HUD hud, Spawn1to10 spawner, Game game) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		handler.object.clear();
		hud.health = 100;
		hud.setScore(0);
		hud.setLevel(1);
		spawnTimer = 10;
		levelTimer = 150;
		levelsRemaining = 10;
		hud.setLevel(1);
		tempCounter = 0;
		addLevels();
		index = r.nextInt(levelsRemaining);
		levelNumber = 0;
		levelCounter = 1;
	}

	/**
	 * Pre-load every level
	 */
	public void addLevels() {
		for (int i = 1; i <= 10; i++) {
			levels.add(i);
		}
	}

	/**
	 * Called once every 60 seconds by the Game loop
	 */
	public void tick() {
		if (game.getPlayerXInt() > (Game.WIDTH - Game.WIDTH/(6 + (2/3)) - 5)) {
			randx = r.nextInt((Game.WIDTH - (Game.WIDTH - game.getPlayerXInt())) - Game.WIDTH/(6 + (2/3)));
		} else if (game.getPlayerXInt() < Game.WIDTH/(6 + (2/3)) + 5) {
			randx = r.nextInt(Game.WIDTH - game.getPlayerXInt() - Game.WIDTH/(6 + (2/3))) + game.getPlayerXInt() + Game.WIDTH/(6 + (2/3));
		} else {
			if (r.nextInt(2) == 0) {
				randx = r.nextInt((Game.WIDTH - (Game.WIDTH - game.getPlayerXInt())) - Game.WIDTH/(6 + (2/3)));
			} else {
				randx = r.nextInt(Game.WIDTH - game.getPlayerXInt() - Game.WIDTH/(6 + (2/3))) + game.getPlayerXInt() + Game.WIDTH/(6 + (2/3));
			}
		}
		
		if (game.getPlayerYInt() > (Game.HEIGHT - Game.HEIGHT/(6 + (2/3))) - 5) {
			randy = r.nextInt((Game.HEIGHT - (Game.HEIGHT - game.getPlayerYInt())) - Game.HEIGHT/(6 + (2/3)));
		} else if (game.getPlayerYInt() < Game.HEIGHT/(6 + (2/3)) + 5) {
			randy = r.nextInt(Game.HEIGHT - game.getPlayerYInt() - Game.HEIGHT/(6 + (2/3))) + game.getPlayerYInt() + Game.HEIGHT/(6 + (2/3));
		} else {
			if (r.nextInt(2) == 0) {
				randy = r.nextInt(Game.HEIGHT - game.getPlayerYInt() - Game.HEIGHT/(6 + (2/3))) + game.getPlayerYInt() + Game.HEIGHT/(6 + (2/3));
			} else {
				randy = r.nextInt((Game.HEIGHT - (Game.HEIGHT - game.getPlayerYInt())) - Game.HEIGHT/(6 + (2/3)));
			}
		}
		
		LevelText welcome1;
		if (levelNumber <= 0) {
			levelTimer--;
			if (tempCounter < 1) {// display intro game message ONE time
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "EASY MODE",
						ID.Levels1to10Text, handler));
				tempCounter++;
			}
			if (levelTimer <= 0) {// time to play!
				handler.clearEnemies();
				tempCounter = 0;
				levelCounter = 1;
				levelNumber = levels.get(index);
			}

		}
		/*
		 * EVERY LEVEL WORKS THE SAME WAY
		 * 
		 * Only the first level is commented
		 * 
		 * Please refer to this bit of code to understand how each level works
		 * 
		 */
		else if (levelNumber == 1) {// this is level 1
			if (spawning) {
				spawnTimer--;// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;// keep decrementing the level spawnTimer 60 times a second
			if (tempCounter < 1) {// called only once, but sets the levelTimer to how long we want this level to run for
				handler.addObject(welcome1 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
						ID.Levels1to10Text,handler));
				levelTimer = 2000;// 2000 / 60 method calls a second = 33.33 seconds long
				tempCounter++;// ensures the method is only called once
			}
			if (levelTimer == 1900) {
				handler.clearLevelText();
			}
			
			if (spawnTimer == 0) {// time to spawn another enemy
				handler.addObject(
						new EnemyBasic(randx, randy, 7, 7, ID.EnemyBasic, handler));// add them to the handler, which handles all game objects
				spawnTimer = 150;// reset the spawn timer
			}
			if (levelTimer == 0) {// level is over
				handler.clearEnemies();// clear the enemies
				hud.setLevel(hud.getLevel() + 1);// Increment level number on HUD
				levelCounter++;
				spawnTimer = 40;
				tempCounter = 0;// reset tempCounter
				if (levelsRemaining == 1) {// time for the boss!
					levelNumber = 101;// arbitrary number for the boss level
				} else {// not time for the boss, just go to the next level
					levels.remove(index);// remove the current level from being selected
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);// pick another level at random
					levelNumber = levels.get(index);// set levelNumber to whatever index was randomly selected
				}
			}
		} else if (levelNumber == 2) {
      
			if (spawning) {
				spawnTimer--;// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (tempCounter < 1) {
				LevelText welcome2 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome2);
				levelTimer = 2000;
				tempCounter++;
			}
			if (tempCounter	> 1900) {
				handler.clearLevelText();
			}
			if (spawnTimer == 60) {
				handler.addObject(
						new EnemySweep(randx, randy, 13, 2, ID.EnemySweep, handler));
			} else if (spawnTimer == 40) {
				handler.addObject(
						new EnemySweep(randx, randy, 13, -2, ID.EnemySweep, handler));
			} else if (spawnTimer == 20) {
				handler.addObject(
						new EnemySweep(randx, randy, 13, 4, ID.EnemySweep, handler));
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 13, -4, ID.EnemySweep, handler));
				spawnTimer = 120;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 3) {
			if (spawning) {
				spawnTimer--;// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (tempCounter < 1) {
				LevelText welcome3 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text, handler);
				handler.addObject(welcome3);
				levelTimer = 1500;
				tempCounter++;
			}
			if (levelTimer == 1400) {
				handler.clearLevelText();
			}
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -5, ID.EnemySmart, handler));
				spawnTimer = 150;
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 4) {
			levelTimer--;
			if (tempCounter < 1) {
				LevelText welcome4 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome4);
				levelTimer = 1300;
				tempCounter++;
				handler.addObject(new EnemyShooter(randx - 35, randy - 75, 100, 100,
						-10, ID.EnemyShooter, this.handler));
			}
			if (levelTimer == 1200) {
				
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 5) {
			if (spawning) {
				spawnTimer--;// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (tempCounter < 1) {
				LevelText welcome5 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome5);
				levelTimer = 1400;
				tempCounter++;
			}
			if (levelTimer == 1300) {
				handler.clearLevelText();
			}
			if (spawnTimer <= 0) {
				handler.addObject(new EnemyBurst(-200, 200, 30, 30, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 180;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 6) {
			if (spawning) {
				spawnTimer--;// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (tempCounter < 1) {
				LevelText welcome6 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome6);
				levelTimer = 1500;
				tempCounter++;
			}
			if (levelTimer == 1400) {
				handler.clearLevelText();
			}
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 5, 5, ID.EnemyBasic, handler));
				spawnTimer = 50;
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 40;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 7) {
			if (spawning) {
				spawnTimer--;// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (tempCounter < 1) {
				LevelText welcome7= new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome7);
				levelTimer = 1200;
				tempCounter++;
			}
			if (levelCounter == 1100) {
				handler.clearLevelText();
			}
			if (spawnTimer == 200) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 2, ID.EnemySweep, handler));
			} else if (spawnTimer == 100) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -2, ID.EnemySweep, handler));
			} else if (spawnTimer == 50) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 4, ID.EnemySweep, handler));
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -4, ID.EnemySweep, handler));
				spawnTimer = 400;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 8) {
      
			if (spawning) {
				spawnTimer--;// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (tempCounter < 1) {
				LevelText welcome8 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome8);
				levelTimer = 1000;
				tempCounter++;
			}
			if (levelTimer == 900) {
				handler.clearLevelText();
				
			}
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -3, ID.EnemySmart, handler));
				spawnTimer = 80;
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 9) {
			LevelText welcome9 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
						ID.Levels1to10Text,handler);
			handler.addObject(welcome9);
			levelTimer--;
			if (tempCounter < 1) {	
				handler.addObject(new EnemyShooter(randx - 35, randy - 75, 200, 200,
						-10, ID.EnemyShooter, this.handler));
				levelTimer = 2000;
				tempCounter++;
			}
			if (levelTimer == 2400) {
				handler.clearLevelText();
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 10) {
			if (spawning) {
				spawnTimer--;// keep decrementing the spawning spawnTimer 60 times a second
			}
			LevelText welcome10 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
						ID.Levels1to10Text,handler);
			handler.addObject(welcome10);
			levelTimer--;
			if (tempCounter < 1) {			
				levelTimer = 1400;
				tempCounter++;
			}
			if (levelCounter == 1300) {
				handler.clearLevelText();
			}
			if (spawnTimer <= 0) {
				handler.addObject(new EnemyBurst(-200, 200, 25, 25, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 100;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		}

		else if (levelNumber == 101) {// arbitrary number for the boss
			System.out.println("101");
			LEVEL_SET++;
			MouseListener.setEasy(true);
			Game.fuckItUpBrah();
			levelCounter = 1;
			game.gameState = STATE.Upgrade;
		}
	}

	public void skipLevel() {
		if (levelsRemaining == 1) {
			tempCounter = 0;
			levelNumber = 101;
		} else if (levelsRemaining > 1) {
			levels.remove(index);
			levelsRemaining--;
			System.out.println(levelsRemaining);
			levelCounter++;
			tempCounter = 0;
			index = r.nextInt(levelsRemaining);
			levelNumber = levels.get(index);
		}
	}
	
	public static void setSpawn(boolean x) {
		spawning = x;
	}

	public void restart() {
		levelNumber = -10;
		tempCounter = 0;
		levelTimer = 150;
		levelsRemaining = 10;
		index = r.nextInt(levelsRemaining);
	}
	
	public int getLevelNumber(){
		return levelNumber;
	}
	
	public int getLevelsRemaining(){
		return levelsRemaining;
	}
	
	public void setLevelsRemaining(int levelRem){
		levelsRemaining = levelRem;
	}
	
	public void setLevelNumber(int l){
		levelNumber = l;
	}
	
	public void resetTempCounter(){
		tempCounter = 0;
	}
	
}