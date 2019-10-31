package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * The graphics behind the menu that resemble fireworks
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class MenuFireworks extends GameObject {

	private Handler handler;
	private Random r;
	private double velX;
	private double x;
	private double y;
	private int sizeX;
	private int sizeY;
	private int max = 5;
	private int min = -5;
	private Color color;

	public MenuFireworks(double x, double y, int sizeX, int sizeY, double velX, double velY, Color color, ID id,
			Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		r = new Random();
		this.color = color;

	}

	public void render(Graphics g) {
		g.setColor(this.color);
		g.fillOval((int) this.x, (int) this.y, sizeX, sizeY);
	}

	public void tick() {
		this.x += velX;
		this.y += velY;

		if (this.y <= 100) {// once it gets this high
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.id == ID.Firework) {// find the firework
					sparks(tempObject);// create sparks
					handler.removeObject(tempObject);// delete big circle
				}
			}
		}
	}

	public void sparks(GameObject tempObject) {// when the big circle breaks into a bunch of smaller ones
		for (int i = 0; i < 3; i++) {
			for (int ii = -5; ii < 0; ii++) {
				handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), ii,
						this.color, ID.FireworkSpark, handler));
			}
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt(4) + 1), 0, this.color,
					ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, -(r.nextInt(4) + 1), 0, this.color,
					ID.FireworkSpark, handler));
			for (int iii = 1; iii < 6; iii++) {
				handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), iii,
						this.color, ID.FireworkSpark, handler));
			}
		}
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}
