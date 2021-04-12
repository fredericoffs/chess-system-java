package chess;

import boardgame.Board;
import boardgame.Piece;
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
		initialSetup(); // iniciar o setup da partida
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

	// Método que realiza o movimento peça no tabuleiro retornando uma peça
	// capturada se for o caso.
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();

		// validar a posição de origem se existe uma peça
		validadeSourcePosition(source);

		// makeMove - realizar o movimento da peça de origem com destino
		Piece capturedPiece = makeMove(source, target);

		// downcast
		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}

	private void validadeSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position.");
		}
		// se não tiver nenhum movimento possível 
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece.");
		}
	}

	// recebe as coordenadas da peça na camada do xadrez
	private void placeNewPiece(char column, int row, Piece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	// responsável pelo inicio da partida de xadrez colocando as peças no tabuleiro
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));

		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
