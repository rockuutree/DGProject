import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
//import java.util.ArrayList;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.StringTokenizer;

public class MessageBox extends DragonGameShape{
//Variables used throughout the class
  private String message;
  private Dragon dragon;
  private int width;
  private int height;
  private int x,y;
  private Color mainColor = Color.black;
  private Color borderColor = Color.yellow;
  private Background background;
  private Timer timer = null;
  private int frameWidth;
  private int frameHeight;
  private int timeToRead;
  private boolean displayOn;

/**
 * Description: Constructor for message box that takes in multiple arguments
 * Dragon, background, framewidth, frameheight, message
 * PRE-CONDITION: Valid arguments
 * POST-CONDITION: Sets instance variables and creates a message box object
 */   
  
  public MessageBox(Dragon d, Background b, int frame_width,int frame_height, String message){
    this.dragon = d;
    this.background = b;
    this.width = frame_width/3;
    this.height = (int) (frame_height * 0.8);
    this.message = message;
    this.displayOn = true;
    this.frameWidth = frame_width;
    this.frameHeight = frame_height;

    //Initial coordinates
    x = frameWidth - width - 20; // so that it's relative to the background location.
    y = frame_height/9;
    //Determines the time by dividing the amount of words by 4
    StringTokenizer token = new StringTokenizer(message);
    this.timeToRead = token.countTokens() / 4;
    messageBoxTimer();//Calls to message box timer 
  }

/**
 * Description: Swing timer that determines how long the message box is visible
 * PRE-CONDITION: Message Box Object is created and has a message
 * POST-CONDITION: Sets a timer and turns off after x time
 */   

  private void messageBoxTimer(){
    timer = new Timer(timeToRead * 1000, new ActionListener(){
      public void actionPerformed(ActionEvent e){
        setDisplay();
        timer.stop();
      }
    });
    timer.start();
  }

    /**
     * draw
     * ----
     * Draws a message box that writes the instructions and changes position
     * depending on which way the dragon is facing
     * Also displays depending if the user toggles or not
     * PRE: g is not null
     */  

  public void draw(Graphics g){
    //displayOn true/false turns off the messageBox or not
    if(displayOn){
        //Main black rectangle
        g.setColor(this.mainColor);
        g.fillRect(x,y, this.width, this.height);
        //Yellow border
        g.setColor(this.borderColor);
        g.fillRect(x, y, this.width, 5); //Top Bar
        g.fillRect(x, y + this.height - 5, this.width, 5); //Bottom Bar
        g.fillRect(x, y, 5, this.height); //Left Bar
        g.fillRect(x + this.width - 5, y, 5, this.height); //Right Bar
        //Texts/Instructions wrapped in box
        g.setColor(Color.white);
        Font font = g.getFont().deriveFont(24.0f);
        g.setFont(font);
        FontMetrics fontMetrics = g.getFontMetrics();
        int messageHeight = fontMetrics.getHeight();
        //Determines y Coordinate for the string
        int yMessage = y + messageHeight + 5;
        //Uses Wrapper method to determine wrapped text
        String wrappedText = wrapper(message, fontMetrics, width - 10);
        String[] sentence = wrappedText.split("/");
        //Loop that draws the strings on the screen
        for(int i = 0; i < sentence.length; i++){
            int xMessage = x + 8;
            g.drawString(sentence[i], xMessage, yMessage);
            yMessage += messageHeight;//Changes y coordinate
        }
    }
  }

   /**
 * Description: Moves the message box
 * PRE-CONDITION: Message Box Object is created
 * POST-CONDITION: Message Box shifts to the side the Dragon is facing
 */   

  public void move(){
    if(dragon.isFacingLeft()){
      x = 20; // Left Side
    } else {
        x = frameWidth - width - 30; //Right side
    }
  }

/**
 * Description: Switches the message box display on and off
 * PRE-CONDITION: Message Box Object is created
 * POST-CONDITION: Changes the boolean displayOn to false/true
 */   

  public void setDisplay(){
    if(displayOn){
        displayOn = false;
        timer.stop();
    } else{
        displayOn = true;
        timer.start();
    }
  }

   /**
 * Description: Wraps the string to fit within the box
 * PRE-CONDITION: Valid arguments
 * POST-CONDITION: Properly wraps the string lines and appends / to endings
 */   

  public String wrapper(String message, FontMetrics font, int length){
    int messageLength = 0;
    StringTokenizer token = new StringTokenizer(message);
    StringBuilder line = new StringBuilder();
    while (token.hasMoreTokens()){
        String word = token.nextToken();//Individual word
        int wordLength = font.stringWidth(word); //Sentence
        if(word.contains("/")){
            messageLength = 0;// New line
        } else if(messageLength + wordLength > length){
            line.append("/");
            messageLength = 0;// New line
        }
        line.append(word).append(" ");// Adds a space
        messageLength += wordLength + font.stringWidth(" ");//Length of string
    }
    return line.toString();
  }
}