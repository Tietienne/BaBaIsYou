package graphics;

import other.Board;

public class Graphics {
	private final Board board;
	
	public Graphics(Board board) {
		this.board = board;
	}
	
	
	public void printBoard() {
		int length = board.getLength();
		int height = board.getHeight();
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < height; i++)
				System.out.print(board.getBoard()[i][j].get(i));
			System.out.println("");
		}
	}
}
