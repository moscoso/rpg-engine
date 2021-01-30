package view.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.director.Director;
import view.scene.Scene;

/**
 * This sets up the GUI window for the game, it holds an image in its content
 * pane.
 *
 * @see JFrame
 * @author ChrisMoscoso
 */
public class GameWindow {

    private static GameWindow instance;
    private static JFrame frame = new JFrame();
    private static ImagePanel panel = new ImagePanel();

    //private final int OFFSET = 10; //The offset created by the window/menu bar

    /**
     * Initializes the window with default size of 832 by 640 and adds a
     * {@link GamePanel}
     */
    private GameWindow() {
        this(1, 1);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    /**
     * A constructor that allows you to specify the size
     *
     * @param width of the window in pixels
     * @param height of the window in pixels
     */
    private GameWindow(int width, int height) {
        frame.setSize(new Dimension(width, height));
        frame.setTitle("RPG ENGINE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.addComponentListener(new GameWindowComponentListener());
        frame.addWindowListener(new GameWindowListener());
        frame.setContentPane(panel);
        frame.validate();
    }

    /**
     * This is a singleton and restricts the instantiation of a class to one "single" instance
     * @return the only instance of this class
     */
    public static GameWindow getInstance() {
        if(instance == null) {
            instance = new GameWindow();
        }
        return instance;
    }
    
    /**
     * Returns the size of the window.
     *
     * @return the size of the window
     */
    public static Dimension getSize() {
        return frame.getSize();
    }

    /**
     * Take the buffered Image and paint it to the screen
     *
     * @param bufferedImage the image that should be drawn to the screen
     */
    public void paintImageToScreen(BufferedImage bufferedImage) {
        panel.setImage(bufferedImage);
        panel.repaint();
    }

    /**
     * Adds a key controller to the windows main panel
     *
     * @param k the key listener to be added
     */
    public void addKeyController(KeyListener k) {
        frame.addKeyListener(k);
    }

    /**
     * Adds a mouse controller to the windows main panel
     *
     * @param m the mouse controller to be added
     */
    public void addMouseController(MouseListener m) {
        frame.addMouseListener(m);
    }

    /**
     * Adds a mouse motion controller to the windows main panel
     *
     * @param m the mouse motion listener to be added
     */
    public void addMouseMotionController(MouseMotionListener m) {
        frame.addMouseMotionListener(m);
    }

    /**
     * A JPanel that is comprised entirely of a bufferedImage
     *
     * @author ChrisMoscoso
     */
    private static class ImagePanel extends JPanel {

        private transient BufferedImage image;

        public ImagePanel(){
            this.setBackground(Color.WHITE);
        }
        
        /**
         * Set the image for the ImagePanel
         *
         * @param image the main image of the ImagePanel
         */
        public void setImage(BufferedImage image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }

    
    private class GameWindowListener implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {
            //Do nothing
        }

        @Override
        public void windowClosing(WindowEvent e) {
            //Do nothing
        }

        @Override
        public void windowClosed(WindowEvent e) {
            //Do nothing
        }

        @Override
        public void windowIconified(WindowEvent e) {Director.pauseGame();}

        @Override
        public void windowDeiconified(WindowEvent e) { Director.resumeGame(); }

        @Override
        public void windowActivated(WindowEvent e) { 
            // Director.resumeGame();
        }

        @Override
        public void windowDeactivated(WindowEvent e) {Director.pauseGame();}
        
    }
    
    private class GameWindowComponentListener implements ComponentListener{

        @Override
        public void componentResized(ComponentEvent e) {
            Scene.setSceneSize(GameWindow.frame.getSize());
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            //Do nothing
        }

        @Override
        public void componentShown(ComponentEvent e) {
            //Do nothing
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            //Do nothing
        }
       
    }
    
}
