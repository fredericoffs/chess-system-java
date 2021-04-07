package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

// Classe com as regras do jogo de xadrez

public class ChessMatch {

	// partida de xadrez tem que ter um tabuleiro
	private Board board;

	// Construtor padrão -
	public ChessMatch() {
		board = new Board(8, 8); // nessa clase que determina o tamanho do tabuleiro da partida.
		initialSetup(); //iniciar o setup da partida
	}

	// Retorna a matriz de peças de xadrez correspondentes a essa partida
	// (chessmatch)
	public ChessPiece[][] getPieces() {
		// Para pegar na camada da partida e não na camada do tabuleiro
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j); // downcasting para interpretar como uma peça do xadrez e
															// não como peça comum
			}
		}
		return mat;
	}

	// responsável pelo inicio da partida de xadrez colocando as peças no tabuleiro
	private void initialSetup() {
		board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
		board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
		board.placePiece(new King(board, Color.WHITE), new Position(7, 4));

	}
}
