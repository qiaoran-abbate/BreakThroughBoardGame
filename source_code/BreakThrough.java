/**
* The BreakThrough program is a game with a GUI interface.
* It it to be played on one computer with 2 players, each player
* will pass the mouse back and fourth between themselves
*
* Rochester Institute of Technology
* @author  Qiaoran Li
* @author  Brandon Joel Giraldo
* @version 1.1.6
* @since   2014-03-14 
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;

/**
 * A class to play the game (contain main method)
 */
public class BreakThrough extends JFrame
{
   //Creation of Icons for board
   public static ImageIcon blackChip = new ImageIcon("blackChip.png");
   public static ImageIcon redChip = new ImageIcon("redChip.png");
   public static ImageIcon winner = new ImageIcon("winner.jpg");
   public static ImageIcon loser = new ImageIcon("loser.jpg");
   
   //Icon will handle player turns based on what it will be assigned later
   public static Icon icon = null;
   
   //Identifying x and y coordinates for 1st and 2nd clicks
   //as well as the calculated possible moves for each chip
   public static int oldX = -1;
   public static int oldY = -1;
   public static int newX = -1;
   public static int newY = -1;
   
   //storage for possible moves
   public myJButton new1 = null; 
   public myJButton new2 = null;
   public myJButton new3 = null;
   
   //int attributes to hold the move count and checker count
   public static int redMoves = 0;
   public static int redCheckers = 16;  
   public static int blackMoves = 0;
   public static int blackCheckers = 16;

   //2D Array to handle button placement on JFrame, as well as ActionListener   
   public final static myJButton[][] squares = new myJButton[8][8];
   Font font = new Font("Serif", Font.ITALIC, 15);
   
   
   //Main method, will create new Instance of Breakthough method
   public static void main(String[] args)
   {
      new BreakThrough(); 
   }
   
   //BreakThrough method will make a new instance of board
   //and assign chips and ActionListeners to each square
   //on the grid
   public BreakThrough()
   {  
      new MakeBoard();
      for(int i = 0; i<8; i++){
         for(int j = 0; j<8; j++){
            if(i == 0 || i == 1){
               squares[i][j].setIcon(blackChip); 
            }
            if(i == 6 || i == 7){
               squares[i][j].setIcon(redChip); 
            }
            System.out.println(squares[i][j].getA() + " " + squares[i][j].getB() +" "+ squares[i][j].getIcon());
            squares[i][j].addActionListener(new CheckerHandler(i,j));
         }
      }
      MakeBoard.newGame.requestFocus();
      MakeBoard.redMoves.setFont(font);
      MakeBoard.blackMoves.setFont(font);
      MakeBoard.redCheckers.setFont(font);
      MakeBoard.blackCheckers.setFont(font);
      
    }
    
    //CheckerHandler class will act as a custom ActionListener 
    //for out purposes. It will detect all input from the user
    //and calculate what is required to implement the functionality
    public class CheckerHandler implements ActionListener
    {
      //Declarations x and y for coordinates
      //and myJButton for assignment with Source
      private int x = -1;
      private int y = -1;
      
      //Constructor to assign x and y values from clicked button         
      public CheckerHandler(int _x, int _y)
      {
         x = _x;
         y = _y;
      }
      
      //actionPerformed will determine if turn is on first click
      //or second click         
      public void actionPerformed(ActionEvent e)
      {
         //If statment to verify if first click
         if(oldX <0)
         {
            //assigning x and y to "old" or first click x and y values
            oldX = x;
            oldY = y;
            System.out.println("old x = "  +x + " old y " + y); 
               
            //If user clicks on empty panel, display message and reset
            //color and calculations for moves
            if(squares[x][y].getIcon() == null)
            {
               JOptionPane.showMessageDialog(squares[x][y],"No piece here, choose again.");
               reset();
               return;
            }
                  
            //If turn icon is unassigned, get players first click icon
            //will either be back or red to start game 
            if(icon == null)
            {
               icon = squares[x][y].getIcon();
            }
                                 
             //during game, show error if its not thie player's turn
            if(!icon.equals(squares[x][y].getIcon()))
            {
               JOptionPane.showMessageDialog(null,"It's not your turn!");
               reset();
               return;
            }
               //Compute the possible moves for first click
               //and highlight their background to yellow
               check();
               //rotate turns once the move is done
               rotateTurn();              
            }
            //Can only reach this else if it is the second click in a series
            //of one players turn
            else if(oldX >= 0){
               //Assign new x and y with second click
               newX = x;
               newY = y;
               System.out.println("new x = " + x + " new y "+ y);
               //call makeMove function to furthur validation and calcuations
               makeMove();             
            }
      }
 
