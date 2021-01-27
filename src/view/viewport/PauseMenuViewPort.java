package view.viewport;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import model.director.Director;
import model.menu.Menu;

/**
 * The MainMenuViewPort draws the main menu
 *
 * @author ChrisMoscoso
 */
public class PauseMenuViewPort implements ViewPort, Observer {

    //Model Variables
    private String[] options = {};
    private int activeOptionIndex;
    private boolean isVisible;
    //Drawing variables
    private int width, height;
    private int logoHeight;
    private int logoY = 100, logoX;
    //File image variables
    private ImageIcon imageIcon;
    BufferedImage fbLogo;
    
    public PauseMenuViewPort(){
        imageIcon = new ImageIcon("src/resources/img/bg.gif");
        try {
            fbLogo = ImageIO.read(new File("src/resources/img/FinalBoss.png"));          
        } catch (IOException ex) {
            logoHeight = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        if(isVisible){
            if (Director.getSize() != null) {
                width = Director.getSize().width;
                height = Director.getSize().height;
            }
            /*DRAW SEMI TRANSPARENT BG*/
            g.setColor(new Color(255, 255, 255, 100));
            g.fillRect(0, 0, width, height);
            /*DRAW LOGO*/
            logoX = width / 2 - fbLogo.getWidth() / 2;
            logoHeight = fbLogo.getHeight();
            g.drawImage(fbLogo, logoX, logoY, null);

            /*DRAW MENU*/
            g.setFont(new Font(g.getFont().getFamily(), Font.PLAIN, 30));
            
            for (int i = 0; i < options.length; i++) {
                if (i == activeOptionIndex) {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.black);
                }
                int stringWidth = g.getFontMetrics().stringWidth(options[i]);
                int stringHeight = g.getFontMetrics().getHeight();
                int padding = 25;
                g.drawString(options[i], (width / 2) - (stringWidth / 2), i * (stringHeight + padding) + logoY + logoHeight + stringHeight + padding);
            }
            
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Menu m = (Menu) o;
        options = m.getOptions();
        activeOptionIndex = m.getCurrentSelectionIndex();
        isVisible = m.isVisible();
    }
}
