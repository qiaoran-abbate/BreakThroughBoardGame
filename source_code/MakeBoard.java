/**
* The MakeBoard class will initialize most GUI components used for
* BreakThrough.java. It will create a board, and the players status
* a long with a title and also handle the ActionListener for everything
* but the board itself
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
 * A class to constract the GUI for breakthrough 
 */
public class MakeBoard extends JFrame implements ActionListener
{   
   //String to hold user name
   String redPlayer = "";
   String blackPlayer = "";  
   //Creating JComponents
   public static JLabel message = new JLabel("<html><font color='green' size=20>Go!</font></html>",SwingConstants.CENTER);
   public static JLabel message1 = new JLabel("<html><font color='green' size=20>Go!</font></html>",SwingConstants.CENTER);      
   public static myJButton newGame = new myJButton("startgame.gif");
   public static myJButton redPlayerIcon = new myJButton("red.gif");
   public static myJButton blackPlayerIcon = new myJButton("black.gif");
   private myJButton quitGame = new myJButton("quitgame.gif"); 
   private myJButton likeUs = new myJButton("likeus.gif");   
   public static JLabel redMoves = new JLabel(BreakThrough.redMoves + "");
   public static JLabel redCheckers = new JLabel(BreakThrough.redCheckers + "");
   JLabel redPlayerName = new JLabel(redPlayer);    
   public static JLabel blackMoves = new JLabel(BreakThrough.blackMoves + "");
   public static JLabel blackCheckers = new JLabel(BreakThrough.blackCheckers + "");
   JLabel blackPlayerName = new JLabel(blackPlayer);    
   Font font = new Font("Serif", Font.ITALIC, 15);

      //Default constructor calls initialize method to create the GUI
   public MakeBoard(){
      initialize();
   }

      //initialize method will create the Frame and put the components in it
   public void initialize()
   {
      this.setSize(800,700);
      this.setTitle("Breakthrough"); 
      this.setLayout(new BorderLayout(10,10)); 
                    
         // North section 
         // Add Title     
      ImageIcon title = new ImageIcon("title.png");
      JLabel north = new JLabel("",SwingConstants.CENTER);
      north.setIcon(title);
      this.add(north,BorderLayout.NORTH);
         
         // West section 
         // Add Red Player (left)
      JPanel redPlayerStatus = new JPanel();
      redPlayerStatus.setLayout(new GridLayout(0,1));      
         //subsection midsection panel
      JPanel midSection = new JPanel(new GridLayout(0,2));
      midSection.add(new JLabel("    Name     :"));
      midSection.add(redPlayerName);
      midSection.add(new JLabel("    Moves    :"));
      midSection.add(redMoves);
      midSection.add(new JLabel("    Checker :"));
      midSection.add(redCheckers);
         //add everything back to east section
      redPlayerStatus.add(new JLabel("<html><font color='red' size=10>RED </font></html>",SwingConstants.CENTER));
      redPlayerStatus.add(redPlayerIcon);
      redPlayerStatus.add(midSection);
      message.setVisible(false);
      redPlayerStatus.add(message);    
      this.add(redPlayerStatus, BorderLayout.WEST);
         
         // East section 
         // Add Red Player (right)
      JPanel blackPlayerStatus = new JPanel();
      blackPlayerStatus.setLayout(new GridLayout(0,1));           
         //subsection midsection2 panel
      JPanel midSection2 = new JPanel(new GridLayout(0,2));
      midSection2.add(new JLabel("Name     : "));
      midSection2.add(blackPlayerName);
      midSection2.add(new JLabel("Moves    : "));
      midSection2.add(blackMoves);
      midSection2.add(new JLabel("Checker : "));
      midSection2.add(blackCheckers);
         //add everything back to east section
      blackPlayerStatus.add(new JLabel("<html><font color='black' size=10>BLACK </font></html>",SwingConstants.LEFT));
      blackPlayerStatus.add(blackPlayerIcon);
      blackPlayerStatus.add(midSection2);
      message1.setVisible(false);
      blackPlayerStatus.add(message1);
      this.add(blackPlayerStatus,BorderLayout.EAST);
               
         // South Section 
         // Add button panel
         // Contain Three buttons: new game, quit game, and like us
      JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
      buttons.add(newGame); 
      buttons.add(quitGame);
      buttons.add(likeUs);
         //add listeners to button
      quitGame.addActionListener(this); 
      newGame.addActionListener(this);
      likeUs.addActionListener(this);
      this.add(buttons,BorderLayout.SOUTH);
   
         // Center Section 
         // Build 8 * 8 chess board
         // using gridLayout (8 by 8);
      JPanel board = new JPanel();
      board.setLayout(new GridLayout(8,8));
         //Create new JButtons on field with background.
      for(int i = 0; i <8; i++) 
      {
         for(int j = 0; j < 8; j++) 
         {
            BreakThrough.squares[i][j] = new myJButton(i,j,null);
            if ((j+i)%2 != 0) 
            {
               BreakThrough.squares[i][j].setBackground(Color.BLACK);
            } 
            else 
            {
               BreakThrough.squares[i][j].setBackground(Color.RED);
            }
            board.add(BreakThrough.squares[i][j]);
            validate(); 
            BreakThrough.squares[i][j].setEnabled(false);  
         }
      }
      this.add(board, BorderLayout.CENTER);
                          
      this.setVisible(true);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
   }  
 
   // This is the ActionListener for the three button
   // new game, quit game, and like us.
   // new game calls 2 input dialog which promot user to input their names
   public void actionPerformed(ActionEvent e)
   {   
      JButton button = new JButton();
      button = (JButton)e.getSource();
      redPlayerName.setFont(font);
      blackPlayerName.setFont(font);
      //If quit game icon is clicked, game will exit
      if(button.getIcon().equals(quitGame.getIcon()))
      {
         System.out.println(button.getIcon());
         JOptionPane.showMessageDialog(null, "Thanks for playing.");
         System.exit(0);
      }
      //if Start Game icon is clicked, game will being
      else if(button.getIcon().equals(newGame.getIcon()))
      {
         //get red player's name
         redPlayer = JOptionPane.showInputDialog("Welcome! \nPlease enter the name of red player.");
         if(redPlayer == null || redPlayer.equals("")){
            redPlayerName.setText("Player1");
         } 
         else{
            redPlayerName.setText(redPlayer);
         }
         //get black player's name
         blackPlayer = JOptionPane.showInputDialog("Welcome! \nPlease enter the name of black player.");
         if(blackPlayer == null || blackPlayer.equals("")){
            blackPlayerName.setText("Player2");
         } 
         else{
            blackPlayerName.setText(blackPlayer);
         }      
         for(int i = 0; i <8; i++) 
         {
            for(int j = 0; j < 8; j++) 
            {
               BreakThrough.squares[i][j].setEnabled(true);
            }
         }  
         JOptionPane.showMessageDialog(null, "Game started, enjoy!");
         newGame.setEnabled(false); 
         likeUs.requestFocus();
      }
      //Show Developers (US!) 
      else if(button.getIcon().equals(likeUs.getIcon()))
      {
         JOptionPane.showMessageDialog(null, "Developed by Brandon G., and Qiaoran L.");
      }
   }
}