         //Make move method will verifty that the move is a legal move
         //and then replace the icon accordingly and change turns 
         public void makeMove()
         {
            //If chip is same spot as itself then
            //it will not be able to eat itself
            //and reset everything
            if(oldX == newX && oldY == newY){
               JOptionPane.showMessageDialog(null,"You didn't move! Try again.");
               rotateTurn();
               resetColor();
               reset();
               return;
            }
            
            //If spot clicked is empty then
            //it will not register a valid move
            //and will reset everything
            Icon pic1 = squares[oldX][oldY].getIcon();
            if(pic1.equals(null)){
               JOptionPane.showMessageDialog(null,"Square is blank to move");
               rotateTurn();
               resetColor();
               reset();
               return;
            }
            
            //If click is same color then 
            //it is not a valid move and will
            //reset everything
            Icon pic2 = squares[newX][newY].getIcon();
            if(pic1.equals(pic2)){
               JOptionPane.showMessageDialog(null,"Can not move the same color piece over each other");
               rotateTurn(); 
               resetColor();       
               reset();
               return;
            }

            //If spot is not equal to the calculated pieces,
            //it will not be a valid move and will reset accordingly
             if(squares[newX][newY].getBackground() != Color.YELLOW){
                JOptionPane.showMessageDialog(null,"This is not a valid move!");
                rotateTurn();
                resetColor();
                reset();
                return;
             }
   
            //if pass all if statement, move the checker
            //update and display the checker count attributes
            squares[newX][newY].setIcon(pic1);
            Icon pic3 = squares[newX][newY].getIcon();
            if(pic2 != null){
               if(!(pic3.equals(pic2))){
                  if(pic2.equals(redChip)){
                     redCheckers--;
                     MakeBoard.redCheckers.setText(redCheckers +"");
                  }
                  else if(pic2.equals(blackChip)){
                     blackCheckers--;
                     MakeBoard.blackCheckers.setText(blackCheckers +"");
                  }
               } 
            } 
            squares[oldX][oldY].setIcon(null);
            
            //update and display turn message and move count message
            //display winner message when one player reaches the other end
            if(pic1.equals(redChip)){
               if(x == 0){
                  MakeBoard.redPlayerIcon.setIcon(winner);
                  MakeBoard.blackPlayerIcon.setIcon(loser);
                  JOptionPane.showMessageDialog(null,"RED wins! Game over.");
                  System.exit(1);
               }
               MakeBoard.message.setVisible(false);
               MakeBoard.message1.setVisible(true);
               redMoves++;
               MakeBoard.redMoves.setText(redMoves + "");
            }else{
               if(x == 7){
                  MakeBoard.redPlayerIcon.setIcon(loser);
                  MakeBoard.blackPlayerIcon.setIcon(winner);
                  JOptionPane.showMessageDialog(null,"BLACK wins! Game over.");
                  System.exit(1);
               }
               MakeBoard.message.setVisible(true);
               MakeBoard.message1.setVisible(false);
               blackMoves++;
               MakeBoard.blackMoves.setText(blackMoves + "");
            }
                     
            resetColor();
            reset(); 
         }
         
         // This method is used to rotate turns between users after a
         // checker placement or an invalid click
         public void rotateTurn()
         {
            if(icon.equals(redChip)){
                  icon = blackChip; 
            }else{
                  icon = redChip; 
            } 
         }
         
         //resetColor will simply undo the highlights of the board
         //and reset to default layout after turn is complete
         public void resetColor(){
            for(int i = 0; i < 8; i++){
               for(int j=0; j<8; j++){
                  if((j+i)%2 != 0){
                     squares[i][j].setBackground((Color.BLACK));
                  }
                  else{
                     squares[i][j].setBackground((Color.RED));
                  }
               }
            }
         }
         
         //reset method will reset all x and y values for first and second click 
         //and also resets calculated positions for each spot
         public void reset(){
            BreakThrough.oldX = -1;
            BreakThrough.oldY = -1;
            BreakThrough.newX = -1;
            BreakThrough.newY = -1;  
            new1 = null; 
            new2 = null;
            new3 = null; 
         } 
         

