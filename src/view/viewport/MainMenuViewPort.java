package view.viewport;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import model.menu.Menu;
import view.window.GameWindow;

/**
 * The MainMenuViewPort draws the main menu
 *
 * @author ChrisMoscoso
 */
public class MainMenuViewPort implements ViewPort, Observer {

    private String[] options = new String[]{};
    private int activeOptionIndex = 0;
    private int width = 0;
    private int height = 0;
    private int logoHeight = 0;
    private int logoY = 100;
    
    private ImageIcon imageIcon;
    BufferedImage fbLogo;
    
    public MainMenuViewPort(){
        imageIcon = new ImageIcon("resources/img/bg.gif");
        try {
            fbLogo = ImageIO.read(new File("resources/img/FinalBoss.png"));          
        } catch (IOException ex) {
            logoHeight = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        width = GameWindow.getSize().width;
        height = GameWindow.getSize().height;

        drawBackground(g);
        drawLogo(g);
        drawMenu(g);        
    }

    @Override
    public void update(Observable o, Object arg) {
        Menu m = (Menu) o;
        options = m.getOptions();
        activeOptionIndex = m.getCurrentSelectionIndex();
    }

    private void drawBackground(Graphics g) {
        Image img = imageIcon.getImage();
        g.drawImage(img, 0, 0, width, height, null);
    }

    private void drawLogo(Graphics g) {
        int logoX = width / 2 - fbLogo.getWidth() / 2;
        logoHeight = fbLogo.getHeight();
        g.drawImage(fbLogo, logoX, logoY, null);
    }

    private void drawMenu(Graphics g) {
        g.setFont(new Font(g.getFont().getFamily(), Font.PLAIN, 30));
        for (int i = 0; i < options.length; i++) {
            boolean optionIsActive = i == activeOptionIndex;
            if (optionIsActive) {
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
