import java.awt.Color;
import zombie.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import javax.imageio.ImageIO;

public class inventoryPlayer extends HUDElement {
 
    public Image image = null;
    public Image image2 = null;
    public Entity owningEntity = null;
    public double startValue = 0;
    public Color color = new Color(255,0,0);
    public int x = 0, y = 0, sizeX = 0, sizeY = 0, sizeY2 = 0;
    
    public inventoryPlayer(){
        super();
        this.type = "inventoryPlayer";
        this.isStatic = true;
    }
    
    @Override
    public void draw(Zombie z, Graphics g){
        if (((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.BackPackOpen)
            g.drawImage(image, 0, 0, z);
        else 
            g.drawImage(image2, 0, 0, z);
        
        if (z.drawGUIs == true) {
               ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.draw(g, z.gUIScale, z);
        }
    }
    
    @Override
    public void inizialize(Zombie z, Map<String, Object> m){
        
        try{
            this.image = ImageIO.read(new File("assets/textures/hud/inventoryPlayer.png")).getScaledInstance(z.gUIScale * 10 + 50,
                    z.gUIScale * 6 + 60, BufferedImage.SCALE_FAST);
            this.image2 = ImageIO.read(new File("assets/textures/hud/inventoryPlayerSmall.png")).getScaledInstance(z.gUIScale * 10 + 50,
                    z.gUIScale + 40, BufferedImage.SCALE_FAST);
            sizeX = z.gUIScale * 10 + 50;
            sizeY = z.gUIScale + 40;
            sizeY2 = z.gUIScale * 6 + 60;
        }   
        catch(Exception e){System.out.println("Oh shooooit, fug, something ist going very wrong: " + e);}
        
    }
    
    @Override
    public boolean onKeyPressed(Zombie z, Graphics g, KeyEvent k){
        if (k.getKeyCode() == KeyEvent.VK_ESCAPE){
            if (((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.BackPackOpen){
                ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.toggelBackPackOpening();
                this.isStatic = true;
            }
        }
        if (k.getKeyCode() == KeyEvent.VK_E){
            if (((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.BackPackOpen){
                ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.toggelBackPackOpening();
                this.isStatic = true;
            }
            else {
                ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.toggelBackPackOpening();
                this.isStatic = false;
            }
        }
        return true;
    }
    
    @Override
    public void onClicked(Zombie z, Graphics g){
        //try{g.drawImage(image, this.owningEntity.X - z.Z, this.owningEntity.Y - z.Q + 60, null);
        try{
            
                int x = MouseInfo.getPointerInfo().getLocation().x;
                int y = MouseInfo.getPointerInfo().getLocation().y;
                
                if (((EntityPlayer)z.entities.get(0)).inventory.BackPackOpen == true) ((EntityPlayer)z.entities.get(0)).inventory.trySwichItems(z.gUIScale, z);
                
        }catch(Exception e){}
        
        
    }
    
    @Override
    public boolean blockInteractions(int mX, int mY){
        if (this.isStatic == true){
            if (mX >= x && mX <= sizeX)
                if (mY >= y && mY <= sizeY){
                    return true;
                }
        }
        else if (this.isStatic == false){
            if (mX >= x && mX <= sizeX)
                if (mY >= y && mY <= sizeY2){
                    return true;
                }
        }
        return false;
        
    }
    
    public inventoryPlayer CreateNew(){
        return new inventoryPlayer();
    }
    
}