         // This method computes the possible moves for black and red checker moves
         // and highlight the backgound of those moves to yellow.
         public void check()
         {
            //compute possible moves for red checkers
            if(squares[x][y].getIcon().equals(redChip))
            {  
               //This is an edge piece, the farthest right on the board
               //it will only calculate two possible movements 
               if(squares[x][y].getB() == 7)
               {
                  //Calculating possible movements
                  new1 = squares[squares[x][y].getA()-1][squares[x][y].getB()];
                  new2 = squares[squares[x][y].getA()-1][squares[x][y].getB()-1];
                  //The piece can NEVER move forward onto a spot if there is another chip
                  //this ensures that it will not be highlighted if that is the case
                  if(new1.getIcon() == null)
                  {
                     squares[squares[x][y].getA()-1][squares[x][y].getB()].setBackground(Color.YELLOW);
                  }
                  //Hight the pieces diagonally forward only when these are opponent's pieces.
                  if(new2.getIcon() != redChip)
                  {
                     squares[squares[x][y].getA()-1][squares[x][y].getB()-1].setBackground(Color.YELLOW);
                  }
               }
               //This is an edge piece, the farthest left on the board
               //it will only calculate two possible movements 
               else if(squares[x][y].getB() == 0)
               {
                  //Calculating possible movements
                  new1 = squares[squares[x][y].getA()-1][squares[x][y].getB()];
                  new3 = squares[squares[x][y].getA()-1][squares[x][y].getB()+1]; 
                  //The piece can NEVER move forward onto a spot if there is another chip
                  //this ensures that it will not be highlighted if that is the case 
                  if(new1.getIcon() == null)
                  {
                     squares[squares[x][y].getA()-1][squares[x][y].getB()].setBackground(Color.YELLOW);
                  }
                  //Hight the pieces diagonally forward only when these are opponent's pieces.
                  if(new3.getIcon() != redChip)
                  {
                     squares[squares[x][y].getA()-1][squares[x][y].getB()+1].setBackground(Color.YELLOW);
                  }
               }
               //if it is not an edge piece, then there can be up to 3 possible moves
               //this will calculate them
               else
               {     
                  //Calculating possible movements 
                  new1 = squares[squares[x][y].getA()-1][squares[x][y].getB()];
                  new2 = squares[squares[x][y].getA()-1][squares[x][y].getB()-1];
                  new3 = squares[squares[x][y].getA()-1][squares[x][y].getB()+1];
                  //The piece can NEVER move forward onto a spot if there is another chip
                  //this ensures that it will not be highlighted if that is the case
                  if(new1.getIcon() == null)
                  {
                     squares[squares[x][y].getA()-1][squares[x][y].getB()].setBackground(Color.YELLOW);
                  }
                  if(new2.getIcon() != redChip)
                  {
                     squares[squares[x][y].getA()-1][squares[x][y].getB()-1].setBackground(Color.YELLOW);
                  }
                  if(new3.getIcon() != redChip)
                  {
                     squares[squares[x][y].getA()-1][squares[x][y].getB()+1].setBackground(Color.YELLOW);
                  }
               }
            }
            //compute possible moves for black checkers
            else if(squares[x][y].getIcon().equals(blackChip))
            { 
               //This is an edge piece, the farthest right on the board
               //it will only calculate two possible movements
               if(squares[x][y].getB() == 7)
               {       
                  //Calculating possible movements
                  new1 = squares[squares[x][y].getA()+1][squares[x][y].getB()];
                  new2 = squares[squares[x][y].getA()+1][squares[x][y].getB()-1];
                  //The piece can NEVER move forward onto a spot if there is another chip
                  //this ensures that it will not be highlighted if that is the case
                  if(new1.getIcon() == null)
                  {
                     squares[squares[x][y].getA()+1][squares[x][y].getB()].setBackground(Color.YELLOW);
                  }
                  //Hight the pieces diagonally forward only when these are opponent's pieces.
                  if(new2.getIcon() != blackChip)
                  {
                     squares[squares[x][y].getA()+1][squares[x][y].getB()-1].setBackground(Color.YELLOW);
                  }
               }
               //This is an edge piece, the farthest left on the board
               //it will only calculate two possible movements
               else if(squares[x][y].getB() == 0)
               {
                  //Calculating possible movements
                  new1 = squares[squares[x][y].getA()+1][squares[x][y].getB()];
                  new3 = squares[squares[x][y].getA()+1][squares[x][y].getB()+1];
                  //The piece can NEVER move forward onto a spot if there is another chip
                  //this ensures that it will not be highlighted if that is the case
                  if(new1.getIcon() == null)
                  {
                     squares[squares[x][y].getA()+1][squares[x][y].getB()].setBackground(Color.YELLOW);
                  }
                  //Hight the pieces diagonally forward only when these are opponent's pieces.
                  if(new3.getIcon() != blackChip)
                  {
                     squares[squares[x][y].getA()+1][squares[x][y].getB()+1].setBackground(Color.YELLOW);
                  }
               }
               else
               {
                  //Calculating possible movements
                  new1 = squares[squares[x][y].getA()+1][squares[x][y].getB()];
                  new2 = squares[squares[x][y].getA()+1][squares[x][y].getB()-1];
                  new3 = squares[squares[x][y].getA()+1][squares[x][y].getB()+1];
                  //The piece can NEVER move forward onto a spot if there is another chip
                  //this ensures that it will not be highlighted if that is the case
                  if(new1.getIcon() == null)
                  {
                     squares[squares[x][y].getA()+1][squares[x][y].getB()].setBackground(Color.YELLOW);
                  }
                  if(new2.getIcon() != blackChip)
                  {
                     squares[squares[x][y].getA()+1][squares[x][y].getB()-1].setBackground(Color.YELLOW);
                  }
                  if(new3.getIcon() != blackChip)
                  {
                     squares[squares[x][y].getA()+1][squares[x][y].getB()+1].setBackground(Color.YELLOW);
                  }
               }
            } //END calculating moves  
         }//END check method
     }
}