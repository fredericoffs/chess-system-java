package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

// Classe com as regras do jogo de xadrez

public class ChessMatch {

	private int turn;
	private Color currentPlayer;

	// partida de xadrez tem que ter um tabuleiro
	private Board board;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	// Construtor padrão -
	public ChessMatch() {
		board = new Board(8, 8); // nessa clase que determina o tamanho do tabuleiro da partida.
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup(); // iniciar o setup da partida
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	// Retorna a matriz de peças de xadrez correspondentes a essa partida
	// (chessmatch)
	public ChessPiece[][] getChessMatch() {
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

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	// Método que realiza o movimento peça no tabuleiro retornando uma peça
	// capturada se for o caso.
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();

		// validar a posição de origem se existe uma peça
		validateSourcePosition(source);
		// validar se a peça pode mover para a posição de destino
		validateTargetPosition(source, target);

		// makeMove - realizar o movimento da peça de origem com destino
		Piece capturedPiece = makeMove(source, target);

		// trocar o turno para o próximo jogador
		nextTurn();

		// downcast
		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position.");
		}

		// Verificar se a peça que irá mover é do jogador atual, fazer downcast para a
		// classe chesspiece pois o getColor é um método dela
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours.");
		}

		// se não tiver nenhum movimento possível
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece.");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		// verificando se a peça na posição de origem não é um movimento possível
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position.");
		}
	}

	private void nextTurn() {
		// incrementa o turno da partida
		turn++;
		// muda o jogador da partida
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	// recebe as coordenadas da peça na camada do xadrez
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
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
