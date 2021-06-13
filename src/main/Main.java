package main;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import graphics.Graphics;
import other.Board;
import other.Lecture;

/**
 * The Main class used to launch the game
 * 
 * @author Etienne and Guillaume
 * @version 1.0
 */
public class Main {

	/**
	 * Check if a level or a directory level is given as argument
	 * 
	 * @param args Array of String 
	 * @return The name of a level or null if no arguments are given
	 */
	public static String getLevel(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("--level")) {
				if (Files.exists(Paths.get("levels/" + args[i + 1]))) {
					return "levels/" + args[i + 1];
				}

			}
			if (args[i].equals("--levels"))
				if (Files.isDirectory(Paths.get(args[i]))) {
					return "";
				}

		}
		return null;
	}

	/**
	 * Draw the initial frame on the screen depending to the board
	 * @param context Context of library Zen5
	 * @param board A board
	 * @param width The width of the screen
	 * @param height The height of the screen
	 * @param graph An instance of the Graphics class
	 * @see ApplicationContext
	 * @see Board
	 * @see Graphics
	 */
	public static void initalFrame(ApplicationContext context, Board board, float width, float height, Graphics graph) {
		context.renderFrame(graphics -> {
			graph.drawBoard(graphics, board, width, height);
			try {
				board.initializeImages(graph);
				board.drawBoard(graph, graphics, width, height);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Execute the game with the board loaded before
	 * 
	 * @param board A board
	 * @param graph An instance of the Graphics class
	 * @param pressableKeys List of keys who can be pressed
	 * @param context Context of library Zen5
	 * @return An int : 1 - game is win, 0 - game is lose, -1 - game is abandoned
	 */
	public static int game(Board board, Graphics graph, ArrayList<KeyboardKey> pressableKeys,
			ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		// get the size of the screen
		float width = screenInfo.getWidth();
		float height = screenInfo.getHeight();

		initalFrame(context, board, width, height, graph);

		// Boucle du jeu tant que le joueur n'a pas perdu.
		while (!board.isOver()) {
			Event event = context.pollOrWaitEvent(10);
			if (event == null) { // no event
				continue;
			}
			Action action = event.getAction();
			// Apr�s avoir r�cup�r� l'action : on fait avancer le jeu comme on le souhaite
			if (action == Action.KEY_PRESSED) {
				if (pressableKeys.contains(event.getKey())) {
					board.moveElements(event.getKey());
					if (board.win()) {
						System.out.println("Partie gagnée !");
						return 1;
					}
					context.renderFrame(graphics -> {
						graph.drawBoard(graphics, board, width, height);
						try {
							board.drawBoard(graph, graphics, width, height);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				}
				if (event.getKey().equals(KeyboardKey.UNDEFINED)) {
					System.out.println("Partie abandonnée !");
					return -1;
				}
			}
		}
		// graph.printBoard();
		System.out.println("Partie Perdu !");
		return 0;
	}
	
	/**
	 * Main method : used to launch the game.
	 * 
	 * @param args Array of String : arguments of the program
	 * @throws IOException In case of read problems with .txt file
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<KeyboardKey> pressableKeys = new ArrayList<>();
		pressableKeys.add(KeyboardKey.RIGHT);
		pressableKeys.add(KeyboardKey.LEFT);
		pressableKeys.add(KeyboardKey.DOWN);
		pressableKeys.add(KeyboardKey.UP);
		
		Application.run(Color.BLACK, context -> {
			try {
				if (getLevel(args) == "" || getLevel(args) == null) {
					int level = 1;
					while (level <= 7) {
						Board board;
						board = Lecture.fileToBoard("levels/level" + level + ".txt");
						board.getCheat(args);

						Graphics graph = new Graphics();
						if (game(board, graph, pressableKeys, context) == 1 && level < 7)
							level += 1;
						else
							context.exit(0);
					}
				} else {
					if (getLevel(args) != null && getLevel(args) != "") {
						Board board;
						board = Lecture.fileToBoard(getLevel(args));
						board.getCheat(args);
	
						Graphics graph = new Graphics();
						game(board, graph, pressableKeys, context);
						context.exit(0);
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		});

	}
}
