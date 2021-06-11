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

public class Main {
	
	public static String getLevel( String[] args) {
		for(int i = 0; i < args.length; i++) {
			if(args[i] == "--level") {
				if(Files.exists(Paths.get("levels/" + args[i + 1]))) {
					System.out.println("1");
					return args[i + 1];
				}
					
			}
			if(args[i] == "--levels")
				if(Files.isDirectory(Paths.get(args[0]))) {
					System.out.println("2");
					return "";	
					}

		}
		return null;
	}

	public static int game(Board board, Graphics graph, ArrayList<KeyboardKey> pressableKeys,
			ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		// get the size of the screen
		float width = screenInfo.getWidth();
		float height = screenInfo.getHeight();

		context.renderFrame(graphics -> {
			graph.drawBoard(graphics, board, width, height);
			try {
				board.initializeImages(graph);
				board.drawBoard(graph, graphics, width, height);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

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

	public static void main(String[] args) throws IOException {
		// Initialisation du niveau
		ArrayList<KeyboardKey> pressableKeys = new ArrayList<>();
		pressableKeys.add(KeyboardKey.RIGHT);
		pressableKeys.add(KeyboardKey.LEFT);
		pressableKeys.add(KeyboardKey.DOWN);
		pressableKeys.add(KeyboardKey.UP);

		Application.run(Color.BLACK, context -> {
			if (getLevel(args) == "" || getLevel(args) == null) {
				System.out.println("3");
				int level = 1;
				while (level <= 7) {
					Board board;
					try {
						board = Lecture.fileToBoard("levels/level" + level + ".txt");

						Graphics graph = new Graphics();
						if (game(board, graph, pressableKeys, context) == 1 && level < 7)
							level += 1;
						else
							context.exit(0);

					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
			else {
				if(getLevel(args) != null && getLevel(args) != "") {
					Board board;
					try {
						board = Lecture.fileToBoard(getLevel(args));

						Graphics graph = new Graphics();
						game(board, graph, pressableKeys, context);
						context.exit(0);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}
}
