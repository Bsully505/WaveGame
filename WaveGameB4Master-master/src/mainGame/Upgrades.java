package mainGame;

import mainGame.Game.STATE;

/**
 * The upgrades that a user can have (they modify the game for the user)
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Upgrades {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Player player;
	private SpawnHard spawnerH;
	private Spawn1to10 spawner;
	private Spawn10to20 spawner2;
	private UpgradeScreen upgradeScreen;
	private String ability = "";
	private int useCounterFreeze = 0;
	private int useCounterClear = 0;
	private int abilityFreeze = 0;
	private int abilityClear = 0;
	private static double SIZE_SCALAR = .1;
	private static double DAMAGE_RESISTANCE_SCALAR = .05;
	private static double SPEED_BOOST_SCALAR = 1.5;

	public Upgrades(Game game, Handler handler, HUD hud, UpgradeScreen upgradeScreen, Player player, SpawnHard spawnerH, Spawn1to10 spawner,
					Spawn10to20 spawner2) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
		this.spawnerH = spawnerH;
		this.spawner = spawner;
		this.spawner2 = spawner2;
	}
	public void setAbilityFreeze(int freeze){
		abilityFreeze = freeze;
	}

	public void setAbilityClear(int clear){
		abilityClear = clear;
	}

	public void clearScreenAbility() {
		handler.clearEnemies();
		hud.setAbilityUses(-1);
		useCounterClear += 1;
		if (hud.getAbilityUses() == 0) {
			hud.reduceNumClear();
			ability = "";
			hud.setAbility(ability);
		}
		else if(useCounterClear == abilityClear){
			hud.reduceNumClear();
			useCounterClear = 0;
		}

	}

	public void decreasePlayerSize() {//changed math to not have reduced outcomes per purchase
		//size shrink reduced from .5 to .1
		player.setPlayerSize(player.getDefaultPlayerSize() - (player.getDefaultPlayerSize()*(SIZE_SCALAR*hud.getNumShrink())));
		//player.setPlayerSize(player.getPlayerSize()/SIZE_SCALAR);//old method had reduced returns per purchase
	}

	public double getSizeScalar(){
		return SIZE_SCALAR;
	}
	public double getSpeedBoostScalar(){
		return SPEED_BOOST_SCALAR;
	}

	public void extraLife() {
		hud.setExtraLives(hud.getExtraLives() + 1);
	}

	public void healthIncrease() {
		hud.getHealthValueMax();
	}

	public void healthRegeneration() {
		hud.setRegen();
	}

	public void improvedDamageResistance() {//changed math to not have reduced outcomes per purchase
		//changed from .5 to .05
		player.setDamage(player.getDamageDefault()-(player.getDamageDefault()*(DAMAGE_RESISTANCE_SCALAR*hud.getNumArmor())));
		//player.setDamage(player.getDamage()/DAMAGE_RESISTANCE_SCALAR);
	}
	public double getDRScalar(){
		return DAMAGE_RESISTANCE_SCALAR;
	}


	public void levelSkipAbility() {
		handler.clearEnemies();
		hud.setLevel(hud.getLevel() + 1);
		if(game.gameState == STATE.GameHard){
			spawnerH.skipLevel();
		}
		else{
		if (Spawn1to10.LEVEL_SET == 1 || Spawn1to10.LEVEL_SET == 3) {
			spawner.skipLevel();
		} else if (Spawn1to10.LEVEL_SET == 2) {
			spawner2.skipLevel();
		}}
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}

	}

	public void freezeTimeAbility() {
		handler.pause();
		Spawn1to10.setSpawn(false);
		Spawn10to20.setSpawn(false);
		hud.setAbilityUses(-1);
		useCounterFreeze += 1;
		if (hud.getAbilityUses() == 0) {
			hud.reduceNumFreeze();
			ability = "";
			hud.setAbility(ability);
		}
		else if(useCounterFreeze  == abilityFreeze){
			hud.reduceNumFreeze();
			useCounterFreeze = 0;
		}

	}

	public void speedBoost() {
		player.setPlayerSpeed(SPEED_BOOST_SCALAR);
		//Player.playerSpeed *= SPEED_BOOST_SCALAR;//2
		//Player.diagonalPlayerSpeed *= SPEED_BOOST_SCALAR;//2
	}

	public String getAbility() {
		return ability;
	}

	/**
	 * Activate the correct upgrade
	 * 
	 * @param path
	 *            is to the image of the upgrade that was pressed by the user
	 */
	public void activateUpgrade(String path) {
		if (path.equals("images/clearscreenability.png")) {
			ability = "clearScreen";
			hud.setAbility(ability);
			hud.setAbilityUses(3);
		} else if (path.equals("images/decreaseplayersize.png")) {
			ability = "decreasePlayerSize";
			decreasePlayerSize();
		} else if (path.equals("images/extralife.png")) {
			ability = "extraLife";
			extraLife();
		} else if (path.equals("images/healthincrease.png")) {
			ability = "HealthIncrease";
			healthIncrease();
		} else if (path.equals("images/healthregeneration.png")) {
			ability = "HealthRegen";
			healthRegeneration();
		} else if (path.equals("images/improveddamageresistance.png")) {
			ability = "ImprovedDamageRistance";
			improvedDamageResistance();
		} else if (path.equals("images/levelskipability.png")) {
			ability = "levelSkip";
			hud.setAbility(ability);
			hud.setAbilityUses(1);
		} else if (path.equals("images/freezetimeability.png")) {
			ability = "freezeTime";
			hud.setAbility(ability);
			hud.setAbilityUses(5);
		} else if (path.equals("images/speedboost.png")) {
			ability = "SpeedBoost";
			speedBoost();
		}
		
		System.out.println(ability);

	}
	
	public void setAbility(String theAbility){
		if(theAbility.equals("clearScreen")){
			ability = "clearScreen";
			hud.setAbility(ability);
			hud.setAbilityUses(3);
		} else if (theAbility.equals("decreasePlayerSize")) {
			ability = "decreasePlayerSize";
			decreasePlayerSize();
		} else if (theAbility.equals("extraLife")) {
			ability = "extraLife";
			extraLife();
		} else if (theAbility.equals("HealthIncrease")) {
			ability = "HealthIncrease";
			healthIncrease();
		} else if (theAbility.equals("HealthRegen")) {
			ability = "HealthRegen";
			healthRegeneration();
		} else if (theAbility.equals("ImprovedDamageRistance")) {
			ability = "ImprovedDamageRistance";
			improvedDamageResistance();
		} else if (theAbility.equals("levelSkip")) {
			ability = "levelSkip";
			hud.setAbility(ability);
			hud.setAbilityUses(1);
		} else if (theAbility.equals("freezeTime")) {
			ability = "freezeTime";
			hud.setAbility(ability);
			hud.setAbilityUses(5);
		} else if (theAbility.equals("SpeedBoost")) {
			ability = "SpeedBoost";
			speedBoost();
		}
	}

	public void resetUpgrades() {
		player.resetSpeed();
		hud.reset();
		hud.setExtraLives(0);
		player.setPlayerSize(player.getDefaultPlayerSize());
		upgradeScreen.resetPaths();
	}

	

}
