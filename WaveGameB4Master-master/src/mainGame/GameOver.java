package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.net.*;
import java.util.*;

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
	private int endScore = 0;
	private File scoresFile = new File("src//mainGame//scores.txt");//txt file
	private File hardScoresFile = new File ("src//mainGame//hardScores.txt");
	private Scanner scan = new Scanner(scoresFile);
	private Scanner hardScan = new Scanner(hardScoresFile);
	private FileWriter hardFWriter = new FileWriter(hardScoresFile, true);
	private FileWriter fWriter = new FileWriter(scoresFile, true);
	private BufferedWriter hardBWriter = new BufferedWriter(hardFWriter);
	private BufferedWriter bWriter = new BufferedWriter(fWriter);
	private PrintWriter hardPWriter = new PrintWriter(hardBWriter);
	private PrintWriter pWriter = new PrintWriter(bWriter);
	private ArrayList<String> fileList = new ArrayList<String>();
	private ArrayList<String> hardFileList = new ArrayList<String>();

	public GameOver(Game game, Handler handler, HUD hud) throws IOException {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		timer = 90;
		this.retryColor = Color.white;
		hud.reset();
	}

	public void fileToArrayList() { //reads from the file and inserts each line into an arraylist
		String currentLine;
		while (scan.hasNextLine()) {
			currentLine = scan.nextLine();
			fileList.add(currentLine);
		}
	}


	public void hardfileToArrayList() {
		String currentLine;
		while (hardScan.hasNextLine()) {
			currentLine = hardScan.nextLine();
			hardFileList.add(currentLine);
		}
	}

	public void sortScores(){
		fileList.sort(new Comparator<String>() { //sort the arraylist
			@Override
			public int compare(String o1, String o2) {
				return extract(o2) - extract(o1); //compares the two scores returned by extract to sort them in order
			}

			int extract(String tempLine) {
				String[] num = tempLine.split(" "); //splits each line with space, separating the score from the name
				return Integer.parseInt(num[0]); //returns the score which is used for sorting the arraylist
			}
		});
	}

	public void hardSortScores(){
		hardFileList.sort(new Comparator<String>() { //sort the arraylist
			@Override
			public int compare(String o1, String o2) {
				return extract(o2) - extract(o1); //compares the two scores returned by extract to sort them in order
			}

			int extract(String tempLine) {
				String[] num = tempLine.split(" "); //splits each line with space, separating the score from the name
				return Integer.parseInt(num[0]); //returns the score which is used for sorting the arraylist
			}
		});
	}

	public void arrayListToFile() {
		try {
			new FileWriter(scoresFile, false).flush(); //clears the file

		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String line : fileList) { //prints the newly sorted list into the file
			pWriter.println(line);
		}
		pWriter.flush();
	}

	public void hardArrayListToFile() {
		try {
			new FileWriter(hardScoresFile, false).flush(); //clears the file

		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String line : hardFileList) { //prints the newly sorted list into the file
			hardPWriter.println(line);
		}
		hardPWriter.flush();
	}

	public int determineMin(ArrayList<String> list) {
		return Integer.parseInt((list.get(0)).split(" ")[0]);
	}

	public ArrayList<String> getFileList(){
		fileToArrayList();
		return fileList;
	}

	public ArrayList<String> getHardFileList() {
		hardfileToArrayList();
		return hardFileList;
	}

	public void sendScore() {
		endScore = hud.getScore();//used to show score at end
		fileToArrayList(); //reads from file and inserts into arraylist first
		System.out.println(Arrays.toString(fileList.toArray()));
		if (determineMin(fileList) < endScore || fileList.size() < 10) {
			String username = JOptionPane.showInputDialog("Enter a username to submit your score!");
			String Highscore = hud.getScore() + " " + username;
			if (fileList.size() >= 10) {
				fileList.remove(fileList.get(0));
			}
			fileList.add(Highscore);
			pWriter.flush();
			sortScores();
			arrayListToFile();
		}
	}

	public void hardSendScore() {
		endScore = hud.getScore();//used to show score at end
		hardfileToArrayList(); //reads from file and inserts into arraylist first
		System.out.println(Arrays.toString(hardFileList.toArray()));
		if (determineMin(hardFileList) < endScore || hardFileList.size() < 10) {
			String username = JOptionPane.showInputDialog("Enter a username to submit your score!");
			String Highscore = hud.getScore() + " " + username;
			if (hardFileList.size() >= 10) {
				hardFileList.remove(hardFileList.get(0));
			}
			hardFileList.add(Highscore);
			hardPWriter.flush();
			hardSortScores();
			hardArrayListToFile();
		}
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
