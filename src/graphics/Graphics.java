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
		int line = board.getLine();
		int column = board.getColumn();
		for (int i = 0; i < line; i++) {
			for (int j = 0; j < column; j++) {
				if (board.getElems(i, j).size() > 0)
					System.out.print(board.getElems(i, j).get(0) + " ");
				else
					System.out.print("(" + i + " " + j + ") ");
			}
			System.out.println("|");
		}
	}

	public void drawBoard(ApplicationContext context, Board b, float width, float height) {
		int column = b.getColumn();
		int heigthCase = (int) (width / column);
		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			for (int i = 0; i < column + 1; i++)
				graphics.drawLine(0, i * heigthCase, (int) width, i * heigthCase);
			for (int i = 0; i < column + 1; i++)
				graphics.drawLine(i * heigthCase, 0, i * heigthCase, (int) height);

		});
	}

	public void drawImage(ApplicationContext context, Board b, float width, float height, int i, int j, BoardElem be)
			throws IOException {
		int line = b.getLine();
		int column = b.getColumn();
		int mid_height = (int) (height - 24 * line) / 2;
		int mid_width = (int) (width - 24 * column) / 2;
		File file = new File("pictures/" + be.toString() + ".gif");
		BufferedImage img = ImageIO.read(file);
		context.renderFrame(graphics -> {
			graphics.drawImage(img, mid_width + i * 24, mid_height + j * 24, null);
		});
	}
}
