import javax.swing.*;
import java.awt.*;

class BusinessLogic{
	public static void GetMove(int currentMove, int remainingMoves, Font font, JButton btnEmpty[], String startingPlayer){
		btnEmpty[currentMove].setFont(font);

		if(startingPlayer.equals("X")){
			if(remainingMoves%2 != 0){
				btnEmpty[currentMove].setText("X");
			}
			else{
				btnEmpty[currentMove].setText("O");
			}
		}
		else{
			if(remainingMoves%2 != 0){
				btnEmpty[currentMove].setText("O");
			}
			else{
				btnEmpty[currentMove].setText("X");
			}
		}
	}
	public static void ShowGame(JPanel pnlSouth, JPanel pnlPlayingField){
		pnlSouth.setLayout(new BorderLayout());
		pnlSouth.add(pnlPlayingField, BorderLayout.CENTER);
		pnlPlayingField.requestFocus();
	}
	public static void ClearPanelSouth(JPanel pnlSouth, JPanel pnlTop, JPanel pnlNewGame, JPanel pnlPlayingField, JPanel pnlBottom, JPanel radioPanel){
		pnlSouth.remove(pnlTop);
		pnlSouth.remove(pnlBottom);
		pnlSouth.remove(pnlPlayingField);
		pnlTop.remove(pnlNewGame);
		pnlSouth.remove(radioPanel);
	}
}