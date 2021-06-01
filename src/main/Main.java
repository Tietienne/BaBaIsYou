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
		Board board = Lecture.fileToBoard("levels/level1.txt");
		Graphics graph = new Graphics(board);
		//graph.printBoard();
		//board.printRules();
		pressableKeys.add(KeyboardKey.RIGHT);
		pressableKeys.add(KeyboardKey.LEFT);
		pressableKeys.add(KeyboardKey.DOWN);
		pressableKeys.add(KeyboardKey.UP);
		ArrayList<KeyboardKey> pressableKeys = new ArrayList<>();
		// ---- //
	        ScreenInfo screenInfo = context.getScreenInfo();
	        // get the size of the screen
	        float width = screenInfo.getWidth();
	        float height = screenInfo.getHeight();
	        System.out.println("size of the screen (" + width + " x " + height + ")");
	        
	        try {
	        	graph.drawBoard(context, board, width, height);
		
	    Application.run(Color.ORANGE, context -> {
	        
				board.drawBoard(graph, context, width, height);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Boucle du jeu tant que le joueur n'a pas perdu.
			while (!board.isOver()) {
	          Event event = context.pollOrWaitEvent(10);
	          if (event == null) {  // no event
	            continue;
	          }
	          Action action = event.getAction();
	          // Apr�s avoir r�cup�r� l'action : on fait avancer le jeu comme on le souhaite
	          if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
	            System.out.println("abort abort !");
	            context.exit(0);
	            return;
	          }
	          System.out.println(event);
	        }
			//graph.printBoard();
			context.exit(0);
		});
	}
}
