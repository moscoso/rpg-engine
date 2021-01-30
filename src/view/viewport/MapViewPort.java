package view.viewport;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
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
public class MapViewPort implements ViewPort, Observer {

    private static final int TILE_WIDTH = 64;
    private static final int TILE_HEIGHT = 64;

    private static final double SCREEN_RATIO = 0.80;

    Tile[][] tiles;

    private ArrayList<Entity> entityList;
    private ArrayList<Projectile> projectileList;

    int mapWidthInTiles = 50;
    int mapHeightInTiles = 50;
    BufferedImage grass;

    public MapViewPort() {
        try {
            grass = ImageIO.read(new File("src/resources/img/grass.jpg"));
        } catch (IOException ex) {

        }
    }

    @Override
    public void draw(Graphics g) {
        // Viewport Size (measured in pixels)
        int w = (int) (GameWindow.getSize().width * SCREEN_RATIO);
        int h = (int) (GameWindow.getSize().height * SCREEN_RATIO);       
        // Viewport Size (measured in units of tiles)
        int widthInTiles = ( w / TILE_WIDTH);
        int heightInTiles = ( h / TILE_HEIGHT);
        // Half of the Screen Size (measured in units of tiles)
        int halfWidthInTiles = (widthInTiles / 2);
        int halfHeightInTiles = (heightInTiles / 2);

        Vector2 avatarPosition = Avatar.getInstance().getTransform().getPosition();

        // Start drawing the map from a position so that the Avatar is in the center
        int startX = ((int) (avatarPosition.x)) - halfWidthInTiles;
        int startY = ((int) (avatarPosition.y)) - halfHeightInTiles;
        
        // Clamp the camera so that the edge of the map is fixed to the edge of the screen
        // startX = clamp(startX, 0, mapWidthInTiles - widthInTiles);
        // startY = clamp(startY, 0, mapHeightInTiles - heightInTiles);

        int finishX = startX + widthInTiles;
        int finishY =  startY + heightInTiles;

        //Start drawing
        for (int i = startX; i < finishX; i++) {
            for (int j = startY; j < finishY; j++) {
                int xPos = (i - startX) * TILE_WIDTH; // The x coordinate located at the left of this tile
                int yPos = (j - startY) * TILE_HEIGHT; // The y coordinate located at the top of this tile
                drawTerrain(g, i, j, xPos, yPos);
                // drawCoordinates(g, i, j, xPos, yPos);
                // Extra loops
                drawEntities(g, i, j, xPos, yPos);               
                drawProjectile(g, i, j, xPos, yPos);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Object[] mapObjects = (Object[]) arg;
        tiles = (Tile[][]) mapObjects[0];
        mapWidthInTiles = tiles.length;
        mapHeightInTiles = tiles[0].length;

        ArrayList<Entity> e = (ArrayList<Entity>) mapObjects[1];
        entityList = e;

        ArrayList<Projectile> p = (ArrayList<Projectile>) mapObjects[2];
        projectileList = p;

    }

    private void drawTerrain(Graphics g, int tileX , int tileY, int xPos, int yPos) {
        boolean inBounds = tileX >= 0 && tileX <= mapWidthInTiles && tileY >= 0 && tileY <= mapHeightInTiles;
        if(inBounds){
            g.setColor(Color.white);
        } else {
            g.setColor(Color.black);
        }
        g.fillRect(xPos, yPos, TILE_WIDTH, TILE_HEIGHT);
    }

    private void drawCoordinates(Graphics g, int tileX , int tileY, int xPos, int yPos) {
        g.setColor(Color.blue);
        String coordinate = "(" + tileX + "," + tileY + ")";
        String coordinate2 = "(" + xPos + "," + yPos + ")";
        int strX = xPos + TILE_WIDTH / 2 - g.getFontMetrics().stringWidth(coordinate) / 2;
        int strY = yPos + TILE_HEIGHT / 2;
        g.drawString(coordinate, strX, strY);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(coordinate2, strX, strY+10);
    }

    private void drawEntities(Graphics g, int tileX, int tileY, int xPos, int yPos) {
        for (Entity e : entityList) {
            boolean entityIsHere = e.getTransform().getPosition().isEqualTo((new Vector2(tileX, tileY)));
            if (entityIsHere) {
                Image entityImg = e.getSprite();
                g.drawImage(entityImg, xPos, yPos, null);

                boolean notAvatar = !e.equals(Avatar.getInstance());
                if (notAvatar) {
                    drawHealthBar(e, g, xPos, yPos);
                }
            }
        }
    }

    private void drawProjectile(Graphics g, int i, int j, int tileX, int tileY) {
        try {
            for (Projectile p : projectileList) {
                boolean projectileIsHere = p.getTransform().getPosition().isEqualTo(new Vector2(i,j));
                if (projectileIsHere) {
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
