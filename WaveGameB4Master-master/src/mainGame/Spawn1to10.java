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

public class Spawn1to10 {

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
	private int countDown = 0;
	public static boolean spawning = false;
	private int levelCounter = 1;
	private LevelText welcomePit;
	private LevelText welcome1, counter3,counter2,counter1;
	private LevelText controls;
	private int randnumber;
	private long timechecker;// used to check the length of time periods used by Bryan 11/6/2019
	private int delay =0;

	public Spawn1to10(Handler handler, HUD hud, Game game) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		handler.object.clear();
		hud.reset();
		hud.setScore(-1*hud.getScore());//resets the score
		setSpawnTimer(10);
		levelTimer = 150;
		levelsRemaining = 10;
		hud.setLevel(1);
		setTempCounter(0);
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
		randnumber = getRandomInteger(100, 1);
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

		if (levelNumber <= 0) {
			levelTimer--;
			if (getTempCounter() < 1) {// display intro game message ONE time
				welcome1 = new LevelText(Game.WIDTH / 2 - 375, Game.HEIGHT / 2 - 250, "Let's start off easy...",
						ID.Levels1to10Text, handler);
				handler.addObject(welcome1);
				LevelText controls = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Controls",
						ID.Levels1to10Text, handler);
				handler.addObject(controls);
				setTempCounter(getTempCounter() + 1);

			}

