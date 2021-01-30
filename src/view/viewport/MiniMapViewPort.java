package view.viewport;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import model.entity.Avatar;
import model.entity.Entity;
import model.tile.Tile;
import model.projectile.Projectile;
import model.vector.Vector2;
import view.window.GameWindow;

/**
 *
 * @author ChrisMoscoso
 */
public class MiniMapViewPort implements ViewPort, Observer {

    private int width;
    private int height;
    private final int tileWidth = 64;
    private final int tileHeight = 64;
    private int widthInTiles = 0;
    private int heightInTiles = 0;
    private Tile[][] tiles;
    
    private double scale = 0.10; 

    private ArrayList<Entity> entityList;
    private ArrayList<Projectile> projectileList;

    @Override
    public void draw(Graphics g) {
        width = (int) (widthInTiles * tileWidth * scale);
        height = (int) (heightInTiles * tileHeight * scale);
        
        int startX =  GameWindow.getSize().width - width;
        int startY =  0;
        
        for(int i = 0; i < widthInTiles; i++){
            for(int j = 0; j < heightInTiles; j++){
                for(Entity e : entityList){
                    Vector2 entityPosition = e.getTransform().getPosition();
                    boolean entityIsHere = entityPosition.isEqualTo(new Vector2(i, j));
                    if(entityIsHere){
                        drawEntity(g, e, startX, startY, i, j);
                    }
                }
                // This seems very redudant
                Vector2 avatarPosition = Avatar.getInstance().getTransform().getPosition();
                boolean avatarIsHere = avatarPosition.isEqualTo(new Vector2(i,j));
                if(avatarIsHere){
                    g.setColor(Color.red);
                    g.drawRect( startX + (int) (i * tileWidth * scale), startY + (int) (j * tileHeight *scale), (int) (tileWidth * scale), (int) (tileHeight * scale) );
                }
            }
        }
        
        
        
        g.setColor(Color.blue);
        g.drawRect(startX, startY, width, height);
        
        /*
        for(int i = 0; i < widthInTiles; i++){
            for(int j = 0; j < heightInTiles; j++){
                if(Avatar.getAvatar().getLocation().equals(new Point(i,j))){
                    g.setColor(Color.red);
                }else{
                    g.setColor(Color.white);
                }
                g.drawRect((int) (i * tileWidth * .2), (int) (j * tileHeight * .2), width, height);
            }
        }*/
        
    }

    @Override
    public void update(Observable o, Object arg) {
        Object[] mapObjects = (Object[]) arg;
        tiles = (Tile[][]) mapObjects[0];
        widthInTiles = tiles.length;
        heightInTiles = tiles[0].length;

        ArrayList<Entity> e = (ArrayList<Entity>) mapObjects[1];
        entityList = e;

        ArrayList<Projectile> p = (ArrayList<Projectile>) mapObjects[2];
        projectileList = p;
    }

    private void drawEntity(Graphics g, Entity e, int startX, int startY, int i, int j) {
        boolean isAvatar = e.equals(Avatar.getInstance());
        if(isAvatar){
            g.setColor(Color.red);
        }else{
            g.setColor(Color.blue);
        }
    
        g.drawRect( startX + (int) (i * tileWidth * scale), startY + (int) (j * tileHeight *scale), (int) (tileWidth * scale), (int) (tileHeight * scale) );
    }

}
