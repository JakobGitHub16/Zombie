/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Graphics;



public class HitDetectionBox {

    public Entity owningEntity = null;
    public int X = 0, Y = 0, sizeX = 0, sizeY = 0;
    public int livetime = 0;
    public int knockback = 0;
    public Item usedItem = null;
    public int chunkLayer = 0;
    
    public HitDetectionBox(int X, int Y, int sizeX, int sizeY, int livetime, int knockback, Entity owningEntity, Item usedItem, int chunklayer) {
        this.X = X;
        this.Y = Y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.livetime = livetime;
        this.owningEntity = owningEntity;
        this.knockback = knockback;
        this.usedItem = usedItem;
        this.chunkLayer = chunklayer;
    }
    
    public void Update(Zombie z){
        this.livetime--;
        if (this.livetime <= 0)
        if (z.hitDetectionBoxes.contains(this)){
            z.hitDetectionBoxes.remove(this);
        }
    }
    
    public void drawDebugInfo(Zombie z, Graphics g){
        g.drawRect(X - z.Z, Y - z.Q, sizeX, sizeY);
    }
    
    
}