			if (levelTimer <= 0) {// time to play!
				//handler.removeObject(counter1);
				handler.clearCoins();
				setTempCounter(0);
				levelCounter = 1;
				index = r.nextInt(levelsRemaining - 5);
				levelNumber = 1;
			}

		}
		if (levelNumber == 0) {
			if(countDown == 0) {
				if(delay==0) {
				counter3 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 50, "3",
						ID.Levels1to10Text, handler);
				handler.addObject(counter3);
				}
				if(delay>=50) {
				countDown++;
				}
				else {
					delay++;
				}
			}
			else if(countDown == 1) {
				if(delay ==50) {
				handler.removeObject(counter3);
				counter2 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 50, "2",
						ID.Levels1to10Text, handler);
				handler.addObject(counter2);
				}
				if(delay==100) {
				countDown++;
				}
				else {
					delay++;
				}
			}
			else if(countDown == 2) {
				if(delay==100) {
				handler.removeObject(counter2);
				counter1 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 50, "1",
						ID.Levels1to10Text, handler);
				handler.addObject(counter1);
				}
				if (delay==140) {
					handler.removeObject(counter1);
				}
				else {
					delay++;
				}
			}
		}

		/**
		 * levels 
		 * 								 
		 * 1: Green 							
		 * 2: Red								
		 * 3: BURST								
		 * 4: Cyan 								
		 * 5: yellow square 					
		 * 6: green and red						
		 * 7:green + burst  					 
		 * 8:green + cyan 						 
		 * 9: red + red							
		 * 10: red boss							
		 * 
		 */

		/*
		 * EVERY LEVEL WORKS THE SAME WAY
		 * 
		 * Only the second level is commented
		 * 
		 * Please refer to the next bit of code to understand how each level works
		 * 
		 */
		/**
		 * easter egg level 
		 */
		if(levelNumber == -1){
			if (getTempCounter() < 1) {
				handler.clearCoins();
				LevelText welcome3 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 130, ("Level " + hud.getLevel()),
						ID.Levels1to10Text, handler);
				handler.addObject(welcome3);
				levelTimer = 1000;

				setTempCounter(getTempCounter() + 1);
			}
		}
		if (levelNumber <= 20 && randnumber == 10) {
			handler.addPickup(new PickupCoin(getRandomInteger(2000, 1),
					getRandomInteger(1000, 1), ID.PickupCoin, "images/PickupCoin.PNG", handler, game ));
		}
		else if (levelNumber == 1) {
			if (spawning) {
				setSpawnTimer(getSpawnTimer() - 1);// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (getTempCounter() < 1) {
				handler.clearCoins();
				LevelText welcome3 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 130, ("Level " + hud.getLevel()),
						ID.Levels1to10Text, handler);
				handler.addObject(welcome3);
				levelTimer = 1000;

				setTempCounter(getTempCounter() + 1);
			}
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			if (getSpawnTimer() == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -5, ID.EnemySmart, handler));
				setSpawnTimer(100);
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				setSpawnTimer(10);
				setTempCounter(0);
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					if (levelsRemaining > 5) {
						System.out.println("Level Number was: " + levelNumber + " And is staying low!");
						index = r.nextInt(levelsRemaining - 5);
						levelNumber++;// = levels.get(index);
						System.out.println("And is changing to: " + levelNumber + "   " + index);
					}
					else {
						System.out.println("Level Number was: " + levelNumber + " And is going up!");
						index = r.nextInt(levelsRemaining);
						levelNumber++;// = levels.get(index);
						System.out.println("And is changing to: " + levelNumber);
					}
				}
			}
		} else if (levelNumber == 2) {
			if (spawning) {
				setSpawnTimer(getSpawnTimer() - 1);// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;// keep decrementing the level spawnTimer 60 times a second
			if (getTempCounter() < 1) {// called only once, but sets the levelTimer to how long we want this level to run for
				handler.clearCoins();
				handler.addObject(welcome1 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
						ID.Levels1to10Text,handler));
				levelTimer = 1000;// 2000 / 60 method calls a second = 33.33 seconds long
				setTempCounter(getTempCounter() + 1);// ensures the method is only called once
			}
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			if (getSpawnTimer() == 0) {// time to spawn another enemy

				handler.addObject(
						new EnemyBasic(randx, randy, 9, 9, ID.EnemyBasic, handler));// add them to the handler, which handles all game objects
				setSpawnTimer(100);// reset the spawn timer
			}
			if (levelTimer == 0) {// level is over
				handler.clearEnemies();// clear the enemies
				handler.clearCoins();
				hud.setLevel(hud.getLevel() + 1);// Increment level number on HUD
				levelCounter++;
				setSpawnTimer(40);
				setTempCounter(0);// reset tempCounter
				if (levelsRemaining == 1) {// time for the boss!
					levelNumber = 101;// arbitrary number for the boss level
				} else {// not time for the boss, just go to the next level
					levels.remove(index);// remove the current level from being selected
					levelsRemaining--;
					if (levelsRemaining > 5) { //If there's still more than 5 levels remaining
						System.out.println("Level Number was: " + levelNumber + " And is staying low!");//DO NOT NEED THIS LINE 
						index = r.nextInt(levelsRemaining - 5);// DO NOT NEED THIS LINE 
						levelNumber++; //= levels.get(index);// Increases level by one 
						System.out.println("And is changing to: " + levelNumber + "   " + index);
					}
					else { //If there's just 5 levels remaining
						System.out.println("Level Number was: " + levelNumber + " And is going up!");// DO NOT NEED THIS LINE 
						index = r.nextInt(levelsRemaining);// DO NOT NEED THIS LINE
						levelNumber++; //= levels.get(index); changed this so now levels increase by one 
						System.out.println("And is changing to: " + levelNumber);
					}
				}
			}
		} else if (levelNumber == 3) {
			if (spawning) {
				setSpawnTimer(getSpawnTimer() - 1);// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (getTempCounter() < 1) {
				handler.clearCoins();
				LevelText welcome5 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
				handler.addObject(welcome5);
				levelTimer = 1000;
				setTempCounter(getTempCounter() + 1);
			}
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			if (getSpawnTimer() <= 0) {
				handler.addObject(new EnemyBurst(-200, 200, 50, 50, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				setSpawnTimer(180);
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				setSpawnTimer(10);
				setTempCounter(0);
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					if (levelsRemaining > 5) {
						System.out.println("Level Number was: " + levelNumber + " And is staying low!");
						index = r.nextInt(levelsRemaining - 5);
						levelNumber++;// = levels.get(index);
						System.out.println("And is changing to: " + levelNumber + "   " + index);
					}
					else {
						System.out.println("Level Number was: " + levelNumber + " And is going up!");
						index = r.nextInt(levelsRemaining);
						levelNumber++;// = levels.get(index);
						System.out.println("And is changing to: " + levelNumber);
					}
				}
			}
		} else if (levelNumber == 4) {
			if (spawning) {
				setSpawnTimer(getSpawnTimer() - 1);// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (getTempCounter() < 1) {
				handler.clearCoins();
				LevelText welcome2 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
				handler.addObject(welcome2);
				levelTimer = 1000;
				setTempCounter(getTempCounter() + 1);
			}
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			if (getSpawnTimer() == 30) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 2, ID.EnemySweep, handler));
			} else if (getSpawnTimer() == 20) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -2, ID.EnemySweep, handler));
			} else if (getSpawnTimer() == 10) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 4, ID.EnemySweep, handler));
			} else if (getSpawnTimer() == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -4, ID.EnemySweep, handler));
				setSpawnTimer(80);
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				setTempCounter(0);
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					if (levelsRemaining > 5) {
						System.out.println("Level Number was: " + levelNumber + " And is staying low!");
						index = r.nextInt(levelsRemaining - 5);
						levelNumber++;// = levels.get(index);
						System.out.println("And is changing to: " + levelNumber + "   " + index);
					}
					else {
						System.out.println("Level Number was: " + levelNumber + " And is going up!");
						index = r.nextInt(levelsRemaining);
						levelNumber++;// = levels.get(index);
						System.out.println("And is changing to: " + levelNumber);
					}
				}
			}
		} else if (levelNumber == 5) {
			levelTimer--;
			if (getTempCounter() < 1) {
				handler.clearCoins();
				LevelText welcome4 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
				handler.addObject(welcome4);
				levelTimer = 1000;
				setTempCounter(getTempCounter() + 1);
				handler.addObject(new EnemyShooter(randx - 35, randy - 75, 100, 100,
						-20, ID.EnemyShooter, this.handler));
			}
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				setSpawnTimer(10);
				setTempCounter(0);
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					if (levelsRemaining > 5) {
						System.out.println("Level Number was: " + levelNumber + " And is staying low!");
						index = r.nextInt(levelsRemaining - 5);
						levelNumber++;// = levels.get(index);
						System.out.println("And is changing to: " + levelNumber + "   " + index);
					}
					else {
						System.out.println("Level Number was: " + levelNumber + " And is going up!");
						index = r.nextInt(levelsRemaining);
						levelNumber++;// = levels.get(index);
						System.out.println("And is changing to: " + levelNumber);
					}
				}
			}
		} else if (levelNumber == 6) {
			if (spawning) {
				setSpawnTimer(getSpawnTimer() - 1);// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (getTempCounter() < 1) {
				handler.clearCoins();
				if (levelsRemaining == 5) {
					LevelText welcome6 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Let's step it up with Level " + hud.getLevel()),
							ID.Levels1to10Text,handler);
					handler.addObject(welcome6);
				}
				else {
					LevelText welcome6 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
							ID.Levels1to10Text,handler);
					handler.addObject(welcome6);
				}

				levelTimer = 1000;
				setTempCounter(getTempCounter() + 1);
			}
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			if (getSpawnTimer() % 50 ==0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 7, 7, ID.EnemyBasic, handler));
				if(getSpawnTimer()==0){
					setSpawnTimer(100);
				}
			}
			else{
				if(getSpawnTimer()==75){
					handler.addObject(
							new EnemySmart(randx, randy, -5, ID.EnemySmart, handler));
				}
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				setSpawnTimer(40);
				setTempCounter(0);
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber++;// = levels.get(index);;
				}
			}
		} else if (levelNumber == 7) {
			if (spawning) {
				setSpawnTimer(getSpawnTimer() - 1);// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (getTempCounter() < 1) {
				handler.clearCoins();
				if (levelsRemaining == 5) {
					LevelText welcome7 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Let's step it up with Level " + hud.getLevel()),
							ID.Levels1to10Text,handler);
					handler.addObject(welcome7);
				}
				else {
					LevelText welcome7 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
							ID.Levels1to10Text,handler);
					handler.addObject(welcome7);
				}
				levelTimer = 1000;
				setTempCounter(getTempCounter() + 1);
			}
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			if (getSpawnTimer() % 65 == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -5, ID.EnemySmart, handler));

			}
			if (getSpawnTimer() == 100) {
				handler.addObject(new EnemyBurst(-200, 200, 50, 50, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				//spawnTimer = 130;
			}
			if (getSpawnTimer() == 0) {
				setSpawnTimer(130);
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				setTempCounter(0);
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber++;// = levels.get(index);
				}
			}
		} else if (levelNumber == 8) {
			if (spawning) {
				setSpawnTimer(getSpawnTimer() - 1);// keep decrementing the spawning spawnTimer 60 times a second
			}
			levelTimer--;
			if (getTempCounter() < 1) {
				handler.clearCoins();
				if (levelsRemaining == 5) {
					LevelText welcome8 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Let's step it up with Level " + hud.getLevel()),
							ID.Levels1to10Text,handler);
					handler.addObject(welcome8);
				}
				else {
					LevelText welcome8 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
							ID.Levels1to10Text,handler);
					handler.addObject(welcome8);
				}
				levelTimer = 1000;
				setTempCounter(getTempCounter() + 1);
			}
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			if (getSpawnTimer()%50 == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -6, ID.EnemySmart, handler));			
			}
			if (getSpawnTimer() == 30) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 2, ID.EnemySweep, handler));
			} else if (getSpawnTimer() == 20) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -2, ID.EnemySweep, handler));
			} else if (getSpawnTimer() == 10) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 4, ID.EnemySweep, handler));
			} else if (getSpawnTimer() == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -4, ID.EnemySweep, handler));
			}
			if (getSpawnTimer() == 0) {
				setSpawnTimer(150);
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				setSpawnTimer(10);
				setTempCounter(0);
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber++;// = levels.get(index);
				}
			}
		} else if (levelNumber == 9) {
			if (spawning) {
				setSpawnTimer(getSpawnTimer() - 1);// keep decrementing the spawning spawnTimer 60 times a second
			}

			//This code is inconsistent with the code
			//that loads the rest of the enemies
			//			LevelText welcome9 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
			//						ID.Levels1to10Text,handler);
			//			handler.addObject(welcome9);

			levelTimer--;
			if (getTempCounter() < 1) {	
				handler.clearCoins();
				if (levelsRemaining == 5) {
					LevelText welcome9 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Let's step it up with Level " + hud.getLevel()),
							ID.Levels1to10Text,handler);
					handler.addObject(welcome9);
				}
				else {
					LevelText welcome9 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
							ID.Levels1to10Text,handler);
					handler.addObject(welcome9);
				}
				//levelTimer = 1000;
				//tempCounter++;
				levelTimer = 2500;
				setTempCounter(getTempCounter() + 1);
			}
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			if (getSpawnTimer()%25 == 0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 7, 7, ID.EnemyBasic, handler));
				if (getSpawnTimer() == 0) {
					setSpawnTimer(50);
				}
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				setSpawnTimer(10);
				setTempCounter(0);
				if (levelsRemaining == 1) {
					levelNumber = 10;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber++;
				}
			}
		} 

		else if (levelNumber == 10) {// arbitrary number for the boss
			game.gameState = STATE.Boss;
			if (getTempCounter() < 1) {
				levelTimer = 1000;
				handler.clearCoins();
				LevelText welcomePit = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Welcome to the Pit"),
						ID.Levels1to10Text,handler);
				handler.addObject(welcomePit);
				handler.addObject(new EnemyBoss(ID.EnemyBoss, handler));
				setTempCounter(getTempCounter() + 1);
			} else if (getTempCounter() >= 1) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.EnemyBoss) {
						if (tempObject.getHealth() <= 0 && LEVEL_SET != 3) {
							handler.removeObject(tempObject);
							LEVEL_SET++;
							levelCounter = 1;
							game.gameState = STATE.Upgrade;
						}

						else if (tempObject.getHealth() <= 900){
							handler.clearLevelText();
						}
						else if (tempObject.getHealth() <= 0 && LEVEL_SET == 3) {
							handler.removeObject(tempObject);
							game.gameState = STATE.GameWonHard;
						}
						else if (tempObject.getHealth() == 900){
							handler.removeObject(welcomePit);

						}
					}

				}

			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				setSpawnTimer(10);
				setTempCounter(0);
				if (levelsRemaining == 1) {
					levelNumber = 11;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber++;// = levels.get(index);
				}
			}
		}
	}

	public void skipLevel() {
		if (levelsRemaining == 1) {
			game.gameState = STATE.Upgrade;
			setTempCounter(0);
			LEVEL_SET++;
		} else if (levelsRemaining > 1) {
			levelNumber++;
			levelsRemaining --;
			System.out.println("Levels Remaining: "+levelsRemaining);
			levelCounter++;
			setTempCounter(0);
		}
	}
	public static int getRandomInteger(int maximum, int minimum){

		return ((int) (Math.random()*(maximum - minimum))) + minimum;

	}

	public static void setSpawn(boolean x) {
		spawning = x;
	}

	public void restart() {
		levelNumber = -10;
		setTempCounter(0);
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
		setTempCounter(0);
	}

	public int getSpawnTimer() {
		return spawnTimer;
	}

	public void setSpawnTimer(int spawnTimer) {
		this.spawnTimer = spawnTimer;
	}

	public int getTempCounter() {
		return tempCounter;
	}

	public void setTempCounter(int tempCounter) {
		this.tempCounter = tempCounter;
	}

}
