package mainGame;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class HardLeaderboard {

	private Game game;
	private Handler handler;
	private HUD hud;
	private int timer;
	private Color retryColor;
	private String text;
	private GameOver gameOver;

	public HardLeaderboard(Game game, Handler handler, HUD hud, GameOver gameOver) throws MalformedURLException {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.retryColor = Color.white;
		this.gameOver = gameOver;
	}

	public void tick(){
		handler.clearPlayer();
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		Font font = new Font("Amoebic", 1, 85);
		Font font2 = new Font("Amoebic", 1, 40);
		g.setFont(font);
		text ="Hard Mode";
		g.drawString(text, Game.WIDTH * 2 / 3 - getTextWidth(font2,text)/2 - 50, Game.HEIGHT/3 - 50);
		g.setFont(font2);

		ArrayList<String> hardLeaderboard = gameOver.getHardFileList();
		
		for (int i = 0; i < hardLeaderboard.size(); i++){
			text = hardLeaderboard.get(i);
			g.drawString(text,Game.WIDTH * 2 / 3 - getTextWidth(font2,text)/2 + 75, Game.HEIGHT/3 + (50*i));
		}
	}

	/**
	 * Function for getting the pixel width of text
	 * 
	 * @param font
	 *            the Font of the test
	 * @param text
	 *            the String of text
	 * @return width in pixels of text
	 */
	public int getTextWidth(Font font, String text) {
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

}
