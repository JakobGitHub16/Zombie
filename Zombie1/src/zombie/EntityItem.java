/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Graphics;

/**
 *
 * @author admin
 */
public class EntityItem extends Entity{
    
    public ItemSlot itemslot = new ItemSlot(0,0);
    
    public EntityItem(){
        super();
        
    }
    
    @Override
    public void draw(Zombie z, Graphics g, int Z, int Q, int shakeX, int shakeY, int depthOffset){
        
        try{g.drawImage(this.itemslot.io, (int)this.X - z.Z, (int)this.Y - z.Q + depthOffset, z);}catch(Exception e){}
        
    }
    
    public void setItem(Item item){
        this.itemslot.item = item.CreateNew();
        this.itemslot.setItem(item.name, item.imagePath, 60);
    }
    
    
}
