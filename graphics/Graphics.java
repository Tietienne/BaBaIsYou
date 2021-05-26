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
			for(int j = 0; j < height; j++) {
				if(board.getBoard().get(i).get(j).size() > 0)
					System.out.print(board.getBoard().get(i).get(j).get(0) + " ");
				else
					System.out.print("   ");
			}
			System.out.println("|");
		}
	}
}
