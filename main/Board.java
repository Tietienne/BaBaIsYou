package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import other.Rules;
import word.Element;
import word.Word;

public class Board {
	private Word[][] board;
	private int length;
	private int height;
	private ArrayList<Rules> rules;
	
	public Board(Word[][] board, int length, int height, List<Rules> rules) {
		Objects.requireNonNull(board);
		Objects.requireNonNull(length);
		Objects.requireNonNull(height);
		Objects.requireNonNull(rules);
		if (board.length!=height) {
			throw new IllegalArgumentException("Le nombre de lignes du plateau n'est pas �gal au nombre de lignes donn� !");
		}
		for (int i=0; i<board.length; i++) {
			if (board[i].length!=length) {
				throw new IllegalArgumentException("Le nombre de colonnes du plateau n'est pas �gal au nombre de colonnes donn� !");
			}
		}
		
		this.board = board;
		this.length = length;
		this.height = height;
		this.rules = (ArrayList<Rules>) rules;
	}
	
	public boolean isOver() {
		for (Rules r : rules) {
			if (r.isYou() && elementExists(r.getE())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean elementExists(Element e) {
		for (int i=0;i<height;i++) {
			for (int j=0;j<length;j++) {
				if (board[i][j].getName().equals(e.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void addRule(Rules r) {
		rules.add(r);
	}
	
	public boolean isDisabled(Element e) {
		for (Rules r : rules) {
			if (r.getE().getName().equals(e.getName())) {
				return false;
			}
		}
		return true;
	}
}
