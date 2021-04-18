package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		if (getColor() == Color.WHITE) {
			// REGRA DO PEÃO BRANCO: se a posição acima estiver vazia pode mover.
			p.setValues(position.getRow() - 1, position.getColumn());

			// verificar se o peão pode mover para posição acima
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// REGRA DO PEÃO BRANCO: no primeiro movimento ele pode mover 2 casas acima
			p.setValues(position.getRow() - 2, position.getColumn());
			Position p2 = new Position(position.getRow() - 1, position.getColumn());

			// verificar se o peão pode mover para 2 posições acima
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0
					&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// REGRA DO PEÃO BRANCO: mover nas diagonais caso houver peça oponente para
			// capturar
			// verificar se o peão pode mover para posição diagonal esquerda
			p.setValues(position.getRow() - 1, position.getColumn() - 1);

			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// verificar se o peão pode mover para posição diagonal direita
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

		} else {
			// REGRA DO PEÃO PRETO - troca o sinal das linhas 
			p.setValues(position.getRow() + 1, position.getColumn());

			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() + 2, position.getColumn());
			Position p2 = new Position(position.getRow() + 1, position.getColumn());

			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0
					&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() + 1, position.getColumn() - 1);

			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}

}
