/**
* The myJButton class extends Functionality 
* to incorporate an X and Y parameter with each 
* JButton created, it will be used as a part
* of BreakThrough.java and MakeBoard.java 
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
 * A class for custon JButtons
 */
 public class myJButton extends JButton{
    //Integers a and b to represent X and Y values
    int a;
    int b; 
    String color;
      
    //Constructor to set JButton equal to an Icon String   
    public myJButton(String icon)
    {
       super();
       this.setIcon(new ImageIcon(icon));
       this.setBorderPainted(false);
       this.setContentAreaFilled(false);
    }
      
    //Constructor to set JButton with X and Y value
    public myJButton(int a, int b, Icon _icon)
    {
       super(); 
       this.a = a; 
       this.b = b; 
       this.setIcon(_icon);
    }
      
    //Accesors for X and Y
    public int getA()
    {
       return a; 
    }
    public int getB()
    {
       return b; 
    }
}