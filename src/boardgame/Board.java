package boardgame;

public class Board {

	private int rows;
	private int columns;

	private Piece[][] pieces;

	public Board(int rows, int columns) {

		// programação defensiva
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: thare must be at least 1 row and 1 column.");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException("Position not on the Board.");
		}
		return pieces[row][column];
	}

	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the Board.");
		}
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	// vai retornar a peça que foi retirada do tabuleiro
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the Board.");
		}
		// se a peça não existir no tabuleiro, retornar null para a peça não existente.
		if (!thereIsAPiece(position)) {
			return null;
		}
		// peça auxiliar para remover a peça do tabuleiro salvando a peça que estava na
		// posição de origem
		Piece aux = piece(position);
		aux.position = null;

		// removendo a peça da posição da matriz completamente
		pieces[position.getRow()][position.getColumn()] = null;

		return aux;
	}

	// valida se a posição do tabuleiro existe em linha e coluna
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	// valida se a posição do tabuleiro existe em position
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the Board.");
		}
		return piece(position) != null;
	}
}
