/**
 * Implements a game of Connect 4, where players take turns
 * dropping checkers down columns.  The first player to get
 * 4 in a row is the winner.
 * @author Norm Krumpe
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ConnectFour extends JFrame implements ActionListener {
 
 public static void main(String[] args) {
  new ConnectFour(6, 7);
 }
 
 private ArrayList<Integer> idk = new ArrayList<Integer>();
 private int rows, columns;
 private JPanel cellPanel, buttonPanel;
 private JLabel status = new JLabel("Player 1's turn...", JLabel.CENTER);
 private Cell[][] cells;
 private JButton[] columnButtons;
 private static int CELL_SIZE = 70; // adjust this for larger or smaller cells
 private int thePlayer = 1;

 /**
  * Constructs a Connect 4 grid with a specified number of rows and columns.
  * 
  * @param rows
  *            the number of rows of squares
  * @param columns
  *            the number of columns of squares
  */
 public ConnectFour(int rows, int columns) {
  super("Let's play Connect 4!");
  this.rows = rows;
  this.columns = columns;

  frameSetup();
  cellPanelSetup();
  buttonPanelSetup();
  statusSetup();
  pack();
  setVisible(true);
 }

 /**
  * Sets up the details for the frame.
  */
 private void frameSetup() {
  setLayout(new BorderLayout());
  setResizable(false);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setLocationRelativeTo(null);
 }

 /**
  * Sets up the details for the square panel itself.
  */
 private void cellPanelSetup() {
  // The layout is based on the number of rows
  cellPanel = new JPanel(new GridLayout(rows, 0));
  cellPanel.setPreferredSize(new Dimension(CELL_SIZE * columns, CELL_SIZE * rows));

  // Each square in the grid is a Square object
  cells = new Cell[rows][columns];
  for (int row = 0; row < cells.length; row++) {
   for (int cell = 0; cell < cells[0].length; cell++) {
    cells[row][cell] = new Cell();
    cellPanel.add(cells[row][cell]);
   }
  }
  add(cellPanel, BorderLayout.CENTER);
 }

 /**
  * Sets up the buttons at the top of each column
  */
 private void buttonPanelSetup() {
  buttonPanel = new JPanel(new GridLayout(1, 0));
  columnButtons = new JButton[columns];
  for (int i = 0; i < columns; i++) {
   columnButtons[i] = new JButton("" + i);
   columnButtons[i].addActionListener(this);
   columnButtons[i].setToolTipText("Move in column " + i);
   buttonPanel.add(columnButtons[i]);
  }
  add(buttonPanel, BorderLayout.NORTH);
 }
 
 /**
  * Does the action when the button is clicked
  */
 public void actionPerformed(ActionEvent e) {
	 for(int i = 0; i < columnButtons.length;i++) {
		if(e.getSource() == columnButtons[i]) {
			makeMove(i);
		}
 	}
	 statusSetup();
	 wonAcross();
	 wonDown();
 } 
 
 /**
  * 
  * @param x 
  * 			is the place at which the user makes the move
  */
 private void makeMove(int x) {
	 for(int i = rows-1; i >= 0; i--) {
		 if(cells[i][x].getPlayer() == 0) {
			 updateCell(i,x,this.thePlayer);
			 cells[i][x].setPlayer(makeChange());
			 break;
		 }
	 } 
	 if(cells[0][x].getPlayer() != 0) {
		 columnButtons[x].setEnabled(false);
	 }
	 if(wonDown() == true || wonAcross() == true) {
		 for(int i = 0; i < columns; i++) {
			 columnButtons[i].setEnabled(false);
		 }
	 }
 }
 
 /**
  * 
  * @return returns the player, either one or two.
  */
 private int makeChange() {
	 if(this.thePlayer == 1) {
		 this.thePlayer = 2;
	 }
	 else if(this.thePlayer == 2) {
		 this.thePlayer = 1;
	 }
	 return this.thePlayer;
 }

 /**
  * Sets up the status label at the bottom of the frame, which keeps track of
  * which player has the move.
  */
 private void statusSetup() {
	 boolean idk = false;
	 if(this.thePlayer == 1) {
		 status.setText("Player 1's turn...");
	 }
	 if(this.thePlayer ==2) {
		 status.setText("Player 2's turn...");
	 }
	 if(this.thePlayer == 1 && (wonDown() == true || wonAcross() == true)) {
		 status.setText("Player 2 won!...");
	 }
	 if(this.thePlayer == 2 && (wonDown() == true || wonAcross() == true)) {
		 status.setText("Player 1 won!...");
	 }
	 if(isFilled() == true && idk == true) {
		 status.setText("Tie Game...");
	 }
	 add(status, BorderLayout.SOUTH);
	 idk = true;
 }
 
 /**
  * Resets adds zeros to the array list which will be used later
  */
 private void makeIt() {
	 for(int i = 0; i < 9; i++) {
		 idk.add(0);
	 }
 }
 
 /**
  * 
  * @return true if one of the players won across the board
  */
 private boolean wonAcross() {
	 makeIt();
	 int inARow1 = 0;
	 int inARow2 = 0;
	 int count = 0;
	 for(int i = 0; i < rows; i++) {
		 for(int x = 0; x < columns - 1; x++) {
			 if(cells[i][x].getPlayer() == cells[i][x+1].getPlayer() && cells[i][x].getPlayer() == 1) {
				 idk.set(count, i);
				 count++;
				 idk.set(count, x);
				 count++;
				 inARow1++;
				 if(inARow1 == 3) {
					 idk.set(7, i);
					 idk.set(8, x);
					 clear(true);
					 return true;
				 }
			 }
			 else if(cells[i][x].getPlayer() == cells[i][x+1].getPlayer() && cells[i][x].getPlayer() == 2) {
				 idk.set(count, i);
				 count++;
				 idk.set(count, x);
				 count++;
				 inARow2++;
				 if(inARow2 == 3) {
					 idk.set(6, i);
					 idk.set(7, x+1);
					 clear(true);
					 return true;
				 }
			 }
		 }
		 inARow1 = 0;
		 inARow2 = 0;
		 count = 0;
	 }
	 return false;
 }
 
 /**
  * 
  * @param hi determines whether to change the color of the background or clear the list
  */
 private void clear(boolean hi) {
//	 for(int obj: idk) {
//		 System.out.println(obj);
//	 } 
	 if(hi = true) {
		 cells[idk.get(0)][idk.get(1)].setBackground(Color.GREEN);
		 cells[idk.get(2)][idk.get(3)].setBackground(Color.GREEN);
		 cells[idk.get(4)][idk.get(5)].setBackground(Color.GREEN);
		 cells[idk.get(6)][idk.get(7)].setBackground(Color.GREEN);
		 
	 }
	 else {
		 for(int obj: idk) {
			 obj = 0;
		 } 
	 }
 }
 
 /**
  * 
  * @return true if a player won by going down the board
  */
 private boolean wonDown() {
	 int count = 0;
	 int inARow1 = 0;
	 int inARow2 = 0;
	 for(int i = 0; i < columns; i++) {
		 for(int x = 0; x < rows - 1; x++) {
			 if(cells[x][i].getPlayer() == cells[x+1][i].getPlayer() && cells[x][i].getPlayer() == 1) {
				 idk.set(count, x);
				 count++;
				 idk.set(count, i);
				 count++;
				 inARow1++;
				 if(inARow1 == 3) {
					 idk.set(6, x+1);
					 idk.set(7, i);
					 clear(true);
					 return true;
				 }
			 }
			 else if(cells[x][i].getPlayer() == cells[x+1][i].getPlayer() && cells[x][i].getPlayer() == 2) {
				 idk.set(count, x);
				 count++;
				 idk.set(count, i);
				 count++;
				 inARow2++;
				 if(inARow2 == 3) {
					 idk.set(6, x + 1);
					 idk.set(7, i);
					 clear(true);
					 return true;
				 }
			 }
		 }
		 inARow1 = 0;
		 inARow2 = 0;
		 count = 0;
	 }
	 return false;
 }
 
 /**
  * 
  * @return false if the board is filled and true if it is not filled
  */
 private boolean isFilled() {
	 for(int i = 0; i < rows; i++) {
		 for(int x = 0; x < columns; x++) {
			 if(cells[i][x].getPlayer() == 0) {
				 return false;
			 }
		 }
	 }
	 return true;
 }

 /**
  * Updates a specified square by indicating which player should now occupy
  * that square
  * 
  * @param row
  *            the row of the square
  * @param col
  *            the column of the square
  * @param player
  *            which player should occupy that square (0=empty, 1=player1,
  *            2=player2)
  */
 public void updateCell(int row, int col, int player) {
  cells[row][col].setPlayer(player);
 }


}
