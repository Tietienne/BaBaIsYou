package main;

//import java.awt.Color;
//import java.awt.geom.Rectangle2D;
import java.io.IOException;
//import java.util.ArrayList;
//
//import fr.umlv.zen5.Application;
//import fr.umlv.zen5.Event;
//import fr.umlv.zen5.ScreenInfo;
//import fr.umlv.zen5.Event.Action;
//import word.Word;
//import other.Rules;

import other.Lecture;

public class Main {

	public static void main(String[] args) throws IOException {
		/*// Initialisation du niveau
		Board b = new Board(new Word[0][0], 0, 0, new ArrayList<Rules>()); // A remplacer !!! --> Fait perdre la partie instantan�ment
		// ---- //
		
	    Application.run(Color.ORANGE, context -> {
	        
	        // get the size of the screen
	        ScreenInfo screenInfo = context.getScreenInfo();
	        float width = screenInfo.getWidth();
	        float height = screenInfo.getHeight();
	        System.out.println("size of the screen (" + width + " x " + height + ")");
	        
	        context.renderFrame(graphics -> {
	          graphics.setColor(Color.ORANGE);
	          graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
	        });
	        
			// Boucle du jeu tant que le joueur n'a pas perdu.
			while (!b.isOver()) {
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
			context.exit(0);
	      });
		
		// Fin du jeu / du programme
		
		// ---- //*/
		Lecture.fileToBoard("level1.txt");
	}
}
