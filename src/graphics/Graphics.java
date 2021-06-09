package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import other.Board;
import word.BoardElem;

public class Graphics {

	public void drawBoard(Graphics2D graphics, Board b, float width, float height) {
		// Couleur du fond
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(0, 0, width, height));
	}

	public void drawImage(Graphics2D graphics, Board b, float width, float height, int i, int j, BoardElem be)
			throws IOException {
		int line = b.getLine();
		int column = b.getColumn();
		int mid_height = (int) (height - 24 * line) / 2;
		int mid_width = (int) (width - 24 * column) / 2;
		Path file = Paths.get("pictures", be.toString() + ".gif");
		BufferedImage img = ImageIO.read(Files.newInputStream(file));
		graphics.drawImage(img, mid_width + i * 24, mid_height + j * 24, null);
	}
}
