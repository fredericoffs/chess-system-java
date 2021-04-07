package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {

		ChessMatch chessMatch = new ChessMatch();
		
		//função para imprimir as peças dessa partida
		UI.printBoard(chessMatch.getPieces());
	}

}
