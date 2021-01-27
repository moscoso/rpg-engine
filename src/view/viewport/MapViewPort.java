package view.viewport;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import model.director.Director;
import model.entity.Avatar;
import model.entity.Entity;
import model.map.tile.Tile;
import model.projectile.Projectile;
import utility.Bounds;

/**
 *
 * @author ChrisMoscoso
 */
public class MapViewPort implements ViewPort, Observer {

    private final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    Tile[][] tiles;

    private ArrayList<Entity> entityList;
    private ArrayList<Projectile> projectileList;

    int widthInTiles = 0;
    int heightInTiles = 0;
    BufferedImage grass;

    public MapViewPort() {
        try {
            grass = ImageIO.read(new File("src/resources/img/grass.jpg"));
        } catch (IOException ex) {

        }
    }

    @Override
    public void draw(Graphics g) {
        //Calculate which portion of the map to draw based on avatar position.
        int windowWidth = (int) (Director.getSize().width * 0.8);
        int windowHeight = (int) (Director.getSize().height * 0.8);       
        
        int windowWidthInTiles = ( windowWidth / TILE_WIDTH);
        int windowHeightInTiles = ( windowHeight / TILE_HEIGHT);

        int startX = Avatar.getInstance().getLocation().x - windowWidthInTiles / 2;
        int startY = Avatar.getInstance().getLocation().y - windowHeightInTiles / 2;

        int finishX = Math.min(startX + windowWidthInTiles, widthInTiles);
        int finishY =  Math.min(startY + windowHeightInTiles, heightInTiles);

        clamp(startX, 0, widthInTiles - windowWidthInTiles);
        clamp(startY, 0, heightInTiles - windowHeightInTiles);

        //Start drawing
        for (int i = startX; i < finishX; i++) {
            for (int j = startY; j < finishY; j++) {
                int tileX = (i - startX) * TILE_WIDTH; // The x coordinate located at the left of this tile
                int tileY = (j - startY) * TILE_HEIGHT; // The y coordinate located at the top of this tile

                drawCoordinates(g, i, j, startX, startY, tileX, tileY);
                drawEntities(g, i, j, tileX, tileY);               
                drawProjectile(g, i, j, tileX, tileY);
            }
        }

        // drawEntitiesEfficiently(g, new Bounds(startX, startY, finishX, finishY));
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

    /**
     * Clamps a value between an upper and lower bound
     * @param value the value to ass
     * @param min the minimum value is the smallest (most negative) value. This is the lower bound in the range of allowed values
     * @param max The maximum value is the largest (most positive) expression value to which the value of the property will be assigned
     * @return
     */
    private int clamp(int value, int min, int max) {
        if(min > max) throw new Error("min argument was greater than max argument");
        if(value < min ) return min;
        else if (value > max) return max;
        else return value;
    }

    private void drawCoordinates(Graphics g, int i , int j, int startX, int startY, int tileX, int tileY) {
        g.setColor(Color.blue);
        String coordinate = "(" + i + "," + j + ")";
        int strX = tileX + TILE_WIDTH / 2 - g.getFontMetrics().stringWidth(coordinate) / 2;
        int strY = tileY + TILE_HEIGHT / 2;
        g.drawString(coordinate, strX, strY);
    }

    private void drawEntities(Graphics g, int i, int j, int tileX, int tileY) {
        for (Entity e : entityList) {
            boolean entityIsHere = e.getLocation().equals(new Point(i, j));
            if (entityIsHere) {
                Image entityImg = e.getSprite();
                g.drawImage(entityImg, tileX, tileY, null);

                boolean notAvatar = !e.equals(Avatar.getInstance());
                if (notAvatar) {
                    drawHealthBar(e, g, tileX, tileY);
                }
            }
        }
    }

    private void drawEntitiesEfficiently(Graphics g, Bounds bound) {
        int windowWidth = (int) (Director.getSize().width * 0.8);
        int windowHeight = (int) (Director.getSize().height * 0.8);       
        int windowWidthInTiles = ( windowWidth / TILE_WIDTH);
        int windowHeightInTiles = ( windowHeight / TILE_HEIGHT);
        int startX = Avatar.getInstance().getLocation().x - windowWidthInTiles / 2;
        int startY = Avatar.getInstance().getLocation().y - windowHeightInTiles / 2;
        for (Entity e : entityList) {
            boolean entityIsInFrame = bound.containsPoint(e.getLocation());
            if (entityIsInFrame) {
                int tileX = (e.getLocation().x - startX) * TILE_WIDTH; // The x coordinate located at the left of this tile
                int tileY = (e.getLocation().y - startY) * TILE_HEIGHT; // The y coordinate located at the top of this tile


                Image entityImg = e.getSprite();
                g.drawImage(entityImg, tileX, tileY, null);

                boolean notAvatar = !e.equals(Avatar.getInstance());
                if (notAvatar) {
                    drawHealthBar(e, g, tileX, tileY);
                }
            }
        }
    }

    private void drawProjectile(Graphics g, int i, int j, int tileX, int tileY) {
        try {
            for (Projectile p : projectileList) {
                if (p.getLocation().equals(new Point(i, j))) {
                    Image entityImg = p.getSprite();
                    g.drawImage(entityImg, tileX, tileY, null);
                }
            }
        } catch (ConcurrentModificationException e) {
            // Not sure what to do
        }
    }

    private void drawHealthBar(Entity e, Graphics g, int tileX, int tileY) {
        double percentageOfHealth = (double) e.getPlayerStats().getCurrentHealth() / (double) e.getPlayerStats().getMaxHealth();
        g.setColor(Color.gray);
        g.fillRoundRect(tileX, tileY, TILE_WIDTH, 3, 5, 5);
        g.setColor(Color.green);
        g.fillRoundRect(tileX, tileY, (int) (TILE_WIDTH * percentageOfHealth), 3, 5, 5);
    }
}
