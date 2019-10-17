package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

/**
 * The game over screen
 *
 * @author Brandon Loehle 5/30/16
 *
 */

public class GameOver {

	private Game game;
	private Handler handler;
	private HUD hud;
	private int timer;
	private Color retryColor;
	private String text;
	private int ticks = 0;
	private int endScore = 0;
	private File scoresFile = new File("src//mainGame//scores.txt");//txt file
	private Scanner scan = new Scanner(scoresFile);
	private FileWriter fWriter = new FileWriter(scoresFile, true);
	private BufferedWriter bWriter = new BufferedWriter(fWriter);
	private PrintWriter pWriter = new PrintWriter(bWriter);

	public GameOver(Game game, Handler handler, HUD hud) throws IOException {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		timer = 90;
		this.retryColor = Color.white;
		hud.reset();
	}

	public void sendScore() {

		endScore = hud.getScore();//used to show score at end
		String username = JOptionPane.showInputDialog("Enter a username to submit your score!");
		String Highscore = new String();
		Highscore = username+" "+ hud.getScore();
		pWriter.append(Highscore);
		pWriter.append("\n");
		pWriter.flush();


	}

	public void tick() {
		handler.clearPlayer();
		handler.clearCoins();
		hud.reset();
		flash();
	}

	public void render(Graphics g) {
		Font font = new Font("Amoebic", 1, 100);
		Font font2 = new Font("Amoebic", 1, 60);
		g.setFont(font);
		text = "Game Over";
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font, text) / 2, Game.HEIGHT / 2 - 150);
		g.setFont(font2);
		text = "Level: " + hud.getLevel();
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 - 50);
		text = "Score: " + endScore;//The end score is just used as a copy of the score before it gets erased because the round ends
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 + 50);
		g.setColor(this.retryColor);
		g.setFont(font2);
		text = "Click anywhere to play again";
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 + 150);
	}

	public void flash() {
		timer--;
		if (timer == 45) {
			this.retryColor = Color.black;
		} else if (timer == 0) {
			this.retryColor = Color.white;
			timer = 90;
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
