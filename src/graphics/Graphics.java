package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.imageio.ImageIO;

	
import other.Board;
import word.BoardElem;

public class Graphics {
	private final HashMap<BoardElem, BufferedImage> pictures = new HashMap<>();
	
	/**
	 * Draw a black rectangle on the screen to hide the previous frame
	 * @param graphics Graphics2D
	 * @param b Board
	 * @param width Width of the window
	 * @param height Height of the window
	 */
	public void drawBoard(Graphics2D graphics, Board b, float width, float height) {
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(0, 0, width, height));
	}

	/**
	 * Load the picture and put it in the Hashmap if it's absent in it
	 * @param be BoardElem
	 * @throws IOException
	 */
	public void initializeImage(BoardElem be) throws IOException {
		Path file = Paths.get("pictures", be.toString() + ".gif");
		BufferedImage img = ImageIO.read(Files.newInputStream(file));
		pictures.putIfAbsent(be, img);
	}

	/**
	 * Draw the picture on the screen at the given coordinates
	 * @param graphics Graphics2D
	 * @param b Board
	 * @param width {@link WCWidth} of the window
	 * @param height Height of the window
	 * @param i int
	 * @param j int
	 * @param be BoardElem
	 * @throws IOException
	 */
	public void drawImage(Graphics2D graphics, Board b, float width, float height, int i, int j, BoardElem be)
			throws IOException {
		int line = b.getLine();
		int column = b.getColumn();
		int mid_height = (int) (height - 24 * line) / 2;
		int mid_width = (int) (width - 24 * column) / 2;

		graphics.drawImage(pictures.get(be), mid_width + i * 24, mid_height + j * 24, null);
	}
}
