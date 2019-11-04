package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main Heads Up Display of the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class HUD {
	private static double BASE_HEALTH = 100; //base value for hp, if changing healthValue, change this as well
	private double healthValue = 100; // updated to healthValueMax
	private double healthValueMax = 100; //total value is restricted by numHealthMax after scalar multiplication
	private double healthValueScalar = 20;//scalar for hp bar in upgrades section
	private double greenValue = 255;
	private int score = 00000000000;
	private int level = 0;
	private boolean regen = false;
	private int timer = 10;
	private int healthBarWidth = 400;
	private double healthBarModifier = 2.5;//unsure if purely visual or not?
	private boolean health = false;
	private String ability = "";
	private int abilityUses = 0;
	private Color scoreColor = Color.white;
	private Color freezeColor = new Color(0, 255, 255, 25);
	private Color regenColor = new Color(120, 255, 120);
	private int extraLives = 0;
	private static int NUM_LIVES_MAX = 1;
	private double randnumber;
	private String highScoreString = "";
	private double costMultipier = 1.25;
	private double cost = 500;
	private static double COST = 500;
	private double activeCost = 3000;
	private static double ACTIVECOST =3000;
	private int numFreeze=0;
	private static int NUM_FREEZE_MAX = 2;
	private static int NUM_REGEN_VALUE = 0;
	private int numRegen=0;
	private int numRegenMax = 10;//max number allowed for regen perk
	private int numHealth=0;
	private static int NUMHEALTH = 0;
	private int numHealthMax = 10;//maximum number allowed for health perk
	private static int NUM_SPEED = 0;
	private int numSpeed=NUM_SPEED;
	private static int NUM_SPEED_MAX = 1;
	private static int NUM_SHRINK = 0;
	private int numShrink=NUM_SHRINK;
	private int numShrinkMax = 5;
	private static int NUM_ARMOR = 0;
	private int numArmor=NUM_ARMOR;
	private static int numArmorMax = 5;
	private int numClear=0;
	private static int NUM_CLEAR_MAX = 2;
	private double regenValue = 0; //total value is restricted by numRegenMax after scalar multiplication
	private double regenValueScalar = .1;//scalar for regenValue, is multiplied by numRegen

	private ArrayList<String> leaderboard;

	public int getNumClear() {
		return numClear;
	}
	public int getNumClearMax(){
		return NUM_CLEAR_MAX;
	}

	public void setNumClear() {
		this.numClear += 1;
	}

	public void reduceNumClear(){
		numClear -= 1;
	}

	public double getregenValue() {
		return regenValue;
	}
	public double getHealthValueMax(){
		return healthValueMax;
	}
	public double getRegenValueScalar(){
		return regenValueScalar;
	}

	public void setHealthValueMax(double healthMax){
		this.healthValueMax = healthMax;
		if(getHealthValueMax() == (BASE_HEALTH + (healthValueScalar * numHealthMax))){
			this.getHealthValueMax();
		}
		else{
			this.healthValueMax = (BASE_HEALTH + (healthValueScalar * this.getNumHealth()));
			this.healthValue = healthValueMax;
			healthBarModifier = (250/healthValueMax);
			healthBarWidth = 4*(int)healthValueMax;
		}
	}
	public double getHealthValue(){
		return healthValue;
	}
	public void setHealthValue(double health){
		this.healthValue = health;
	}
	public double getHealthValueScalar(){
		return healthValueScalar;
	}



	public double getBaseHealth(){
		return BASE_HEALTH;
	}

	public void setregenValue() { //possible regen fix
		if(getregenValue() == regenValueScalar * numRegenMax ){
			this.getregenValue();
		}
		else{
			this.regenValue = regenValueScalar * this.getNumRegen();
		}
		System.out.println("Get Regen value: " + this.getregenValue());//debug statement
	}

	public int getNumFreeze() {
		return numFreeze;
	}
	public int getNumFreezeMax(){
		return NUM_FREEZE_MAX;
	}

	public void setNumFreeze() {
		this.numFreeze += 1;
	}
	public void reduceNumFreeze(){
		this.numFreeze -= 1;
	}


	public int getNumRegen() {
		return numRegen;
	}

	public void setNumRegen() {// possible regen fix
		if(this.getNumRegen() < numRegenMax) {
			this.numRegen += 1;
		}
		else {
			this.getNumRegen();
		}
	}
	public int getNumRegenMax(){//used to prevent paying for regen skill that are maxed out, in MouseListener.java
		return numRegenMax;
	}

	public int getNumHealth() {
		return numHealth;
	}

	public void setNumHealth() {
		if(this.getNumHealth() < numHealthMax){
			this.numHealth += 1;
		}
		else{
			this.getNumHealth();
		}
	}

	public int getNumHealthMax(){
		return numHealthMax;
	}

	public int getNumSpeed() {
		return numSpeed;
	}

	public void setNumSpeed() {
		this.numSpeed += 1;
	}
	public int getNumSpeedMax(){
		return NUM_SPEED_MAX;
	}

	public int getNumShrink() {
		return numShrink;
	}

	public void setNumShrink() {
		if(this.getNumShrink() < numShrinkMax){
			this.numShrink += 1;
		}
		else {
			this.getNumShrink();
		}
	}
	public int getNumShrinkMax(){
		return numShrinkMax;
	}

	public int getNumArmor() {
		return numArmor;
	}
	public int getNumArmorMax(){
		return numArmorMax;
	}

	public void setNumArmor() {
		if(this.getNumArmor() < numArmorMax){
			this.numArmor += 1;
		}
		else {
			this.getNumArmor();
		}

	}


	public double getCostMultipier() {
		return costMultipier;
	}

	public void setCostMultipier(double costMultipier) {
		this.costMultipier = costMultipier;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getActiveCost(){
		return activeCost;
	}
	public void setActiveCost(double a){
		this.activeCost = a;
	}

	public void tick() {
		healthValue = Game.clamp(healthValue, 0, healthValue);
		healthValue = Game.clamp(healthValue, 0, healthValueMax);
		greenValue = Game.clamp(greenValue, 0, 255);
		greenValue = healthValue * healthBarModifier;
		
		
		
		//each tick generate a random # and if that random number equals a specified #, draw a coin
		
		if (regen) {// regenerates health if that ability has been unlocked
			timer--;
			if (timer == 0) {
				healthValue += this.getregenValue();
				timer = 10;
			}
			healthValue = Game.clamp(healthValue, 0, healthValueMax);
		}
	}
	
	public void reset(){ //resets all abilities back to zero state
		resetCost();
		resetRegen();
		resetHealth();
		resetShrink();
		resetArmor();
		resetSpeed();
	}


	public void render(Graphics g) {
		
		
		Font font = new Font("Amoebic", 1, 30);
		g.setColor(Color.GRAY);
		g.fillRect(15, 15, healthBarWidth, 64);
		g.setColor(new Color(75, (int) greenValue, 0));
		g.fillRect((int) 15, (int) 15, (int) healthValue * 4, 64);
		if (regen && healthValue < healthValueMax)
			g.setColor(regenColor);
		else
			g.setColor(scoreColor);
		g.drawRect(15, 15, healthBarWidth, 64);
		if (Handler.getFreeze()) {
			g.setColor(Color.GRAY);
			g.fillRect(1560, 20, 300, 30);
			g.setColor(Color.CYAN);
			g.fillRect(1560, 20, Handler.getTimer(), 30);
			g.setColor(scoreColor);
			g.drawRect(1560, 20, 300, 30);
			g.setColor(freezeColor);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}

		g.setFont(font);
		g.setColor(scoreColor);
		g.drawString("Score: " + score, 15, 115);
		g.drawString("Level: " + level, 15, 150);
		g.drawString("Extra Lives: " + extraLives, 15, 185);
		
		if (this.highScoreString != null){
			g.drawString("High Score:", 15, 950);
			g.drawString(this.highScoreString, 15, 1000);
		}

		if (ability.equals("freezeTime")) {
			g.drawString("Time Freezes: " + abilityUses, Game.WIDTH - 300, 64);
		} else if (ability.equals("clearScreen")) {
			g.drawString("Screen Clears: " + abilityUses, Game.WIDTH - 300, 64);
		} else if (ability.equals("levelSkip")) {
			g.drawString("Level Skips: " + abilityUses, Game.WIDTH - 300, 64);
		}
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}
	
	public String getAbility(){
		return ability;
	}

	public int getAbilityUses() {
		return this.abilityUses;
	}

	public void setAbilityUses(int abilityUses) {
		this.abilityUses += abilityUses;
	}

	public void updateScoreColor(Color color) {
		this.scoreColor = color;
	}

	public void setScore(int score) {
		this.score += score;
	}
	/**
	 * 
	 * @param score: the original score
	 * sets the end game score used to show score
	 */

	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean getHealth(){
		return health;
	}

	/*public void setHealth(double health) {//original
		this.health = health;
	}*/
	public void setHealth() {
		health = true;
	}

	public void setRegen() {
		regen = true;
	}


	public void setExtraLives(int lives) {
		this.extraLives = lives;
	}

	public int getExtraLives() {
		return this.extraLives;
	}

	public int getNumLivesMax(){
		return NUM_LIVES_MAX;
	}

///////////////////individual power-ups reset section/////////////////////
	public void resetRegen() {
		regen = false;
		regenValue = NUM_REGEN_VALUE;
		numRegen = NUM_REGEN_VALUE;
	}
	public void resetShrink(){
		numShrink = NUM_SHRINK;
	}
	public void resetArmor(){
		numArmor = NUM_ARMOR;
	}
	public void resetHealth() {

		health = false;
		numHealth = NUMHEALTH;
		healthValueMax = BASE_HEALTH;//additional piece
		healthValue = healthValueMax;
		healthBarModifier = 2.5;
		healthBarWidth = 400;
		greenValue = 255;
	}
	public void resetCost(){
		cost = COST;
		activeCost = ACTIVECOST;
	}
	public void resetSpeed(){
		numSpeed = NUM_SPEED;
	}
///////////////////individual power-ups reset section/////////////////////

	public void restoreHealth() {
		this.healthValue = healthValueMax;
	}


	public void setHighScore(String data) {
		
		leaderboard = new ArrayList<String>(Arrays.asList(data.split(",")));
		System.out.println(leaderboard.size());
		
		this.highScoreString = leaderboard.get(0);
		
	}
	
	public ArrayList<String> getLeaderboard(){
		return leaderboard;
	}
}