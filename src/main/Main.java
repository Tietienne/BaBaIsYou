package main;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.ScreenInfo;

import graphics.Graphics;
import other.Board;
import other.Lecture;

public class Main {

	public static void main(String[] args) throws IOException {
		// Initialisation du niveau
		Board board = Lecture.fileToBoard("levels/level1.txt");
		Graphics graph = new Graphics(board);
		board.printRules();
		//graph.printBoard();
		// ---- //
		
	    Application.run(Color.ORANGE, context -> {
	        
	        // get the size of the screen
	        ScreenInfo screenInfo = context.getScreenInfo();
	        float width = screenInfo.getWidth();
	        float height = screenInfo.getHeight();
	        System.out.println("size of the screen (" + width + " x " + height + ")");
	        
	        context.renderFrame(graphics -> {
		          graphics.setColor(Color.ORANGE);
		          graphics.fill(new Rectangle2D.Float(0, 0, width, height));
		        });
	        
	        try {
	        	graph.drawBoard(context, board, width, height);
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
