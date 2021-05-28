package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.zen5.ApplicationContext;
import other.Board;
import word.BoardElem;

public class Graphics {
	private final Board board;
	
	public Graphics(Board board) {
		this.board = board;
	}
	
	public void printBoard() {
		int length = board.getLength();
		int height = board.getHeight();
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < height; j++) {
				if(board.getElems(i, j).size() > 0)
					System.out.print(board.getElems(i, j).get(0) + " ");
				else
					System.out.print("   ");
			}
			System.out.println("|");
		}
	}
	
	public void drawBoard(ApplicationContext context, Board b, float width, float height) {
		int column = b.getLength();
		int line = b.getHeight();
		int widthCase = (int) (width/column);
		int lengthCase = (int) (height/line);
		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			for (int i=0; i<column; i++) {
				graphics.drawLine(i*widthCase, 0, i*widthCase, (int) height);
			}
			for (int j=0; j<line; j++) {
				graphics.drawLine(0, j*lengthCase, (int) width, j*lengthCase);
			}
		});
	}
	
	public void drawImage(ApplicationContext context, Board b, float width, float height, int i, int j, BoardElem be) throws IOException {
		int column = b.getLength();
		int line = b.getHeight();
		int widthCase = (int) (width/column);
		int lengthCase = (int) (height/line);
		File file = new File("pictures/"+be.toString()+".gif");
		BufferedImage img = ImageIO.read(file);
		context.renderFrame(graphics -> {
			graphics.drawImage(img, i*widthCase+widthCase/3, j*lengthCase+lengthCase/3, null);
		});
	}
}
