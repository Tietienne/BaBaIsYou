package main;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

import graphics.Graphics;
import other.Board;
import other.Lecture;

public class Main {

	public static void main(String[] args) throws IOException {
		// Initialisation du niveau
		Board board = Lecture.fileToBoard("levels/level6.txt");
		Graphics graph = new Graphics();
		ArrayList<KeyboardKey> pressableKeys = new ArrayList<>();
		// graph.printBoard();
		// board.printRules();
		pressableKeys.add(KeyboardKey.RIGHT);
		pressableKeys.add(KeyboardKey.LEFT);
		pressableKeys.add(KeyboardKey.DOWN);
		pressableKeys.add(KeyboardKey.UP);
		// ---- //

		Application.run(Color.BLACK, context -> {

			ScreenInfo screenInfo = context.getScreenInfo();
			// get the size of the screen
			float width = screenInfo.getWidth();
			float height = screenInfo.getHeight();
			System.out.println("size of the screen (" + width + " x " + height + ")");

			context.renderFrame(graphics -> {
				graph.drawBoard(graphics, board, width, height);
				try {
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
						if (board.isWin()) {
							System.out.println("Partie gagnée !");
							context.exit(0);
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
						context.exit(0);
					}
				}
			}
			// graph.printBoard();
			System.out.println("Partie terminée !");
			context.exit(0);
		});
	}
}
