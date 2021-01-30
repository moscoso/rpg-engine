
package model.factory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A sprite factory should be able to turn sprite IDs into spritepaths 
 * @author ChrisMoscoso
 */
public class SpriteFactory {
    
    public static final String PLAYER_NORTH = "resources/img/entity/summoner/north_idle.png";
    public static final String PLAYER_EAST = "resources/img/entity/summoner/east_idle.png";
    public static final String PLAYER_SOUTH = "resources/img/entity/summoner/south_idle.png";
    public static final String PLAYER_WEST = "resources/img/entity/summoner/west_idle.png";
    public static final String FIREBALL_NORTH = "resources/img/projectile/fireball_north.png";
    public static final String FIREBALL_EAST = "resources/img/projectile/fireball_east.png";
    public static final String FIREBALL_SOUTH = "resources/img/projectile/fireball_south.png";
    public static final String FIREBALL_WEST = "resources/img/projectile/fireball_west.png";
    public static final String RAT = "resources/img/entity/rat.png";
    
    
    
    public static int tileWidth = 64;
    public static int tileHeight = 64;
    
    public static BufferedImage getGenericSprite(){
        BufferedImage i;
        i = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = i.getGraphics();
        g.setColor(Color.red);
        g.fillRect(0, 0, tileWidth, tileHeight);
        return i;
    }
    
    public static Image getSprite(String sprite){
        BufferedImage i;
        try {
            if(sprite != null){
                i = ImageIO.read(new File(sprite));
            }else{
                i = getGenericSprite();
            }
        } catch (IOException ex) {
            System.out.println(ex);
            i = getGenericSprite();
        }
        return i;
    }
}
